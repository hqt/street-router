package com.fpt.router.artifacter.algorithm;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.algorithm.Station;
import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.artifacter.model.helper.PathType;
import com.fpt.router.artifacter.model.viewmodel.Path;
import com.fpt.router.artifacter.model.viewmodel.Result;
import com.fpt.router.artifacter.utils.DistanceUtils;
import com.fpt.router.artifacter.utils.TimeUtils;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Purpose: Find shortest bus route go through two points
 * Created by Huynh Quang Thao on 9/30/15.
 */
public class TwoPointAlgorithm {

    CityMap map;
    double walkingDistance;
    Location start;
    Location end;

    public String run(CityMap map, Location start, Location end, String startAddress, String endAddress,
                      LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK) {

        // assign to local variables
        this.map = map;
        this.start = start;
        this.end = end;
        this.walkingDistance = walkingDistance;

        // can walking
        if (DistanceUtils.distance(start, end) < 350) {
            return "{" +
                        "\"code\": \"success\"" +
                        "\"type:\":\"walking\"" +
                    "}";
        }

        List<Station> nearStartStations = findNearestStations(start);
        List<Station> nearEndStations = findNearestStations(end);

        String failMessage = "{" +
                                "\"code\": \"fail\"" +
                                "\"type:\":\"start location too far\"" +
                            "}";
        if (nearStartStations.size() == 0) {
            return failMessage;
        } else if (nearStartStations.size() > 3) {
            nearStartStations = nearStartStations.subList(0, 3);
        }
        if (nearEndStations.size() == 0) {
            return failMessage.replace("start", "end");
        } else if (nearEndStations.size() > 3) {
            nearEndStations = nearEndStations.subList(0, 3);
        }

        // brute force here
        List<Result> results = new ArrayList<Result>();
        for (Station fromStation : nearStartStations) {
            for (Station toStation : nearEndStations) {
                // create start path
                Path startPath = new Path();
                startPath.stationFromName = startAddress;
                startPath.stationToName = fromStation.name;
                startPath.type = PathType.WALKING;
                startPath.transferTurn = 0;
                startPath.distance = DistanceUtils.distance(start, fromStation.location);
                int millis = (int) (startPath.distance / Config.HUMAN_SPEED_M_S);
                startPath.time = new Period(millis);

                // create end path
                Path endPath = new Path();
                endPath.stationFromName = toStation.name;
                endPath.stationToName = endAddress;
                endPath.type = PathType.WALKING;
                endPath.transferTurn = 0;
                endPath.distance = DistanceUtils.distance(end, toStation.location);
                millis = (int) (endPath.distance / Config.HUMAN_SPEED_M_S);
                endPath.time = new Period(millis);

                RaptorAlgorithm algor = new RaptorAlgorithm();
                Result result = algor.run(map, fromStation, toStation, startPath, endPath, K, isOptimizeK, departureTime);

                if (result.code.equals("success")) {
                    results.add(result);
                }
            }
        }

        Collections.sort(results, new Comparator<Result>() {
            @Override
            public int compare(Result r1, Result r2) {
                return r1.minutes - r2.minutes;
            }
        });

        // convert this list to json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(results);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
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
