package com.fpt.router.artifacter.model.entity;

import com.fpt.router.artifacter.model.helper.RouteType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 9/26/2015.
 */
@Entity
@Table(name = "Route")
public class Route implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RouteID", unique = true, nullable = false)
    private int routeId;
    @Column(name = "RouteNo", nullable = false)
    private int routeNo;
    @Column(name = "RouteType", nullable = false)
    private RouteType routeType;
    @Column(name = "RouteName", nullable = false)
    private String routeName;

    /**
     * relationship between trip and route
     */
    @OneToMany(mappedBy = "route")
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

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Route) {
            Route other = (Route) obj;
            return other.getRouteNo() == this.routeNo && other.getRouteType().equals(this.getRouteType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode;
        hashCode = this.routeNo * this.routeType.hashCode();
        return hashCode;
    }
}
