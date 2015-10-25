package com.fpt.router.artifacter.algorithm;


import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.config.Config.NearBusStationLimit;
import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.algorithm.Station;
import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.artifacter.model.helper.PathType;
import com.fpt.router.artifacter.model.viewmodel.Path;
import com.fpt.router.artifacter.model.viewmodel.Result;
import com.fpt.router.artifacter.utils.DistanceUtils;
import com.fpt.router.artifacter.utils.JSONUtils;
import com.fpt.router.artifacter.utils.NoResultHelper;
import com.google.gson.Gson;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.util.*;

/**
 * Purpose: Find shortest bus route go through two points
 * Created by Huynh Quang Thao on 9/30/15.
 */
public class TwoPointAlgorithm {

    public static enum SearchType {
        TWO_POINT,
        THREE_POINT,
        FOUR_POINT,
        FOUR_POINT_OPT
    }

    CityMap map;
    double walkingDistance;
    Location start;
    Location end;
    String startAddress;
    String endAddress;
    int K;
    boolean isOptimizeK;
    LocalTime departureTime;
    List<Result> results;
    String message;

    List<Station> nearStartStations;
    List<Station> nearEndStations;

    final boolean isOptimizeVersion = true;



    public Object solveAndReturnObject(CityMap map, Location start, Location end, String startAddress, String endAddress,
                      LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK, SearchType searchType) {
        solve(map, start, end, startAddress, endAddress, departureTime, walkingDistance, K, isOptimizeK, searchType);
        if (message != null) {
           return message;
        } else {
            return results;
        }
    }

    public String solveAndReturnJSon(CityMap map, Location start, Location end, String startAddress, String endAddress,
                      LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK, SearchType searchType) {

        solve(map, start, end, startAddress, endAddress, departureTime, walkingDistance, K, isOptimizeK, searchType);

        if (message != null) {
            // return message;
            Gson gson = JSONUtils.buildGson();
            return gson.toJson(NoResultHelper.NoJourneyFound(message));
        }

        Gson gson = JSONUtils.buildGson();
        String json = gson.toJson(results);
        return json;

    }

    public void solve(CityMap map, Location start, Location end, String startAddress, String endAddress,
                      LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK, SearchType searchType) {
        // assign to local variables
        this.map = map;
        this.start = start;
        this.end = end;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.walkingDistance = walkingDistance;
        this.isOptimizeK = isOptimizeK;
        this.departureTime = departureTime;
        this.K = K;

        // prevent testing at midnight :)
        this.departureTime = new LocalTime(12, 0, 0);

        // can walking
        if (DistanceUtils.distance(start, end) < Config.WALKING_DISTANCE) {
            message = "có thể đi bộ trực tiếp giữa hai điểm";
            return;
        }

        nearStartStations = findNearestStations(start);
        nearEndStations = findNearestStations(end);

        if (nearStartStations.size() == 0) {
            message= "không có trạm xe buýt nào gần " + startAddress;
            return;
        }
        if (nearEndStations.size() == 0) {
            message = "không có trạm xe buýt nào gần " + endAddress;
            return;
        }

        results = new ArrayList<Result>();
        if (isOptimizeVersion) {
            solveUsingRaptorOptimize(searchType);
        } else {
            solveUsingRaptorClassical(searchType);
        }

        // handle different than multi point algorithm :)
        if (results.size() == 0) {
            message = "Không tìm được tuyến xe buýt từ " + startAddress + " đến " + endAddress;
            return;
        }

        // sort priority
        // 1. transfer turn
        // 2. travel time
        // 3. travel distance
        Collections.sort(results, new Comparator<Result>() {
            @Override
            public int compare(Result r1, Result r2) {
                if (r1.totalTransfer != r2.totalTransfer) {
                    return r1.totalTransfer - r2.totalTransfer;
                }

                if (r1.minutes != r2.minutes) {
                    return r1.minutes - r2.minutes;
                }

                return (int) (r1.totalDistance - r2.totalDistance);
            }
        });

        if (results.size() > Config.BUS_RESULT_LIMIT) {
            results = results.subList(0, Config.BUS_RESULT_LIMIT);
        }

    }

