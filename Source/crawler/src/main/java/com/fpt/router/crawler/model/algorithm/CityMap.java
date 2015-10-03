package com.fpt.router.crawler.model.algorithm;

import java.util.ArrayList;
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

    // index for stations
    public Map<Integer, Station> stationIdMap;

    // index for routes. routeId = (routeNo, routeType)
    public Map<Integer, Route> routeIdMap;

    public Station getStationById(int stationId) {
        return stationIdMap.get(stationId);
    }

    public Route getRouteById(int routeId) {
        return routeIdMap.get(routeId);
    }

}
