package com.fpt.router.algorithm;

import com.fpt.router.model.CityMap;
import com.fpt.router.model.Location;
import com.fpt.router.model.Route;

import java.util.*;

/**
 * Purpose: Raptor Algorithm Implementation
 * Created by Huynh Quang Thao on 9/6/15.
 */
public class RaptorAlgorithm {

    CityMap map;

    public String run(Location start, Location end, String startAddress, String endAddress) {
       return null;
    }

    // number of change bus
    int k;
    // limit route for each turn. just care stop that can be hop on
    List<Integer> markedStopIds;
    // list for storing pair(rId, pId) for traversing purpose. Using Map for searching effective
    Map<Integer, Integer> Q = new HashMap<Integer, Integer>();
    // List<Pair<Integer, Integer>> Q = new ArrayList<Pair<Integer, Integer>>();

    private void initialize() {
        markedStopIds = new ArrayList<Integer>();

    }

    public void raptor() {

        for (int transitNo = 0; transitNo < k; transitNo++) {

            // step 1. find the  first stop that satisfy condition
            for (Integer p : markedStopIds) {
                // all routes that go through this stop
                for (Route route : map.stopIdMap.get(p).routes) {
                    if (Q.containsKey(route.routeId)) {
                        int _p = Q.get(route.routeId);
                        // _p come after p in sequence of route. Fucking wrong here !!!!!
                        if (_p > p) {
                            Q.put(route.routeId, p);
                        } else {
                            Q.put(route.routeId, p);
                        }
                    }
                }
            }

            // step 2. traversal each route
            for (Map.Entry<Integer, Integer> pair : Q) {

            }
        }
    }
}
