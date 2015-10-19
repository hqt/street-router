package com.fpt.router.web.viewmodel.staff;


import com.fpt.router.artifacter.model.entity.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 10/11/2015.
 */
public class RouteListVM {

    private int numberOfItems;
    private List<RouteVM> routeListVMs;

    public RouteListVM() {

    }

    public List<RouteVM> getRouteListVMs() {
        return routeListVMs;
    }

    public void setRouteListVMs(List<RouteVM> routeListVMs) {
        this.routeListVMs = routeListVMs;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public RouteListVM (List<Route> routes) {
        routeListVMs = new ArrayList<RouteVM>();
        for (int i = 0; i < routes.size(); i++) {
            routeListVMs.add(new RouteVM(routes.get(i)));
        }
        /*for (Route route : routes) {
            routeListVMs.add(new RouteVM(route));
        }*/
    }
}
