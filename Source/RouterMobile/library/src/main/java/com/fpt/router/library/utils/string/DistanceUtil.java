package com.fpt.router.library.utils.string;

/**
 * Created by ngoan on 10/27/2015.
 */
public class DistanceUtil {
    public static double distance(double lat1, double lon1, double lat2,
                                  double lon2) {

        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else
            return distance(lat1, lon1, lat2, lon2, 'K');
    }

    public static double distance(double lat1, double lon1, double lat2,
                                  double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
