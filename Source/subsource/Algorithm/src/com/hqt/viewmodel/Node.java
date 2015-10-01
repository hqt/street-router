package com.hqt.viewmodel;

import com.hqt.model.Location;

import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/30/15.
 */
public class Node {
    public String name;
    public String street;
    public Location location;
    public String type;
    public double distance;
    public double time;
    public int routeNo;
    public List<Location> points;
}
