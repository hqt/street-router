package com.fpt.router.crawler.dao;

import com.fpt.router.crawler.database.DBUtils;
import com.fpt.router.crawler.model.algorithm.CityMap;
import com.fpt.router.crawler.model.algorithm.PathInfo;
import com.fpt.router.crawler.utils.DTOConverter;

import java.sql.*;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/5/15.
 */
public class MapDAL {

    public static void insertDatabase(com.fpt.router.crawler.model.entity.CityMap map) {
        //insert route and trip
        RouteDAO routeDAO = new RouteDAO();
        StationDAO stationDAL = new StationDAO();
        TripDAO tripDao = new TripDAO();
        PathInfoDAO pathInfoDAO = new PathInfoDAO();
        ConnectionDAO connectionDAO = new ConnectionDAO();

        //insert station
        List<com.fpt.router.crawler.model.entity.Station> stations = map.getStations();
        for (com.fpt.router.crawler.model.entity.Station station : stations) {
            System.out.println("Insert Station : " + station.getName());
            stationDAL.create(station);
        }

        System.out.println("------------------------------------------");

        List<com.fpt.router.crawler.model.entity.Route> routes = map.getRoutes();
        for (com.fpt.router.crawler.model.entity.Route route : routes) {
            System.out.println("Route : " + route.getRouteName());
            routeDAO.create(route);
            //insert PathInfo
            List<com.fpt.router.crawler.model.entity.PathInfo> pathInfos = route.getPathInfos();
            for (com.fpt.router.crawler.model.entity.PathInfo pathInfo : pathInfos) {
                if (pathInfo.getTo() != null) {
                    System.out.println("\tPath Info: From " + pathInfo.getFrom().getName() +
                            "To: " + pathInfo.getTo().getName());
                } else {
                    System.out.println("\tPath Info: From " + pathInfo.getFrom().getName() +
                            "To: Null");
                }

                pathInfoDAO.create(pathInfo);
            }

            //insert trip - connection
            List<com.fpt.router.crawler.model.entity.Trip> trips = route.getTrips();
            for (com.fpt.router.crawler.model.entity.Trip trip : trips) {
                System.out.println("Trip : " + trip.getTripNo() +
                        "Start time: " + trip.getStartTime() + " End Time: " + trip.getEndTime());
                tripDao.create(trip);
                for (com.fpt.router.crawler.model.entity.Connection conn : trip.getConnections()) {
                    System.out.println("\t\tConnection : " + conn.getArrivalTime());
                    connectionDAO.create(conn);
                }
            }
        }
    }

    public static com.fpt.router.crawler.model.algorithm.CityMap readDatabase() {
        CityMap cityMap = new CityMap();

        StationDAO stationDAL = new StationDAO();
        RouteDAO routeDAO = new RouteDAO();
        TripDAO tripDao = new TripDAO();
        PathInfoDAO pathInfoDAO = new PathInfoDAO();

        // all stationMap
        List<com.fpt.router.crawler.model.entity.Station> entityStations = stationDAL.findAll();
        cityMap.stations = DTOConverter.convertStations(entityStations);

        // all routes
        List<com.fpt.router.crawler.model.entity.Route> entityRoutes = routeDAO.findAll();
        cityMap.routes = DTOConverter.convertRoutes(entityRoutes);

        cityMap.buildIndex();

        for (com.fpt.router.crawler.model.entity.Route route : entityRoutes) {
            // all pathinfos has been loaded due to hibernate properties
            // testing purpose
            System.out.println("f: " + route.getPathInfos().size());
            com.fpt.router.crawler.model.entity.PathInfo pathInfo = route.getPathInfos().get(0);
            System.out.println("connection size: " + pathInfo.getConnections().size());
            System.out.println("shit "  + pathInfo.getConnections().get(0).getArrivalTime());
        }

        return cityMap;
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
