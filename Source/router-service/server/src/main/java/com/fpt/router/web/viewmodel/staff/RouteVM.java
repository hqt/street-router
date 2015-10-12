package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.Route;

/**
 * Created by datnt on 10/11/2015.
 */
public class RouteVM {
    private long routeId;
    private String routeName;
    private int routeNo;


    public RouteVM() {

    }


    public RouteVM(Route route) {
        this.routeId = route.getRouteId();
        this.routeNo = route.getRouteNo();
        this.routeName = route.getRouteName();
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
