package com.fpt.router.dal;

import com.fpt.router.dal.*;
import com.fpt.router.model.*;

import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/5/15.
 */
public class MapDAL {
    public static void insertDatabase(CityMap map) {
        //insert route and trip
        RouteDAL routeDAL = new RouteDAL();
        StationDAL stationDAL = new StationDAL();
        TripDAL tripDao = new TripDAL();
        PathInfoDAL pathInfoDAL = new PathInfoDAL();
        ConnectionDAL connectionDAL = new ConnectionDAL();
        //insert station
        List<Station> stations = map.getStations();
        for (Station station : stations) {
            System.out.println("Station : " + station.getName());
            stationDAL.insertStation(station);
        }

        List<Route> routes = map.getRoutes();
        for (Route route : routes) {
            System.out.println("Route : " + route.getRouteName());
            routeDAL.insertRoute(route);
            //insert pathinfor
            List<PathInfo> pathInfos = route.getPathInfos();
            for (PathInfo pathInfo : pathInfos) {
                System.out.println("Path Infor :" + pathInfo.getMiddleLocations());
                pathInfoDAL.insertPathInfo(pathInfo);
            }
            //insert trip - connection
            List<Trip> trips = route.getTrips();
            for (Trip trip : trips) {
                System.out.println("Trip : " + trip.getTripNo());
                List<Connection> connectionSet = trip.getConnections();
                for (Connection conn : connectionSet) {
                    System.out.println("Connection : " + conn.getArrivalTime());
                    connectionDAL.insertConnection(conn);
                }

            }

        }
    }

    public static CityMap readDatabase() {
        CityMap cityMap = new CityMap();

        StationDAL stationDAL = new StationDAL();
        RouteDAL routeDAL = new RouteDAL();
        TripDAL tripDao = new TripDAL();
        PathInfoDAL pathInfoDAL = new PathInfoDAL();
        List<Station> stations = stationDAL.getStations();
        cityMap.setStations(stations);

        /*List<Route> routes = new  ArrayList<Route>();*/

        List<Route> routes = routeDAL.getRoute();
        System.out.println("Route Size : "+routes.size());



        /*Route route = routeDao.getRouteId(1);
        ConnectionDao connectionDao = new ConnectionDao();

        List<Trip> trips = tripDao.getTripsWithId(route);
        for (Trip trip : trips){
            System.out.println("Trip : " + trip.getTripId());
            List<Connection> connectionList = connectionDao.getListConnectionWithTrip(trip);
            trip.setConnections(connectionList);

        }

        List<PathInfo> pathInfos = pathInfoDao.getPathInfo(route);
        route.setPathInfos(pathInfos);
        route.setTrips(trips);

        routes.add(route);*/
        cityMap.setRoutes(routes);
        /*cityMap.setRoutes(routes);*/
        return  cityMap;
    }
}
