package com.fpt.router.crawler.dao;

import com.fpt.router.crawler.database.DBUtils;
import com.fpt.router.crawler.model.entity.*;
import com.fpt.router.crawler.model.entity.Connection;

import java.sql.*;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/5/15.
 */
public class MapDAL {

    public static void insertDatabase(CityMap map) {
        //insert route and trip
        RouteDAO routeDAO = new RouteDAO();
        StationDAO stationDAL = new StationDAO();
        TripDAO tripDao = new TripDAO();
        PathInfoDAO pathInfoDAO = new PathInfoDAO();
        ConnectionDAO connectionDAO = new ConnectionDAO();

        //insert station
        List<Station> stations = map.getStations();
        for (Station station : stations) {
            System.out.println("Insert Station : " + station.getName());
            stationDAL.create(station);
        }

        System.out.println("------------------------------------------");

        List<Route> routes = map.getRoutes();
        for (Route route : routes) {
            System.out.println("Route : " + route.getRouteName());
            routeDAO.create(route);
            //insert PathInfo
            List<PathInfo> pathInfos = route.getPathInfos();
            for (PathInfo pathInfo : pathInfos) {
                System.out.println("\tPath Info: From " + pathInfo.getFrom().getName() +
                        "To: " + pathInfo.getTo().getName());
                pathInfoDAO.create(pathInfo);
            }

            //insert trip - connection
            List<Trip> trips = route.getTrips();
            for (Trip trip : trips) {
                System.out.println("Trip : " + trip.getTripNo() +
                            "Start time: " + trip.getStartTime() + " End Time: " + trip.getEndTime());
                tripDao.create(trip);
                for (Connection conn : trip.getConnections()) {
                    System.out.println("Connection : " + conn.getArrivalTime());
                    connectionDAO.create(conn);
                }
            }
        }
    }

    public static CityMap readDatabase() {
        CityMap cityMap = new CityMap();

        StationDAO stationDAL = new StationDAO();
        RouteDAO routeDAO = new RouteDAO();
        TripDAO tripDao = new TripDAO();
        PathInfoDAO pathInfoDAO = new PathInfoDAO();
        List<Station> stations = stationDAL.findAll();
        cityMap.setStations(stations);

        /*List<Route> routes = new  ArrayList<Route>();*/

        List<Route> routes = routeDAO.findAll();
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

    public static void deleteDatabase() {
        java.sql.Connection conn = null;
        try {
            conn = DBUtils.getConnection();

            String sql = "Truncate table Connection";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM Trip";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM PathInfo";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM Route";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM Station";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
