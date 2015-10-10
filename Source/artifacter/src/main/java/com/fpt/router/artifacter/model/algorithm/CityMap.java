package com.fpt.router.artifacter.model.algorithm;

import java.util.*;

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
        // sort again stations by id
        Collections.sort(stations, new Comparator<Station>() {
            @Override
            public int compare(Station s1, Station s2) {
                return s1.id - s2.id;
            }
        });

        // buildIndex index for stationMap
        stationIdMap = new HashMap<Integer, Station>();
        for (Station station : stations) {
            if (stationIdMap.containsKey(station.id)) {
                System.out.println("buildIndex failed");
            } else {
                stationIdMap.put(station.id, station);
            }
        }

        // buildIndex index for routes
        routeIdMap = new HashMap<Integer, Route>();
        for (Route route : routes) {
            if (routeIdMap.containsKey(route.routeId)) {
                System.out.println("buildIndex failed");
            } else {
                routeIdMap.put(route.routeId, route);
            }
        }

    }

    // base on assumption that all stations must be started from 0 1 2 3 ...
    public void buildRouteThroughStation() {

        // initialize array
        List<List<Route>> routesInStation = new ArrayList<List<Route>>();
        for (int i = 0; i < stations.size(); i++) {
            routesInStation.add(new ArrayList<Route>());
        }

        // set routes for each index of station
        for (Route route : routes) {
            for (PathInfo pathInfo : route.pathInfos) {
                Station from = pathInfo.from;
                List<Route> routes = routesInStation.get(from.id);
                routes.add(route);
                routesInStation.set(from.id, routes);
            }
        }

        // put again those data to station
        for (int i = 0; i < routesInStation.size(); i++) {
            Station station = getStationById(i);
            station.routes = routesInStation.get(i);
        }
    }
}
