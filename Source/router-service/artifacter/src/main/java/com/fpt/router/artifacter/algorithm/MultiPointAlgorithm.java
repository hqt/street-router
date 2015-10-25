package com.fpt.router.artifacter.algorithm;

import com.fpt.router.artifacter.algorithm.TwoPointAlgorithm.SearchType;
import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.artifacter.model.viewmodel.Journey;
import com.fpt.router.artifacter.model.viewmodel.Result;
import com.fpt.router.artifacter.utils.NoResultHelper;
import com.fpt.router.artifacter.utils.TimeUtils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.util.*;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/30/15.
 */
public class MultiPointAlgorithm {

    CityMap map;
    Location start;
    Location end;
    String startAddress;
    String endAddress;
    LocalTime departureTime;
    double walkingDistance;
    int K;
    boolean isOptimizeK;

    public List<Journey> run(CityMap map, Location start, Location end, String startAddress, String endAddress,
                             List<Location> middleLocations, List<String> middleAddresses,
                             LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK) {

        this.map = map;
        this.start = start;
        this.end = end;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.departureTime = departureTime;
        this.walkingDistance = walkingDistance;
        this.K = K;
        this.isOptimizeK = isOptimizeK;

        List<Journey> journeys;

        if (middleLocations.size() == 1) {
            journeys = buildThreePoint(middleLocations.get(0), middleAddresses.get(0));
        } else {
            journeys = buildFourPoint(middleLocations, middleAddresses);
        }



        Collections.sort(journeys, new Comparator<Journey>() {
            @Override
            public int compare(Journey j1, Journey j2) {
                if (j1.minutes != j2.minutes) {
                    return j1.minutes - j2.minutes;
                }
                return (int) (j1.totalDistance - j2.totalDistance);
            }
        });

        if (journeys.size() > Config.BUS_RESULT_LIMIT) {
            return journeys.subList(0, Config.BUS_RESULT_LIMIT);
        }
        return journeys;
    }


    private List<Location> buildLocations(Location start, Location end, Location... middleLocations) {
        List<Location> res = new ArrayList<Location>();
        res.add(start);
        res.addAll(Arrays.asList(middleLocations));
        res.add(end);
        return res;
    }

    private List<String> buildAddresses(String start, String end, String... middleAddresses) {
        List<String> res = new ArrayList<String>();
        res.add(start);
        res.addAll(Arrays.asList(middleAddresses));
        res.add(end);
        return res;
    }

    public List<Journey> buildThreePoint(Location middleLocation, String middleAddress) {
        // A -> B -> C
        System.out.println("Building.. three point");
        List<Location> searchLocations = buildLocations(start, end, middleLocation);
        List<String> searchAddresses = buildAddresses(startAddress, endAddress, middleAddress);
        List<Journey> results = solve(searchLocations, searchAddresses);
        return results;
    }

    public List<Journey> buildFourPoint(List<Location> middleLocations, List<String> middleAddresses) {

        List<Journey> journeys = new ArrayList<Journey>();

        List<Location> searchLocations;
        List<String> searchAddresses;

        String message = null;

        //  if both two cases fail. make fail message
        // if just one case fail. reject that case. not print out that result.

        // A -> B -> C -> D
        searchLocations = buildLocations(start, end, middleLocations.get(0), middleLocations.get(1));
        searchAddresses = buildAddresses(startAddress, endAddress, middleAddresses.get(0), middleAddresses.get(1));
        List<Journey> results = solve(searchLocations, searchAddresses);
        if (results.get(0).code.equals(Config.CODE.SUCCESS)) {
            journeys.addAll(results);
        } else {
            message = results.get(0).code;
        }

        // A -> C -> B -> D
        searchLocations = buildLocations(start, end, middleLocations.get(1), middleLocations.get(0));
        searchAddresses = buildAddresses(startAddress, endAddress, middleAddresses.get(1), middleAddresses.get(0));
        results = solve(searchLocations, searchAddresses);
        if (results.get(0).code.equals(Config.CODE.SUCCESS)) {
            journeys.addAll(results);
        } else if (message != null) {
            // both two case fail. return fail message
            return NoResultHelper.NoJourneyFound(message);
        }

        return journeys;
    }

    public List<Journey> solve(List<Location> searchLocations, List<String> searchAddresses) {

        List<Journey> res;

        ArrayListMultimap<Integer, Result> journeyCombines = ArrayListMultimap.create();
        int count = 0;

        // build middle results for each locations
        for (int i = 0; i < searchLocations.size()-1; i++) {
            Location first = searchLocations.get(i);
            String firstAddress = searchAddresses.get(i);

            Location second = searchLocations.get(i+1);
            String secondAddress = searchAddresses.get(i+1);

            TwoPointAlgorithm algor = new TwoPointAlgorithm();
            Object resultObj = algor.solveAndReturnObject(map, first, second, firstAddress, secondAddress,
                    departureTime, walkingDistance, K, isOptimizeK, SearchType.FOUR_POINT);

            // just cannot find route at one middle path. terminate program ASAP
            // because we cannot find route for whole paths
            if (resultObj instanceof  String) {
                return NoResultHelper.NoJourneyFound((String) resultObj);
            } else {
                List<Result> results = (List<Result>) resultObj;
                journeyCombines.putAll(i, results);
                count++;
            }
        }

        // combine all results. to find best routes
        if (count == 3) {
            // four points
            res = combineResultFourPoints(journeyCombines.get(0), journeyCombines.get(1), journeyCombines.get(2));
        } else {
            // three points
            res = combineResultThreePoints(journeyCombines.get(0), journeyCombines.get(1));
        }

        return res;
    }

    private List<Journey> combineResultFourPoints(List<Result> r1, List<Result> r2, List<Result> r3) {

        List<Journey> journeys = new ArrayList<Journey>();

        for (Result first : r1) {
            for (Result second : r2) {
                for (Result third : r3) {

                    Journey journey = new Journey();
                    Period p = first.totalTime.plus(second.totalTime.plus(third.totalTime));
                    journey.totalTime = p;
                    journey.minutes = (int) (TimeUtils.convertToMilliseconds(p) / (60 * 1000));
                    journey.totalDistance = first.totalDistance + second.totalDistance + third.totalDistance;
                    journey.code = Config.CODE.SUCCESS;

                    // combine all single results into journey
                    journey.results.add(first);
                    journey.results.add(second);
                    journey.results.add(third);

                    journeys.add(journey);
                }
            }
        }

        return journeys;
    }

    private List<Journey> combineResultThreePoints(List<Result> r1, List<Result> r2) {
        List<Journey> journeys = new ArrayList<Journey>();

        for (Result first : r1) {
            for (Result second : r2) {

                    Journey journey = new Journey();
                    Period p = first.totalTime.plus(second.totalTime);
                    journey.totalTime = p;
                    journey.minutes = (int) (TimeUtils.convertToMilliseconds(p) / (60 * 1000));
                    journey.totalDistance = first.totalDistance + second.totalDistance;
                    journey.code = Config.CODE.SUCCESS;

                    // combine all single results into journey
                    journey.results.add(first);
                    journey.results.add(second);

                    journeys.add(journey);
                }
            }

        return journeys;
    }

}
