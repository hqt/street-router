package com.fpt.router.model;

import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/7/15.
 */
public class PathInfo {
    public Station from;
    public Station to;
    public List<Location> middleLocations;
    public int order;


    public double distance;
    public double time;
}
