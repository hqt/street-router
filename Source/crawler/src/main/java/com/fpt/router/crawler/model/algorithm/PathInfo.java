package com.fpt.router.crawler.model.algorithm;

import com.fpt.router.crawler.model.helper.Location;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 9/26/2015.
 */
public class PathInfo implements IAlgorithmModel {

    @Override
    public void build() {

    }

    public static enum PathType {
        WALKING_BUS,
        CONNECTED_BUS
    }

    public long pathInfoId;

    public int pathInfoNo;

    public List<Location> middleLocations;

    public Route route;

    public Station from;

    public Station to;

    public List<Connection> connections;

    //endregion
    public PathInfo(){

    }
}
