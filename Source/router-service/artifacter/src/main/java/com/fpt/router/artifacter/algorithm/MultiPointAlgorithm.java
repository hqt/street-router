package com.fpt.router.artifacter.algorithm;

import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.helper.Location;
import org.joda.time.LocalTime;

import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/30/15.
 */
public class MultiPointAlgorithm {

    CityMap map;
    double walkingDistance;
    Location start;
    Location end;

    public String run(CityMap map, Location start, Location end, String startAddress, String endAddress,
                      List<Location> middleLocations, List<String> addresses,
                      LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK) {

        return null;
    }
}
