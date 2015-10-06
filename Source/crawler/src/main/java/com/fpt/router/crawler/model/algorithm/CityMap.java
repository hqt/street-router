package com.fpt.router.crawler.model.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/5/15.
 */
public class CityMap {
    public List<Route> routes;
    public List<Station> stations;

    public CityMap() {
        routes = new ArrayList<Route>();
        stations = new ArrayList<Station>();
    }

    // index for stationMap
    public Map<Integer, Station> stationIdMap;

    // index for routes. routeId = (routeNo, routeType)
    public Map<Integer, Route> routeIdMap;

    public Station getStationById(int stationId) {
        return stationIdMap.get(stationId);
    }

    public Route getRouteById(int routeId) {
        return routeIdMap.get(routeId);
    }

    public void buildIndex() {
        // build index for stationMap
        stationIdMap = new HashMap<Integer, Station>();
        for (Station station : stations) {
            if (stationIdMap.containsKey(station.id)) {
                System.out.println("build failed");
            } else {
                stationIdMap.put(station.id, station);
            }
        }

        // build index for routes
        routeIdMap = new HashMap<Integer, Route>();
        for (Route route : routes) {
            if (routeIdMap.containsKey(route.routeId)) {
                System.out.println("build failed");
            } else {
                routeIdMap.put(route.routeId, route);
            }
        }
    }
}
