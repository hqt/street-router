package com.fpt.router.crawler.algorithm;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fpt.router.crawler.config.Config;
import com.fpt.router.crawler.model.algorithm.CityMap;
import com.fpt.router.crawler.model.algorithm.Station;
import com.fpt.router.crawler.model.helper.Location;
import com.fpt.router.crawler.model.helper.PathType;
import com.fpt.router.crawler.model.viewmodel.Path;
import com.fpt.router.crawler.model.viewmodel.Result;
import com.fpt.router.crawler.utils.DistanceUtils;
import com.fpt.router.crawler.utils.TimeUtils;
import org.joda.time.LocalTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Purpose:
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

        List<Station> nearStartStations = findNearestStations(start);
        List<Station> nearEndStations = findNearestStations(end);

        if (nearStartStations.size() == 0) {
            return "start location too far";
        } else if (nearStartStations.size() > 2) {
            nearStartStations = nearStartStations.subList(0, 2);
        }
        if (nearEndStations.size() == 0) {
            return "end location too far";
        } else if (nearEndStations.size() > 2) {
            nearEndStations = nearEndStations.subList(0, 2);
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
                // startPath.totalTime = TimeUtils.toMinute(startPath.totalDistance / Config.BUS_SPEED);

                // create end path
                Path endPath = new Path();
                endPath.stationFromName = toStation.name;
                endPath.stationToName = endAddress;
                endPath.type = PathType.WALKING;
                endPath.transferTurn = 0;
                endPath.distance = DistanceUtils.distance(end, toStation.location);
                // endPath.totalTime = TimeUtils.toMinute(endPath.totalDistance / Config.BUS_SPEED);

                RaptorAlgorithm algor = new RaptorAlgorithm();
                Result result = algor.run(map, fromStation, toStation, startPath, endPath, K, isOptimizeK, departureTime);
                results.add(result);
            }
        }

        Collections.sort(results, new Comparator<Result>() {
            @Override
            public int compare(Result r1, Result r2) {
                return 0;
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
