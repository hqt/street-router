package com.fpt.router.model;

import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/5/15.
 */
public class Station {
    // for improving performance when do algorithm
    public int id;
    public String code;
    public String name;
    public String street;
    public Location location;

    // all routes through this stop
    public List<Route> routes;

    /*public List<Route> routes;

    public List<PathInfo> pathInfos;

    public Station() {
        pathInfos = new ArrayList<PathInfo>();
        routes = new ArrayList<Route>();
    }

    // because number of paths from one station to other stations often max is 5. so we can use list for searching
    public boolean existPath(Station to) {
        // fucking wrong here !!! we should compare two "to" vertex is duplicated
        *//*for (PathInfo path : pathInfos) {
            if (path.to.code.equals(to.code)) {
                return true;
            }
        }*//*
        for (PathInfo path : pathInfos) {
            if (path.to.code.equals(to.code)) {
                return true;
            }
        }
        return false;
    }

    public boolean existRoute(Route r) {
        for (Route route : routes) {
            if (r.routeId == route.routeId) return true;
        }
        return false;
    }*/

    @Override
    public boolean equals(Object that) {
        if (that == null) return false;
        if (this == that) return true;
        return this.code.equals(((Station)that).code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
