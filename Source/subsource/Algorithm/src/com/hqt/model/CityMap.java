package com.hqt.model;

import java.util.List;
import java.util.Map;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/20/15.
 */
public class CityMap {
    public List<Station> stations;
    public List<Route> routes;

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
