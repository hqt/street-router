package com.fpt.router.artifacter.model.viewmodel;

import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.artifacter.model.helper.PathType;
import org.joda.time.Period;

import java.util.List;

/**
 * Purpose: A Path is a detail information from stationA to stationB in one journey
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class Path implements INode {
    public int stationFromId;
    public int stationToId;
    public String stationFromName;
    public String stationToName;
    public Location stationFromLocation;
    public Location stationToLocation;
    public int transferTurn;
    public PathType pathType;
    public int routeNo;
    public double distance;
    public Period time;     // in minute
    public List<Location> points;
}
