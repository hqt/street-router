package com.fpt.router.crawler.model.viewmodel;

import com.fpt.router.crawler.model.helper.Location;

import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class Path {
    public String name;
    public String street;
    public Location location;
    public String type;
    public double distance;
    public double time;
    public int routeNo;
    public List<Location> points;
}
