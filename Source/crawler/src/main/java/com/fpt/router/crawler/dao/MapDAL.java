package com.fpt.router.crawler.dao;

import com.fpt.router.crawler.database.DBUtils;
import com.fpt.router.crawler.model.algorithm.*;
import com.fpt.router.crawler.model.helper.Location;
import com.fpt.router.crawler.utils.DTOConverter;
import com.fpt.router.crawler.utils.DistanceUtils;
import com.fpt.router.crawler.utils.TimeUtils;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
        cityMap.stations = DTOConverter.convertStations(cityMap, entityStations);

        // all routes
        List<com.fpt.router.crawler.model.entity.Route> entityRoutes = routeDAO.findAll();
        cityMap.routes = DTOConverter.convertRoutes(cityMap, entityRoutes);

        cityMap.buildIndex();

        // all PathInfos
        for (int i = 0; i < entityRoutes.size(); i++) {
            com.fpt.router.crawler.model.entity.Route entityRoute = entityRoutes.get(i);
            Route route = cityMap.routes.get(i);
            if (route.routeId != entityRoute.getRouteId()) {
                System.out.println("wrong parsing");
            }
            List<com.fpt.router.crawler.model.entity.PathInfo> entityPathInfos = entityRoute.getPathInfos();
            route.pathInfos = DTOConverter.convertPathInfos(cityMap, entityPathInfos);
        }

        // all trips
        for (int i = 0; i < entityRoutes.size(); i++) {
            com.fpt.router.crawler.model.entity.Route entityRoute = entityRoutes.get(i);
            Route route = cityMap.routes.get(i);
            List<com.fpt.router.crawler.model.entity.Trip> entityTrips = entityRoute.getTrips();
            route.trips = DTOConverter.convertTrips(cityMap, entityTrips);
        }

        // build again connections for saving time than loading from database
        buildConnections(cityMap);

        return cityMap;
    }

    // city map has enough information for building again connections
    private static void buildConnections(CityMap map) {

        for (Route r: map.routes){

            double totalDistance = DistanceUtils.distance(r);

            // find distance of pathInfo
            List<Double> pathInfoDistances = new ArrayList<Double>();
            for (PathInfo pathInfo : r.pathInfos) {
                if (pathInfo.to == null) continue;
                Location startLocation = new Location(pathInfo.from.location.latitude, pathInfo.from.location.longitude);
                Location endLocation = new Location(pathInfo.to.location.latitude, pathInfo.to.location.longitude);
                double pathInfoDistance = DistanceUtils.distanceTwoLocation(startLocation, endLocation, pathInfo.middleLocations);
                pathInfoDistances.add(pathInfoDistance);
            }

            // create connections for each trip
            for (Trip trip : r.trips) {
                List<Connection> connections = new ArrayList<Connection>();

                if (r.routeNo == 613) continue;
                if (r.routeNo == 75) continue;

                if (trip == null || trip.startTime == null || trip.endTime == null) {
                    int a = 3;
                }
                Period totalTravelTime = Period.fieldDifference(trip.startTime, trip.endTime);
                long totalMillis = TimeUtils.convertToMilliseconds(totalTravelTime);

                // for each pathInfo. Create one connection base on PathInfo length
                for (int i = 0; i < pathInfoDistances.size(); i++) {
                    // time for this pathInfo
                    long pathInfoTravel = (long) (totalMillis * pathInfoDistances.get(i) / totalDistance);
                    Period pathInfoTravelPeriod = new Period(pathInfoTravel);

                    // create connection.
                    // Base on our business, previous bus departure time == next bus arrival time
                    Connection connection = new Connection();
                    connection.trip = trip;
                    connection.pathInfo = r.pathInfos.get(i);
                    if (i == 0) {
                        connection.arrivalTime = trip.startTime;
                    } else {
                        connection.arrivalTime = connections.get(i-1).departureTime;
                    }
                    if (i == (pathInfoDistances.size()-1)) {
                        connection.departureTime = trip.endTime;
                    } else {
                        LocalTime departureTime = connection.arrivalTime.plus(pathInfoTravelPeriod);
                        connection.departureTime = departureTime;
                    }

                    connections.add(connection);
                    trip.connections = connections;
                }
            }
        }
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
