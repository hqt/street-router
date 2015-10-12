package com.fpt.router.artifacter.model.algorithm;

import com.fpt.router.artifacter.model.helper.Location;
import org.joda.time.Period;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 9/26/2015.
 */
public class PathInfo implements IAlgorithmModel {

    @Override
    public void build() {

    }

    public int pathInfoId;

    public int pathInfoNo;

    public List<Location> middleLocations;

    // has been calculated when creating connections
    public double distance;

    public Route route;

    public Station from;

    public Station to;

    // average time go to this PathInfo
    public Period cost;

    public List<Connection> connections;

    //endregion
    public PathInfo(){

    }
}
