package com.fpt.router.model;

import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/7/15.
 */
public class Connection {
    public Stop from;
    public Stop to;
    public double distance;
    public double time;
    public List<Location> middleLocations;
}
