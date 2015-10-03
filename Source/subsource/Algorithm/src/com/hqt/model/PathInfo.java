package com.hqt.model;

import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/20/15.
 */
public class PathInfo {
    public static enum PathType {
        WALKING_BUS,
        CONNECTED_BUS,
    }

    public Route route;
    public PathType type;
    public Station from;
    public Station to;
    // time cost to go from A to
    public double time;
    // distance between A and B
    public double distance;
    // support for  drawing
    public List<Location> middleLocations;
}
