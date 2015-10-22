package com.fpt.router.web.action.staff;

import com.fpt.router.artifacter.model.entity.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 10/12/2015.
 */
public class abcd {

    public static void main(String args[]) {

        Route route = new Route();
        route.setRouteName("asdasd");
        route.setRouteId(123);

        List<Route> routes = new ArrayList<>();
        routes.add(route);
        routes.add(route);
        routes.add(route);
        routes.add(route);
        routes.add(route);
        routes.add(route);
        routes.add(route);
        routes.add(route);
        routes.add(route);
        routes.add(route);
        routes.add(route);

        for (int i = 0; i < routes.size(); i = i + 2) {
            subRoute(routes, i);
        }

        System.out.println("Done demo!");
    }

    // below function for demo validate sub list and this function to add to helper
    public static List<Route> subRoute(List<Route> routes, int index) {

        int last = index + 2;

        if (last > routes.size()) {
            last = routes.size();
        }
        // sub list route
        List<Route> subRoute = routes.subList(index, last);
        toString(subRoute);
        return subRoute;
    }



    public static void toString(List<Route> subRoute) {
        /*for (Route r : subRoute) {
            System.out.println(r.getRouteId() + " - " + r.getRouteName());
        }
        System.out.println("*****************************");*/

        System.out.println("Ngô Tiến Đạt");
    }
}
