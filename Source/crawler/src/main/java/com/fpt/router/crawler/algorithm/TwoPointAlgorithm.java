package com.fpt.router.crawler.algorithm;


import com.fpt.router.crawler.model.algorithm.CityMap;
import com.fpt.router.crawler.model.algorithm.Station;
import com.fpt.router.crawler.model.helper.Location;
import com.fpt.router.crawler.model.viewmodel.Result;
import com.fpt.router.crawler.utils.DistanceUtils;

import java.util.ArrayList;
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

    public String run(Location start, Location end, String startAddress, String endAddress, double walkingDistance) {

        // assign to local variables
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
                RaptorAlgorithm algor = new RaptorAlgorithm();

            }
        }

        // convert this list to json
        return null;

    }


    /** find list of nearest stations from this station */
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
