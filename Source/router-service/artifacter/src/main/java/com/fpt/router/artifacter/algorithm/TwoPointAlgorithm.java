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
import com.google.gson.Gson;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    List<Result> results;
    String message;



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
            return message;/*
            List<Result> dummyResults = new ArrayList<Result>();
            Result result = new Result();
            result.code = message;
            dummyResults.add(result);
            Gson gson = JSONUtils.buildGson();
            String json = gson.toJson(dummyResults);
            return json;*/
        }

        /*// convert this list to json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(results);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

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
        this.walkingDistance = walkingDistance;
        this.startAddress = startAddress;
        this.endAddress = endAddress;

        // prevent testint at midnight :)
        departureTime = new LocalTime(8, 0, 0);

        // can walking
        if (DistanceUtils.distance(start, end) < Config.WALKING_DISTANCE) {
            message = "có thể đi bộ trực tiếp giữa hai điểm";
            return;
        }

        List<Station> nearStartStations = findNearestStations(start);
        List<Station> nearEndStations = findNearestStations(end);

        // choose limit stations. for optimize time to run.
        int busStationLimit = 0;
        switch (searchType) {
            case TWO_POINT:
                System.out.println("two point");
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


        if (nearStartStations.size() == 0) {
            message= "không có trạm xe buýt nào gần " + startAddress;
            return;
        } else if (nearStartStations.size() > busStationLimit) {
            System.out.println("near stations size: " + nearStartStations.size());
            nearStartStations = nearStartStations.subList(0, busStationLimit);
        }
        if (nearEndStations.size() == 0) {
            message = "không có trạm xe buýt nào gần " + endAddress;
            return;
        } else if (nearEndStations.size() > busStationLimit) {
            System.out.println("near stations size: " + nearEndStations.size());
            nearEndStations = nearEndStations.subList(0,busStationLimit);
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
                startPath.transferTurn = -1;
                startPath.distance = DistanceUtils.distance(start, fromStation.location);
                int millis = (int) (startPath.distance / Config.HUMAN_SPEED_M_S) * 1000;
                startPath.time = new Period(millis);

                // create end path
                Path endPath = new Path();
                endPath.stationFromName = toStation.name;
                endPath.stationToName = endAddress;
                endPath.pathType = PathType.WALKING;
                endPath.transferTurn = -2;
                endPath.distance = DistanceUtils.distance(end, toStation.location);
                millis = (int) (endPath.distance / Config.HUMAN_SPEED_M_S) * 1000;
                endPath.time = new Period(millis);

                RaptorAlgorithm algor = new RaptorAlgorithm();
                Result result = algor.run(map, fromStation, toStation, startPath, endPath, K, isOptimizeK, departureTime);

                if (result.code.equals(Config.CODE.SUCCESS)) {
                    results.add(result);
                }
            }
        }

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
