package com.fpt.router.artifacter.utils;

import com.fpt.router.artifacter.model.algorithm.*;
import com.fpt.router.artifacter.model.helper.Location;

import java.util.ArrayList;
import java.util.List;

/**
 *  Purpose:*Created by Huynh Quang Thao on 10/4/15.
*/
public class DTOConverter {
    public static void convertRoute(CityMap map, com.fpt.router.artifacter.model.entity.Route entityRoute, Route algorRoute) {
        algorRoute.routeId = (int) entityRoute.getRouteId();
        algorRoute.routeNo = entityRoute.getRouteNo();
        algorRoute.routeName = entityRoute.getRouteName();
        algorRoute.routeType = entityRoute.getRouteType();
        // algorRoute.trips = convertTrips(map, entityRoute.getTrips());
        // algorRoute.pathInfos = convertPathInfos(map, entityRoute.getPathInfos());
    }

    public static void convertStation(CityMap map, com.fpt.router.artifacter.model.entity.Station entityStation, Station algorStation) {
        algorStation.id = (int) entityStation.getStationId();
        algorStation.code = entityStation.getCodeId();
        algorStation.name = entityStation.getName();
        algorStation.street = entityStation.getStreet();
        algorStation.location = new Location(entityStation.getLatitude(), entityStation.getLongitude());
        // algorStation.from = entityStation.getFrom();
        // algorStation.to = entityStation.getTo();
    }


    public static void convertPathInfo(CityMap map, com.fpt.router.artifacter.model.entity.PathInfo entityPathInfo, PathInfo algorPathInfo) {
        algorPathInfo.pathInfoId = entityPathInfo.getPathInfoId();
        algorPathInfo.pathInfoNo = entityPathInfo.getPathInfoNo();
        algorPathInfo.middleLocations = StringUtils.convertToLocations(entityPathInfo.getMiddleLocations());
        algorPathInfo.route = map.getRouteById((int) entityPathInfo.getRoute().getRouteId());
        algorPathInfo.from = map.getStationById((int) entityPathInfo.getFrom().getStationId());
        if (entityPathInfo.getTo() != null) {
            algorPathInfo.to = map.getStationById((int) entityPathInfo.getTo().getStationId());
        } else {
            algorPathInfo.to = null;
        }

        // algorPathInfo.connections = entityPathInfo.getConnections();
    }

    public static void convertTrip(CityMap map, com.fpt.router.artifacter.model.entity.Trip entityTrip, Trip algorTrip) {
        algorTrip.tripId = entityTrip.getTripId();
        algorTrip.tripNo = entityTrip.getTripNo();
        algorTrip.startTime = entityTrip.getStartTime();
        algorTrip.endTime = entityTrip.getEndTime();
        algorTrip.route = map.getRouteById((int) entityTrip.getRoute().getRouteId());
        // algorTrip.connections = entityTrip.getConnections();
    }


    public static void convertConnection(CityMap map, com.fpt.router.artifacter.model.entity.Connection entityConnection, Connection algorConnection) {
        algorConnection.id = entityConnection.getId();
        // algorConnection.trip = entityConnection.getTrip();
        // algorConnection.pathInfo = entityConnection.getPathInfo();
        // algorConnection.arrivalTime = TimeUtils.convert(entityConnection.getArrivalTime());
    }

    public static List<Trip> convertTrips(CityMap map, List<com.fpt.router.artifacter.model.entity.Trip> entityTrips) {
        List<Trip> res = new ArrayList<Trip>();
        for (com.fpt.router.artifacter.model.entity.Trip entityTrip : entityTrips) {
            Trip trip = new Trip();
            convertTrip(map, entityTrip, trip);
            res.add(trip);
        }
        return res;
    }

    public static List<PathInfo> convertPathInfos(CityMap map, List<com.fpt.router.artifacter.model.entity.PathInfo> PathInfos) {
        List<PathInfo> res = new ArrayList<PathInfo>();
        for (com.fpt.router.artifacter.model.entity.PathInfo entityPathInfo : PathInfos) {
            PathInfo pathInfo = new PathInfo();
            convertPathInfo(map, entityPathInfo, pathInfo);
            res.add(pathInfo);
        }
        return res;
    }

    public static List<Station> convertStations(CityMap map, List<com.fpt.router.artifacter.model.entity.Station> stations) {
        List<Station> res = new ArrayList<Station>();
        for (com.fpt.router.artifacter.model.entity.Station entityStation : stations) {
            Station station = new Station();
            convertStation(map, entityStation, station);
            res.add(station);
        }
        return res;
    }

    public static List<Route> convertRoutes(CityMap map, List<com.fpt.router.artifacter.model.entity.Route> routes) {
        List<Route> res = new ArrayList<Route>();
        for (com.fpt.router.artifacter.model.entity.Route entityRoute : routes) {
            Route route = new Route();
            convertRoute(map, entityRoute, route);
            res.add(route);
        }
        return res;
    }
}
