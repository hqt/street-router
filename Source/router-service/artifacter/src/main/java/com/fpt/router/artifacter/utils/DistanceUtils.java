package com.fpt.router.artifacter.utils;

import com.fpt.router.artifacter.model.entity.PathInfo;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.helper.Location;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class DistanceUtils {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double distance(Location l1, Location l2) {
        return distance(l1.latitude, l2.latitude, l1.longitude, l2.longitude, 0, 0);
    }
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public static double distanceTwoLocation(Location from, Location to, List<Location> middleLocations) {
        double distance;

        if ((from == null || to == null)) {
            return 0.0f;
        }

        if ((middleLocations == null) || (middleLocations.size() == 0)) {
            return distance(from, to);
        }

        // counting two pivot segmentDistance
        distance = distance(from, middleLocations.get(0));
        distance += distance(middleLocations.get(middleLocations.size()-1), to);

        for (int i = 0; i < middleLocations.size()-1; i++) {
            distance += distance(middleLocations.get(i), middleLocations.get(i+1));
        }

        return distance;
    }

    public static double distance(Route route) {
        List<PathInfo> pathInfos = route.getPathInfos();

        double distance = 0.0f;
        for (PathInfo pathInfo : pathInfos) {
            if (pathInfo.getTo() == null) continue;
            List<Location> locations = StringUtils.convertToLocations(pathInfo.getMiddleLocations());
            Location from = new Location(pathInfo.getFrom().getLatitude(), pathInfo.getFrom().getLongitude());
            Location to = new Location(pathInfo.getTo().getLatitude(), pathInfo.getTo().getLongitude());
            distance += distanceTwoLocation(from, to, locations);
        }
        return distance;
    }

    public static double distance(com.fpt.router.artifacter.model.algorithm.Route route) {
        List<com.fpt.router.artifacter.model.algorithm.PathInfo> pathInfos = route.pathInfos;

        double distance = 0.0f;
        for (com.fpt.router.artifacter.model.algorithm.PathInfo pathInfo : pathInfos) {
            if (pathInfo.to == null) continue;
            Location from = new Location(pathInfo.from.location.latitude, pathInfo.from.location.longitude);
            Location to = new Location(pathInfo.to.location.latitude, pathInfo.to.location.longitude);
            distance += distanceTwoLocation(from, to, pathInfo.middleLocations);
        }
        return distance;
    }

}
