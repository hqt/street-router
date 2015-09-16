package com.fpt.router.model;

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
    public List<Stop> stops;
    public Map<Integer, Stop> stopIdMap;

    public CityMap() {
        routes = new ArrayList<Route>();
        stops = new ArrayList<Stop>();
        stopIdMap = new HashMap<Integer, Stop>();
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public Map<Integer, Stop> getStopIdMap() {
        return stopIdMap;
    }

    public void setStopIdMap(Map<Integer, Stop> stopIdMap) {
        this.stopIdMap = stopIdMap;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }
}
