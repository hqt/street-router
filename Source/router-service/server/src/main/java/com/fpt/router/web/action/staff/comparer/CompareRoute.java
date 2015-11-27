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

    private HashSet<Route> redundantRouteDB;
    private HashSet<Route> redundantRouteSource;
    private List<Route> listRouteDB;
    private List<Route> listRouteSource;
    public List<RouteNotification> listRouteNof;
    public List<TripNotification> listTripNof;

    public CompareRoute(List<Route> listRouteDB, List<Route> listRouteSource) {
        this.redundantRouteDB = new HashSet<Route>(listRouteDB);
        this.redundantRouteSource = new HashSet<Route>(listRouteSource);
        this.listRouteDB = listRouteDB;
        this.listRouteSource = listRouteSource;
        this.listRouteNof = new ArrayList<RouteNotification>();
        this.listTripNof = new ArrayList<TripNotification>();
    }

    public void run() {
        processRedundant();
        processRouteThread();
    }

    public void processRedundant() {
        redundant();
        for (Route route : redundantRouteDB) {
            RouteNotification routeNof = convertRoute(route);
            routeNof.setRoute(route);
            routeNof.setType(2);
            this.listRouteNof.add(routeNof);
        }
        for (Route route : redundantRouteSource) {
            RouteNotification routeNof = convertRoute(route);
            routeNof.setType(1);
            this.listRouteNof.add(routeNof);
        }
    }

    public RouteNotification convertRoute(Route route) {
        RouteNotification routeNof = new RouteNotification();
        routeNof.setChangeRouteName(route.getRouteName());
        routeNof.setRouteNo(route.getRouteNo());
        routeNof.setRouteType(route.getRouteType());
        routeNof.setCreatedTime(new Date());
        return routeNof;
    }

    protected void redundant() {
        HashSet<Route> hsTotal = new HashSet<Route>();
        hsTotal.addAll(redundantRouteDB);
        hsTotal.addAll(redundantRouteSource);

        hsTotal.removeAll(redundantRouteDB);
        redundantRouteSource.removeAll(hsTotal);
        redundantRouteDB.removeAll(redundantRouteSource);

        redundantRouteSource = hsTotal;

        // split list route
        this.listRouteDB.removeAll(redundantRouteDB);
        this.listRouteSource.removeAll(redundantRouteSource);

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
        // ------------- Begin Thread Compare DEPART Route ------------------- //
        for (Route routeDbDepart : routesDbDepart) {
            for (Route routeSourceDepart : routesSourceDepart) {

                // compare route
                CompareThreadRoute compareThreadRoute = new CompareThreadRoute(routeDbDepart, routeSourceDepart);
                executorService.execute(compareThreadRoute);

                // compare trip in same route
                if (routeDbDepart.getRouteNo() == routeSourceDepart.getRouteNo()) {
                    CompareTrip compareTrip = new CompareTrip(routeDbDepart.getTrips(), routeSourceDepart.getTrips());
                    compareTrip.run();
                    listTripNof.addAll(compareTrip.listTripNof);
                }
            }
        }
        // ------------- End Thread Compare DEPART Route ------------------- //

        // ------------- Begin Thread Compare RETURN Route ------------------- //
        for (Route routeDbReturn : routesDbReturn) {
            for (Route routeSourceReturn : routesSourceReturn) {

                // compare route
                CompareThreadRoute compareThreadRoute = new CompareThreadRoute(routeDbReturn, routeSourceReturn);
                executorService.execute(compareThreadRoute);

                // compare trip in same route
                if (routeDbReturn.getRouteNo() == routeSourceReturn.getRouteNo()) {
                    CompareTrip compareTrip = new CompareTrip(routeDbReturn.getTrips(), routeSourceReturn.getTrips());
                    compareTrip.run();
                    listTripNof.addAll(compareTrip.listTripNof);
                }
            }
        }
        // ------------- End Thread Compare RETURN Route ------------------- //

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

            if (routeDB.getRouteNo() != routeSource.getRouteNo() || !routeDB.getRouteType().equals(routeSource.getRouteType())) {
                return;
            }
            System.out.println("Compare Route: " + routeDB.getRouteName() + " - " + routeSource.getRouteName());

            boolean canAdd = false;
            RouteNotification routeNof = new RouteNotification();
            if (!Objects.equals(routeDB.getRouteName(), routeSource.getRouteName())) {
                routeNof.setChangeRouteName(routeSource.getRouteName());
                canAdd = true;
            }

            if (canAdd) {
                routeNof.setRoute(routeDB);
                routeNof.setRouteNo(routeSource.getRouteNo());
                routeNof.setRouteType(routeSource.getRouteType());
                routeNof.setType(0);
                routeNof.setCreatedTime(new Date());
                listRouteNof.add(routeNof);
            }

            // If you see this comment again, it's mean you have to test thread trip inside route thread.
        }
    }
}