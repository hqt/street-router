package com.fpt.router.artifacter.algorithm;

import com.fpt.router.artifacter.algorithm.TwoPointAlgorithm.SearchType;
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
            List<Journey> journeys1 = fourPoint1(map, start, end, startAddress, endAddress, middleLocations, middleAddresses, departureTime, walkingDistance, K, isOptimizeK);

            String failMess = null;
            if (!journeys1.get(0).code.equals("success")) {
                failMess = journeys1.get(0).code;
            } else {
                journeys.addAll(journeys1);
            }

            List<Journey> journeys2 = fourPoint2(map, start, end, startAddress, endAddress, middleLocations, middleAddresses, departureTime, walkingDistance, K, isOptimizeK);
            if (!journeys2.get(0).code.equals("success") && failMess != null) {
                return journeys1;
            } else {
                journeys.addAll(journeys2);
            }
        }

        sort(journeys);

        return journeys;
    }

    public List<Journey> threePoint(CityMap map, Location start, Location end, String startAddress, String endAddress,
                             List<Location> middleLocations, List<String> middleAddresses,
                             LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK) {

        Object resultObj = twoPointAlgorithm.solveAndReturnObject(map, start, middleLocations.get(0), startAddress,
                endAddress, departureTime, walkingDistance, K, isOptimizeK, SearchType.THREE_POINT);

        List<Result> results1, results2;

        if (resultObj instanceof String) {
            String msg = (String) resultObj;
            Journey journey = new Journey();
            journey.code = msg;
            List<Journey> dummyJourneys = new ArrayList<Journey>();
            dummyJourneys.add(journey);
            return dummyJourneys;
        } else {
            results1 = (List<Result>) resultObj;
        }

        resultObj = twoPointAlgorithm.solveAndReturnObject(map, middleLocations.get(0), end, startAddress,
                endAddress, departureTime, walkingDistance, K, isOptimizeK, SearchType.THREE_POINT);

        if (resultObj instanceof String) {
            String msg = (String) resultObj;
            Journey journey = new Journey();
            journey.code = msg;
            List<Journey> dummyJourneys = new ArrayList<Journey>();
            dummyJourneys.add(journey);
            return dummyJourneys;
        } else {
            results2 = (List<Result>) resultObj;
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
                journey.code = "success";
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

        System.out.println("result 1. point 1: " + start.toString());
        System.out.println("result 1. point 2: " + middleLocations.get(1).toString());
        Object resultObj = twoPointAlgorithm.solveAndReturnObject(map, start, middleLocations.get(1), startAddress,
                middleAddresses.get(1), departureTime, walkingDistance, K, isOptimizeK, SearchType.FOUR_POINT);

        List<Result> results1, results2, results3;


        Journey journey = new Journey();
        List<Journey> dummyJourneys = new ArrayList<Journey>();
        dummyJourneys.add(journey);

        if (resultObj instanceof String) {
            String msg = (String) resultObj;
            journey.code = msg;
            return dummyJourneys;
        } else {
            results1 = (List<Result>) resultObj;
            if (results1.size() == 0) {
                journey.code = "result not found";
                return dummyJourneys;
            }
        }


        resultObj = twoPointAlgorithm.solveAndReturnObject(map, middleLocations.get(1), middleLocations.get(0), middleAddresses.get(1),
                middleAddresses.get(0), departureTime, walkingDistance, K, isOptimizeK, SearchType.FOUR_POINT);

        if (resultObj instanceof String) {
            String msg = (String) resultObj;
            journey.code = msg;
            return dummyJourneys;
        } else {
            results2 = (List<Result>) resultObj;
            if (results2.size() == 0) {
                journey.code = "result not found";
                return dummyJourneys;
            }
        }

        resultObj = twoPointAlgorithm.solveAndReturnObject(map, middleLocations.get(0), end, middleAddresses.get(0),
                endAddress, departureTime, walkingDistance, K, isOptimizeK, SearchType.FOUR_POINT);

        if (resultObj instanceof String) {
            String msg = (String) resultObj;
            journey.code = msg;
            return dummyJourneys;
        } else {
            results3 = (List<Result>) resultObj;
            if (results3.size() == 0) {
                journey.code = "result not found";
                return dummyJourneys;
            }
        }

        List<Journey> journeys = new ArrayList<Journey>();

        for (int x = 0; x < results1.size(); x++) {
            for (int y = 0; y < results2.size(); y++) {
                for (int z = 0; z < results3.size(); z++) {

                    Result first = results1.get(x);
                    Result second = results2.get(y);
                    Result third = results3.get(z);

                    journey = new Journey();
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

    public List<Journey> fourPoint1(CityMap map, Location start, Location end, String startAddress, String endAddress,
                             List<Location> middleLocations, List<String> middleAddresses,
                             LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK) {

        System.out.println("result 1. point 1: " + start.toString());
        System.out.println("result 1. point 2: " + middleLocations.get(0).toString());
        Object resultObj = twoPointAlgorithm.solveAndReturnObject(map, start, middleLocations.get(0), startAddress,
                middleAddresses.get(0), departureTime, walkingDistance, K, isOptimizeK, SearchType.FOUR_POINT);

        List<Result> results1, results2, results3;

        Journey journey = new Journey();
        List<Journey> dummyJourneys = new ArrayList<Journey>();
        dummyJourneys.add(journey);

        if (resultObj instanceof String) {
            String msg = (String) resultObj;
            journey.code = msg;
            return dummyJourneys;
        } else {
            results1 = (List<Result>) resultObj;
            if (results1.size() == 0) {
                journey.code = "result not found";
                return dummyJourneys;
            }
        }


        resultObj = twoPointAlgorithm.solveAndReturnObject(map, middleLocations.get(0), middleLocations.get(1), middleAddresses.get(0),
                middleAddresses.get(1), departureTime, walkingDistance, K, isOptimizeK, SearchType.FOUR_POINT);

        if (resultObj instanceof String) {
            String msg = (String) resultObj;
            journey.code = msg;
            return dummyJourneys;
        } else {
            results2 = (List<Result>) resultObj;
            if (results2.size() == 0) {
                journey.code = "result not found";
                return dummyJourneys;
            }
        }

        resultObj = twoPointAlgorithm.solveAndReturnObject(map, middleLocations.get(1), end, middleAddresses.get(1),
                endAddress, departureTime, walkingDistance, K, isOptimizeK, SearchType.FOUR_POINT);

        if (resultObj instanceof String) {
            String msg = (String) resultObj;
            journey.code = msg;
            return dummyJourneys;
        } else {
            results3 = (List<Result>) resultObj;
            if (results3.size() == 0) {
                journey.code = "result not found";
                return dummyJourneys;
            }
        }

        System.out.println("Size R1 " + results1.size());
        System.out.println("Size R2 " + results2.size());
        System.out.println("Size R3 " + results3.size());


        List<Journey> journeys = new ArrayList<Journey>();
        for (int x = 0; x < results1.size(); x++) {
            for (int y = 0; y < results2.size(); y++) {
                for (int z = 0; z < results3.size(); z++) {

                    Result first = results1.get(x);
                    Result second = results2.get(y);
                    Result third = results3.get(z);

                    journey = new Journey();
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
