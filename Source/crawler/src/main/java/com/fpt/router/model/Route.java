package com.fpt.router.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by asus on 9/26/2015.
 */
@Entity
@Table(name = "Route")
public class Route {
    public enum RouteType{
        DEPART,
        RETURN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RouteID", unique = true, nullable = false)
    private long routeId;
    @Column(name = "RouteNo", nullable = false)
    private int routeNo;
    @Column(name = "RouteType", nullable = false)
    private RouteType routeType;
    @Column(name = "RouteName", nullable = false)
    private String routeName;

    /**
     * relationship between trip and route
     */
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<Trip> trips;

    @OneToMany(mappedBy = "route")
    private List<PathInfo> pathInfos = new ArrayList<PathInfo>();


    public Route(){
        trips = new ArrayList<Trip>();
    }

    public Route(int routeNo, RouteType routeType, String routeName) {
        this.routeNo = routeNo;
        this.routeType = routeType;
        this.routeName = routeName;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public int getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(int routeNo) {
        this.routeNo = routeNo;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<PathInfo> getPathInfos() {
        return pathInfos;
    }

    public void setPathInfos(List<PathInfo> pathInfos) {
        this.pathInfos = pathInfos;
    }
}
