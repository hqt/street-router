package com.fpt.router.web.action.builder;

import com.fpt.router.artifacter.model.entity.Connection;
import com.fpt.router.artifacter.model.entity.PathInfo;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Trip;
import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.artifacter.utils.DistanceUtils;
import com.fpt.router.artifacter.utils.StringUtils;
import com.fpt.router.artifacter.utils.TimeUtils;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 11/26/2015.
 */
public class BuildUtils {

    public void buildCon(Route route, Trip trip) {

        double totalDistance = DistanceUtils.distance(route);

        // find segmentDistance of pathInfo
        List<Double> pathInfoDistances = new ArrayList<Double>();
        for (PathInfo pathInfo : route.getPathInfos()) {
            // this connection is zero-length
            if (pathInfo.getTo() == null) {
                pathInfoDistances.add(0.0);
                continue;
            }
            Location startLocation = new Location(pathInfo.getFrom().getLatitude(), pathInfo.getFrom().getLongitude());
            Location endLocation = new Location(pathInfo.getTo().getLatitude(), pathInfo.getTo().getLongitude());
            double pathInfoDistance = DistanceUtils.distanceTwoLocation(startLocation, endLocation,
                    StringUtils.convertToLocations(pathInfo.getMiddleLocations()));
            pathInfoDistances.add(pathInfoDistance);
        }

        List<Connection> connections = new ArrayList<Connection>();

        // count total totalTime for traveling whole trip
        if ((trip == null) || (trip.getStartTime() == null) || (trip.getEndTime() == null)) {
            int a = 3;
        }

        Period totalTravelTime = Period.fieldDifference(trip.getStartTime(), trip.getEndTime());
        long totalMillis = TimeUtils.convertToMilliseconds(totalTravelTime);

        // for each pathInfo. Create one connection base on PathInfo length
        for (int i = 0; i < pathInfoDistances.size(); i++) {
            // totalTime for this pathInfo
            long pathInfoTravel = (long) (totalMillis * pathInfoDistances.get(i) / totalDistance);
            Period pathInfoTravelPeriod = new Period(pathInfoTravel);

            // create connection.
            // Base on our business, previous bus departure totalTime == next bus arrival totalTime
            Connection connection = new Connection();
            connection.setTrip(trip);
            connection.setPathInfo(route.getPathInfos().get(i));
            if (i == 0) {
                connection.setArrivalTime(trip.getStartTime());
            } else {
                connection.setArrivalTime(connections.get(i-1).getDepartureTime());
            }
            if (i == (pathInfoDistances.size()-1)) {
                connection.setDepartureTime(trip.getEndTime());
            } else {

                LocalTime departureTime = connection.getArrivalTime().plus(pathInfoTravelPeriod);
                connection.setDepartureTime(departureTime);
            }

            connections.add(connection);
            trip.setConnections(connections);
        }
    }
}
