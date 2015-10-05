package com.fpt.router.crawler.model.viewmodel;

import com.fpt.router.crawler.model.helper.Location;
import com.fpt.router.crawler.model.helper.PathType;

import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class Path {
    public String stationFromName;
    public String stationToName;
    public int transferTurn;
    public PathType type;
    public int routeNo;
    public double distance;
    public double time;     // in minute
    public List<Location> points;
}
