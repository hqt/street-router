package com.hqt.model;

import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/20/15.
 */
public class Station {
    // for improving performance when do algorithm
    public int id;
    public String code;
    public String name;
    public String street;
    public Location location;

    // list all routes go through this station
    public List<Route> routes;

    // list all foot-path stations go through this station
    public List<Station> stations;
}
