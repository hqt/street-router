package com.fpt.router.crawler.model.viewmodel;

import com.fpt.router.crawler.model.helper.Location;

import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class Path {
    public String stationFromName;
    public String stationToName;
    public int transferTurn;
    public String street;
    public String type;
    public int routeNo;
    public List<Location> points;
}
