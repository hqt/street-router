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
}
