package com.fpt.router.model;

import com.fpt.router.dao.*;
import com.fpt.router.work.DemoDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by asus on 9/27/2015.
 */
public class MainTest {
    public static void main(String[] args){
        MainTest mainTest = new MainTest();
       /* mainTest.insertIntoDB();*/
        CityMap cityMap = mainTest.getCity();
        List<Station> stations = cityMap.getStations();
        for (Station station: stations){
            System.out.println("Station : "+station.getCodeId());
        }

        List<Route> routes = cityMap.getRoutes();
        for (Route route:routes){
            System.out.println("Route : "+route.getRouteId());
            List<Trip> trips = route.getTrips();
            for (Trip trip: trips){
                System.out.println("Trip :"+trip.getTripId());
                List<Connection> connectionList = trip.getConnections();
                for (Connection conn : connectionList){
                    System.out.println("Connection : "+conn.getId());
                }
            }
        }

    }


    public void insertIntoDB(){
        try{

            DemoDB demoDB = new DemoDB();
            CityMap cityMap = demoDB.cityMap;
            //insert route and trip
            RouteDao routeDao = new RouteDao();
            StationDao stationDao = new StationDao();
            TripDao tripDao = new TripDao();
            PathInfoDao pathInfoDao = new PathInfoDao();
            ConnectionDao connectionDao = new ConnectionDao();
            //insert station
            List<Station> stations = cityMap.getStations();
            for (Station station : stations){
                System.out.println("Station : " + station.getName());
                stationDao.insertStation(station);
            }




            List<Route> routes = cityMap.getRoutes();
            for (Route route :routes){
                System.out.println("Route : "+route.getRouteName());
                 routeDao.insertRoute(route);
                List<Trip> trips = route.getTrips();
                for (Trip trip : trips){
                    System.out.println("Trip : "+trip.getTripNo());
                    List<Connection> connectionSet = trip.getConnections();
                    for (Connection conn : connectionSet){
                        System.out.println("Connection : "+conn.getArrivalTime());
                        connectionDao.insertConnection(conn);
                    }

                }
                List<PathInfo> pathInfos = route.getPathInfos();
                for (PathInfo pathInfo: pathInfos){
                    System.out.println("Path Infor :"+pathInfo.getMiddleLocations());
                    pathInfoDao.insertPathInfo(pathInfo);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public CityMap getCity(){
        CityMap cityMap = new CityMap();

        StationDao stationDao = new StationDao();
        RouteDao routeDao = new RouteDao();
        TripDao tripDao = new TripDao();
        PathInfoDao pathInfoDao = new PathInfoDao();
        List<Station> stations = stationDao.getStations();
        cityMap.setStations(stations);

        /*List<Route> routes = new  ArrayList<Route>();*/

        List<Route> routes = routeDao.getRoute();
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
