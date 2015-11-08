package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.PathInfo;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Station;
import com.fpt.router.artifacter.model.entity.Trip;
import com.fpt.router.artifacter.model.helper.RouteType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 10/11/2015.
 */
public class RouteVM {
    private long routeId;
    private String routeName;
    private int routeNo;
    private RouteType routeType;
    private PathinfoListVM pathInfosVM;
    private TripListVM tripsVM;
    private StationListVM stationsVM;

    public RouteVM() {

    }

    public RouteVM(Route route) {
        this.routeId = route.getRouteId();
        this.routeNo = route.getRouteNo();
        this.routeName = route.getRouteName();
        this.routeType = route.getRouteType();
    }

    public RouteVM(com.fpt.router.artifacter.model.algorithm.Route route) {
        this.routeId = route.routeId;
        this.routeNo = route.routeNo;
        this.routeName = route.routeName;
        this.routeType = route.routeType;
    }

    public List<Station> convertPathInfoToStation(List<PathInfo> pathInfos) {
        List<Station> stations = new ArrayList<>();
        for (PathInfo p : pathInfos) {
            Station stationFrom = p.getFrom();
            stations.add(stationFrom);
        }
        return stations;
    }


    public PathinfoListVM getPathInfosVM() {
        return pathInfosVM;
    }

    public void setPathInfosVM(PathinfoListVM pathInfosVM) {
        this.pathInfosVM = pathInfosVM;
    }

    public TripListVM getTripsVM() {
        return tripsVM;
    }

    public void setTripsVM(TripListVM tripsVM) {
        this.tripsVM = tripsVM;
    }

    public StationListVM getStationsVM() {
        return stationsVM;
    }

    public void setStationsVM(StationListVM stationsVM) {
        this.stationsVM = stationsVM;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(int routeNo) {
        this.routeNo = routeNo;
    }

}
