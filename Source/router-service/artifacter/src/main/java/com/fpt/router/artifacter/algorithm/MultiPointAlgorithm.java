package com.fpt.router.artifacter.algorithm;

import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.artifacter.model.viewmodel.Journey;
import com.fpt.router.artifacter.model.viewmodel.Result;
import com.fpt.router.artifacter.utils.TimeUtils;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.jar.JarOutputStream;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/30/15.
 */
public class MultiPointAlgorithm {

    CityMap map;
    double walkingDistance;
    Location start;
    Location end;
    TwoPointAlgorithm twoPointAlgorithm = new TwoPointAlgorithm();

    public List<Journey> run(CityMap map, Location start, Location end, String startAddress, String endAddress,
                             List<Location> middleLocations, List<String> middleAddresses,
                             LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK) {
        return limitJourney(getJourneys(map, start, end, startAddress, endAddress,
                 middleLocations, middleAddresses,
                 departureTime, walkingDistance, K, isOptimizeK));
    }

    public List<Journey> getJourneys(CityMap map, Location start, Location end, String startAddress, String endAddress,
                      List<Location> middleLocations, List<String> middleAddresses,
                      LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK) {

        // assign variable
        this.map = map;
        this.walkingDistance = walkingDistance;
        this.start = start;
        this.end = end;

        List<Journey> journeys = new ArrayList<Journey>();
        if (middleLocations.size() == 1) {
            journeys.addAll(threePoint(map, start, end, startAddress, endAddress, middleLocations, middleAddresses, departureTime, walkingDistance, K, isOptimizeK));
        }

        if (middleLocations.size() == 2) {
            journeys.addAll(fourPoint1(map, start, end, startAddress, endAddress, middleLocations, middleAddresses, departureTime, walkingDistance, K, isOptimizeK));
            journeys.addAll(fourPoint2(map, start, end, startAddress, endAddress, middleLocations, middleAddresses, departureTime, walkingDistance, K, isOptimizeK));
        }

        sort(journeys);

        return journeys;
    }

    public List<Journey> threePoint(CityMap map, Location start, Location end, String startAddress, String endAddress,
                             List<Location> middleLocations, List<String> middleAddresses,
                             LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK) {

        List<Result> results1 = twoPointAlgorithm.solveAndReturnObject(map, start, middleLocations.get(0), startAddress,
                endAddress, departureTime, walkingDistance, K, isOptimizeK);
        List<Result> results2 = twoPointAlgorithm.solveAndReturnObject(map, middleLocations.get(0), end, startAddress,
                endAddress, departureTime, walkingDistance, K, isOptimizeK);

        if (results1 == null || results2 == null) {
            return null;
        }

        List<Journey> journeys = new ArrayList<Journey>();
        for (int x = 0; x < results1.size(); x++) {
            for (int y = 0; y < results2.size(); y++) {
                Result first = results1.get(x);
                Result second = results2.get(y);

                Journey journey = new Journey();
                Period p = first.totalTime.plus(second.totalTime);
                journey.totalTime = p;
                journey.minutes = (int) (TimeUtils.convertToMilliseconds(p) / (60 * 1000));
                journey.totalDistance = first.totalDistance + second.totalDistance;
                journey.results.add(first);
                journey.results.add(second);
                journeys.add(journey);
            }
        }

        return journeys;
    }

    public List<Journey> fourPoint2(CityMap map, Location start, Location end, String startAddress, String endAddress,
                             List<Location> middleLocations, List<String> middleAddresses,
                             LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK) {

        List<Result> results1 = twoPointAlgorithm.solveAndReturnObject(map, start, middleLocations.get(0), startAddress,
                endAddress, departureTime, walkingDistance, K, isOptimizeK);
        List<Result> results2 = twoPointAlgorithm.solveAndReturnObject(map, middleLocations.get(0), middleLocations.get(1), startAddress,
                endAddress, departureTime, walkingDistance, K, isOptimizeK);
        List<Result> results3 = twoPointAlgorithm.solveAndReturnObject(map, middleLocations.get(1), end, startAddress,
                endAddress, departureTime, walkingDistance, K, isOptimizeK);

        List<Journey> journeys = new ArrayList<Journey>();
        if (results1 == null || results2 == null || results3 == null) {
            Journey journey = new Journey();
            journey.code = "fail";
            journeys.add(journey);
            return journeys;
        }

        for (int x = 0; x < results1.size(); x++) {
            for (int y = 0; y < results2.size(); y++) {
                for (int z = 0; z < results3.size(); z++) {

                    Result first = results1.get(x);
                    Result second = results2.get(y);
                    Result third = results3.get(z);

                    Journey journey = new Journey();
                    Period p = first.totalTime.plus(second.totalTime.plus(third.totalTime));
                    journey.totalTime = p;
                    journey.minutes = (int) (TimeUtils.convertToMilliseconds(p) / (60 * 1000));
                    journey.totalDistance = first.totalDistance + second.totalDistance + third.totalDistance;
                    journey.results.add(first);
                    journey.results.add(second);
                    journey.results.add(third);
                    journeys.add(journey);
                }
            }
        }

        return journeys;
    }

    public List<Journey> fourPoint1(CityMap map, Location start, Location end, String startAddress, String endAddress,
                             List<Location> middleLocations, List<String> middleAddresses,
                             LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK) {

        List<Result> results1 = twoPointAlgorithm.solveAndReturnObject(map, start, middleLocations.get(0), startAddress,
                endAddress, departureTime, walkingDistance, K, isOptimizeK);
        List<Result> results2 = twoPointAlgorithm.solveAndReturnObject(map, middleLocations.get(1), middleLocations.get(0), startAddress,
                endAddress, departureTime, walkingDistance, K, isOptimizeK);
        List<Result> results3 = twoPointAlgorithm.solveAndReturnObject(map, middleLocations.get(0), end, startAddress,
                endAddress, departureTime, walkingDistance, K, isOptimizeK);

        List<Journey> journeys = new ArrayList<Journey>();
        if (results1 == null || results2 == null || results3 == null) {
            Journey journey = new Journey();
            journey.code = "fail";
            journeys.add(journey);
            return journeys;
        }

        for (int x = 0; x < results1.size(); x++) {
            for (int y = 0; y < results2.size(); y++) {
                for (int z = 0; z < results3.size(); z++) {

                    Result first = results1.get(x);
                    Result second = results2.get(y);
                    Result third = results3.get(z);

                    Journey journey = new Journey();
                    Period p = first.totalTime.plus(second.totalTime.plus(third.totalTime));
                    journey.totalTime = p;
                    journey.minutes = (int) (TimeUtils.convertToMilliseconds(p) / (60 * 1000));
                    journey.totalDistance = first.totalDistance + second.totalDistance + third.totalDistance;
                    journey.results.add(first);
                    journey.results.add(second);
                    journey.results.add(third);
                    journey.code = "success";
                    journeys.add(journey);
                }
            }
        }

        return journeys;
    }

    public void sort(List<Journey> journeys) {
        Collections.sort(journeys, new Comparator<Journey>() {
            @Override
            public int compare(Journey j1, Journey j2) {
                if (j1.minutes == j2.minutes) {
                    return (int) (j1.totalDistance - j2.totalDistance);
                }

                return j1.minutes - j2.minutes;
            }
        });
    }

    public List<Journey> limitJourney(List<Journey> journeys) {
        if (journeys.size() > 5) {
            return journeys.subList(0, 5);
        }
        return journeys;
    }
}