    private void solveUsingRaptorClassical(SearchType searchType) {
        // choose limit stations. for optimize time to runClassical.
        int busStationLimit = 0;
        switch (searchType) {
            case TWO_POINT:
                busStationLimit = NearBusStationLimit.HIGH;
                break;
            case THREE_POINT:
                busStationLimit = NearBusStationLimit.HIGH;
                break;
            case FOUR_POINT:
                busStationLimit = NearBusStationLimit.MEDIUM;
                break;
            case FOUR_POINT_OPT:
                busStationLimit = NearBusStationLimit.MEDIUM;
                break;
        }
        System.out.println("limit bus station limit: " + busStationLimit);

        System.out.println("near start stations size: " + nearStartStations.size());
        if (nearStartStations.size() > busStationLimit) {
            nearStartStations = nearStartStations.subList(0, busStationLimit);
        }


        System.out.println("near end stations size: " + nearEndStations.size());
        if (nearEndStations.size() > busStationLimit) {
            nearEndStations = nearEndStations.subList(0, busStationLimit);
        }

        // brute force here
        results = new ArrayList<Result>();
        for (Station fromStation : nearStartStations) {
            for (Station toStation : nearEndStations) {
                // create start path
                Path startPath = new Path();
                startPath.stationFromName = startAddress;
                startPath.stationToName = fromStation.name;
                startPath.pathType = PathType.WALKING;
                startPath.stationFromLocation = start;
                startPath.stationToLocation = fromStation.location;
                startPath.transferTurn = -1;
                startPath.distance = DistanceUtils.distance(start, fromStation.location);
                int millis = (int) (startPath.distance / Config.HUMAN_SPEED_M_S) * 1000;
                startPath.time = new Period(millis);

                // create end path
                Path endPath = new Path();
                endPath.stationFromName = toStation.name;
                endPath.stationToName = endAddress;
                endPath.pathType = PathType.WALKING;
                // delay assign endPath.stationFromLocation to Raptor algorithm
                endPath.stationToLocation = end;
                endPath.transferTurn = -2;
                endPath.distance = DistanceUtils.distance(end, toStation.location);
                millis = (int) (endPath.distance / Config.HUMAN_SPEED_M_S) * 1000;
                endPath.time = new Period(millis);

                if (fromStation.id == 1445 && toStation.id == 466) {
                    int c = 3;
                }

                RaptorAlgorithm algor = new RaptorAlgorithm();
                Result result = algor.runClassical(map, fromStation, toStation, startPath, endPath,
                        K, isOptimizeK, departureTime);

                if (result.code.equals(Config.CODE.SUCCESS)) {
                    results.add(result);
                }
            }
        }
    }

    private void solveUsingRaptorOptimize(SearchType searchType) {

        // because this is optimize version. we just care about start near station
        int limit = -1;
        switch (searchType) {
            case FOUR_POINT:
                limit = 12;
                break;
            case FOUR_POINT_OPT:
                limit = 10;
                break;
        }

        if ((limit != -1) && (nearStartStations.size() > limit)) {
            nearStartStations = nearStartStations.subList(0, limit);
        }

        System.out.println("Searching from : " + startAddress + " to " + endAddress);


        // brute force here
        results = new ArrayList<Result>();
        for (Station fromStation : nearStartStations) {
            // create start path
            Path startPath = new Path();
            startPath.stationFromName = startAddress;
            startPath.stationToName = fromStation.name;
            startPath.pathType = PathType.WALKING;
            startPath.transferTurn = -1;
            startPath.stationFromLocation = start;
            startPath.stationToLocation = fromStation.location;
            startPath.distance = DistanceUtils.distance(start, fromStation.location);
            int millis = (int) (startPath.distance / Config.HUMAN_SPEED_M_S) * 1000;
            startPath.time = new Period(millis);

            // build a list of all end stations
            Map<Integer, Station> endStations = new HashMap<Integer, Station>();
            Map<Integer, Path> endPaths = new HashMap<Integer, Path>();

            for (Station endStation : nearEndStations) {
                // create end path
                Path endPath = new Path();
                endPath.stationFromName = endStation.name;
                endPath.stationToName = endAddress;
                endPath.pathType = PathType.WALKING;
                // delay assign endPath.stationFromLocation to Raptor algorithm
                endPath.stationToLocation = end;
                endPath.transferTurn = -2;
                endPath.distance = DistanceUtils.distance(end, endStation.location);
                millis = (int) (endPath.distance / Config.HUMAN_SPEED_M_S) * 1000;
                endPath.time = new Period(millis);

                endStations.put(endStation.id, endStation);
                endPaths.put(endStation.id, endPath);
            }

            RaptorAlgorithm algorithm = new RaptorAlgorithm();
            List<Result> middleResults = algorithm.runOpt(map, fromStation, endStations, startPath, endPaths,
                    K, isOptimizeK, departureTime);
            results.addAll(middleResults);
        }
    }


    /** find list of nearest stationMap from this station */
    private List<Station> findNearestStations(Location location) {
        List<Station> res = new ArrayList<Station>();
        for (Station station : map.stations) {
            if (DistanceUtils.distance(location, station.location) <= walkingDistance) {
                res.add(station);
            }
        }
        return res;
    }
}
