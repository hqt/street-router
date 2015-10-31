package com.fpt.router.library.model.bus;



import com.fpt.router.library.model.entity.PathType;
import com.fpt.router.library.model.common.Location;

import org.joda.time.Period;

import java.io.Serializable;
import java.util.List;

/**
 * Purpose: A Path is a detail information from stationA to stationB in one journey
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class Path implements INode,Serializable {
    public int stationFromId;
    public int stationToId;
    public String stationFromName;
    public String stationToName;
    public Location stationFromLocation;
    public Location stationToLocation;
    public int transferTurn;
    public PathType type;
    public int routeNo;
    public double distance;
    public Period time;     // in minute
    public List<Location> points;
}
