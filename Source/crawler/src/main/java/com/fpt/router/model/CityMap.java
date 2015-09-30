package com.fpt.router.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/5/15.
 */
public class CityMap {
    private List<Route> routes;
    private List<Station> stations;

    public CityMap() {
        routes = new ArrayList<Route>();
        stations = new ArrayList<Station>();
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}
