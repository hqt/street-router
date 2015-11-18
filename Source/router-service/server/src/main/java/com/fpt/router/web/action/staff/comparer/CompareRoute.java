package com.fpt.router.web.action.staff.comparer;

import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.RouteNotification;
import com.fpt.router.artifacter.model.entity.TripNotification;
import com.fpt.router.artifacter.model.helper.RouteType;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by datnt on 11/14/2015.
 */
public class CompareRoute {

    private HashSet<Route> redudantRouteDB;
    private HashSet<Route> redudantRouteSource;
    private List<Route> listRouteDB;
    private List<Route> listRouteSource;
    public List<RouteNotification> listRouteNof;
    public List<TripNotification> listTripNof;

    public CompareRoute(List<Route> listRouteDB, List<Route> listRouteSource) {
        this.redudantRouteDB = new HashSet<Route>(listRouteDB);
        this.redudantRouteSource = new HashSet<Route>(listRouteSource);
        this.listRouteDB = listRouteDB;
        this.listRouteSource = listRouteSource;
        this.listRouteNof = new ArrayList<RouteNotification>();
        this.listTripNof = new ArrayList<TripNotification>();
    }

    public void run() {
        redudant();
        processRouteThread();
        int a = 3;
    }

    protected void redudant() {
        HashSet<Route> hsTotal = new HashSet<Route>();
        hsTotal.addAll(redudantRouteDB);
        hsTotal.addAll(redudantRouteSource);

        hsTotal.removeAll(redudantRouteDB);
        redudantRouteSource.removeAll(hsTotal);
        redudantRouteDB.removeAll(redudantRouteSource);

        redudantRouteSource = hsTotal;

        // split list route
        this.listRouteDB.removeAll(redudantRouteDB);
        this.listRouteSource.removeAll(redudantRouteSource);

    }

    protected void processRouteThread() {

        System.out.println("Main Comparison Route Thread starting...");

        List<Route> routesDbDepart = new ArrayList<Route>();
        List<Route> routesDbReturn = new ArrayList<Route>();
        for (Route route : this.listRouteDB) {
            if (route.getRouteType().equals(RouteType.DEPART)) {
                routesDbDepart.add(route);
            } else {
                routesDbReturn.add(route);
            }
        }

        List<Route> routesSourceDepart = new ArrayList<Route>();
        List<Route> routesSourceReturn = new ArrayList<Route>();
        for (Route route : this.listRouteSource) {
            if (route.getRouteType().equals(RouteType.DEPART)) {
                routesSourceDepart.add(route);
            } else {
                routesSourceReturn.add(route);
            }
        }

        // Begin execute route thread
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (Route routeDbDepart : routesDbDepart) {
            for (Route routeSourceDepart : routesSourceDepart) {

                // compare route
                CompareThreadRoute compareThreadRoute = new CompareThreadRoute(routeDbDepart, routeSourceDepart);
                executorService.execute(compareThreadRoute);

                // compare trip in route
                CompareTrip compareTrip = new CompareTrip(routeDbDepart.getTrips(), routeSourceDepart.getTrips());
                compareTrip.run();
                listTripNof.addAll(compareTrip.listTripNof);
            }
        }

        for (Route routeDbReturn : routesDbReturn) {
            for (Route routeSourceReturn : routesSourceReturn) {

                // compare route
                CompareThreadRoute compareThreadRoute = new CompareThreadRoute(routeDbReturn, routeSourceReturn);
                executorService.execute(compareThreadRoute);

                // compare trip in route
                CompareTrip compareTrip = new CompareTrip(routeDbReturn.getTrips(), routeSourceReturn.getTrips());
                compareTrip.run();
                listTripNof.addAll(compareTrip.listTripNof);
            }
        }
        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Main Route Thread interrupted!!!");
            e.printStackTrace();
        }
        // End execute route thread

        System.out.println("Main Compare Route Thread finished.");
    }

    private class CompareThreadRoute implements Runnable {

        private Route routeDB;
        private Route routeSource;

        public CompareThreadRoute(Route routeDB, Route routeSource) {
            this.routeDB = routeDB;
            this.routeSource = routeSource;
        }

        @Override
        public void run() {
            compareRoute();
        }

        public void compareRoute() {

            if (routeDB.getRouteNo() != routeSource.getRouteNo()) {
                return;
            }

            boolean canAdd = false;
            RouteNotification routeNof = new RouteNotification();
            if (!Objects.equals(routeDB.getRouteName(), routeSource.getRouteName())) {
                routeNof.setChangeRouteName(routeSource.getRouteName());
                canAdd = true;
            }

            if (canAdd) {
                routeNof.setRoute(routeDB);
                routeNof.setCreatedTime(new Date());
                listRouteNof.add(routeNof);
            }

            // test thread
        }
    }
}