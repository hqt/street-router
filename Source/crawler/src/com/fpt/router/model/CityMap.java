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
    public List<Station> stations;
    public Map<Integer, Station> stationIdMap;

    public CityMap() {
        routes = new ArrayList<Route>();
        stations = new ArrayList<Station>();
        stationIdMap = new HashMap<Integer, Station>();
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public Map<Integer, Station> getStationIdMap() {
        return stationIdMap;
    }

    public void setStationIdMap(Map<Integer, Station> stationIdMap) {
        this.stationIdMap = stationIdMap;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}
