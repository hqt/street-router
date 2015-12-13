package com.fpt.router.utils;

import android.util.Log;

import com.fpt.router.library.utils.DecodeUtils;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import static java.lang.Math.*;

/**
 * Created by Nguyen Trung Nam on 11/13/2015.
 */
public class PolyLineUtils {

    public static boolean isLocationOnEdgeOrPath(LatLng point, List<LatLng> poly, boolean closed,
                                                  boolean geodesic, double toleranceEarth) {
        int size = poly.size();
        if (size == 0) {
            return false;
        }
        double tolerance = toleranceEarth / EARTH_RADIUS;
        double havTolerance = hav(tolerance);
        double lat3 = toRadians(point.latitude);
        double lng3 = toRadians(point.longitude);
        LatLng prev = poly.get(closed ? size - 1 : 0);
        double lat1 = toRadians(prev.latitude);
        double lng1 = toRadians(prev.longitude);
        if (geodesic) {
            for (LatLng point2 : poly) {
                double lat2 = toRadians(point2.latitude);
                double lng2 = toRadians(point2.longitude);
                if (isOnSegmentGC(lat1, lng1, lat2, lng2, lat3, lng3, havTolerance)) {
                    return true;
                }
                lat1 = lat2;
                lng1 = lng2;
            }
        } else {
            // We project the points to mercator space, where the Rhumb segment is a straight line,
            // and compute the geodesic distance between point3 and the closest point on the
            // segment. This method is an approximation, because it uses "closest" in mercator
            // space which is not "closest" on the sphere -- but the error is small because
            // "tolerance" is small.
            double minAcceptable = lat3 - tolerance;
            double maxAcceptable = lat3 + tolerance;
            double y1 = mercator(lat1);
            double y3 = mercator(lat3);
            double[] xTry = new double[3];
            for (LatLng point2 : poly) {
                double lat2 = toRadians(point2.latitude);
                double y2 = mercator(lat2);
                double lng2 = toRadians(point2.longitude);
                if (max(lat1, lat2) >= minAcceptable && min(lat1, lat2) <= maxAcceptable) {
                    // We offset longitudes by -lng1; the implicit x1 is 0.
                    double x2 = wrap(lng2 - lng1, -PI, PI);
                    double x3Base = wrap(lng3 - lng1, -PI, PI);
                    xTry[0] = x3Base;
                    // Also explore wrapping of x3Base around the world in both directions.
                    xTry[1] = x3Base + 2 * PI;
                    xTry[2] = x3Base - 2 * PI;
                    for (double x3 : xTry) {
                        double dy = y2 - y1;
                        double len2 = x2 * x2 + dy * dy;
                        double t = len2 <= 0 ? 0 : clamp((x3 * x2 + (y3 - y1) * dy) / len2, 0, 1);
                        double xClosest = t * x2;
                        double yClosest = y1 + t * dy;
                        double latClosest = inverseMercator(yClosest);
                        double havDist = havDistance(lat3, latClosest, x3 - xClosest);
                        if (havDist < havTolerance) {
                            return true;
                        }
                    }
                }
                lat1 = lat2;
                lng1 = lng2;
                y1 = y2;
            }
        }
        return false;
    }

    public static double distanceToLine(final LatLng p, final LatLng start, final LatLng end) {
        if (start.equals(end)) {
            DecodeUtils.calculateDistance(end, p);
        }

        final double s0lat = toRadians(p.latitude);
        final double s0lng = toRadians(p.longitude);
        final double s1lat = toRadians(start.latitude);
        final double s1lng = toRadians(start.longitude);
        final double s2lat = toRadians(end.latitude);
        final double s2lng = toRadians(end.longitude);

        double s2s1lat = s2lat - s1lat;
        double s2s1lng = s2lng - s1lng;
        final double u = ((s0lat - s1lat) * s2s1lat + (s0lng - s1lng) * s2s1lng)
                / (s2s1lat * s2s1lat + s2s1lng * s2s1lng);
        if (u <= 0) {
            return DecodeUtils.calculateDistance(p, start);
        }
        if (u >= 1) {
            return DecodeUtils.calculateDistance(p, end);
        }
        LatLng sa = new LatLng(p.latitude - start.latitude, p.longitude - start.longitude);
        LatLng sb = new LatLng(u * (end.latitude - start.latitude), u * (end.longitude - start.longitude));
        return DecodeUtils.calculateDistance(sa, sb);
    }

    private static boolean isOnSegmentGC(double lat1, double lng1, double lat2, double lng2,
                                         double lat3, double lng3, double havTolerance) {
        double havDist13 = havDistance(lat1, lat3, lng1 - lng3);
        if (havDist13 <= havTolerance) {
            return true;
        }
        double havDist23 = havDistance(lat2, lat3, lng2 - lng3);
        if (havDist23 <= havTolerance) {
            return true;
        }
        double sinBearing = sinDeltaBearing(lat1, lng1, lat2, lng2, lat3, lng3);
        double sinDist13 = sinFromHav(havDist13);
        double havCrossTrack = havFromSin(sinDist13 * sinBearing);
        if (havCrossTrack > havTolerance) {
            return false;
        }
        double havDist12 = havDistance(lat1, lat2, lng1 - lng2);
        double term = havDist12 + havCrossTrack * (1 - 2 * havDist12);
        if (havDist13 > term || havDist23 > term) {
            return false;
        }
        if (havDist12 < 0.74) {
            return true;
        }
        double cosCrossTrack = 1 - 2 * havCrossTrack;
        double havAlongTrack13 = (havDist13 - havCrossTrack) / cosCrossTrack;
        double havAlongTrack23 = (havDist23 - havCrossTrack) / cosCrossTrack;
        double sinSumAlongTrack = sinSumFromHav(havAlongTrack13, havAlongTrack23);
        return sinSumAlongTrack > 0;  // Compare with half-circle == PI using sign of sin().
    }

    private static double sinDeltaBearing(double lat1, double lng1, double lat2, double lng2,
                                          double lat3, double lng3) {
        double sinLat1 = sin(lat1);
        double cosLat2 = cos(lat2);
        double cosLat3 = cos(lat3);
        double lat31 = lat3 - lat1;
        double lng31 = lng3 - lng1;
        double lat21 = lat2 - lat1;
        double lng21 = lng2 - lng1;
        double a = sin(lng31) * cosLat3;
        double c = sin(lng21) * cosLat2;
        double b = sin(lat31) + 2 * sinLat1 * cosLat3 * hav(lng31);
        double d = sin(lat21) + 2 * sinLat1 * cosLat2 * hav(lng21);
        double denom = (a * a + b * b) * (c * c + d * d);
        return denom <= 0 ? 1 : (a * d - b * c) / sqrt(denom);
    }
    /**
     * The earth's radius, in meters.
     * Mean radius as defined by IUGG.
     */
    static final double EARTH_RADIUS = 6371009;

    /**
     * Restrict x to the range [low, high].
     */
    static double clamp(double x, double low, double high) {
        return x < low ? low : (x > high ? high : x);
    }

    /**
     * Wraps the given value into the inclusive-exclusive interval between min and max.
     *
     * @param n   The value to wrap.
     * @param min The minimum.
     * @param max The maximum.
     */
    static double wrap(double n, double min, double max) {
        return (n >= min && n < max) ? n : (mod(n - min, max - min) + min);
    }

    /**
     * Returns the non-negative remainder of x / m.
     *
     * @param x The operand.
     * @param m The modulus.
     */
    static double mod(double x, double m) {
        return ((x % m) + m) % m;
    }

    /**
     * Returns mercator Y corresponding to latitude.
     * See http://en.wikipedia.org/wiki/Mercator_projection .
     */
    static double mercator(double lat) {
        return log(tan(lat * 0.5 + PI / 4));
    }

    /**
     * Returns latitude from mercator Y.
     */
    static double inverseMercator(double y) {
        return 2 * atan(exp(y)) - PI / 2;
    }

    /**
     * Returns haversine(angle-in-radians).
     * hav(x) == (1 - cos(x)) / 2 == sin(x / 2)^2.
     */
    static double hav(double x) {
        double sinHalf = sin(x * 0.5);
        return sinHalf * sinHalf;
    }

    /**
     * Computes inverse haversine. Has good numerical stability around 0.
     * arcHav(x) == acos(1 - 2 * x) == 2 * asin(sqrt(x)).
     * The argument must be in [0, 1], and the result is positive.
     */
    static double arcHav(double x) {
        return 2 * asin(sqrt(x));
    }

    // Given h==hav(x), returns sin(abs(x)).
    static double sinFromHav(double h) {
        return 2 * sqrt(h * (1 - h));
    }

    // Returns hav(asin(x)).
    static double havFromSin(double x) {
        double x2 = x * x;
        return x2 / (1 + sqrt(1 - x2)) * .5;
    }

    // Returns sin(arcHav(x) + arcHav(y)).
    static double sinSumFromHav(double x, double y) {
        double a = sqrt(x * (1 - x));
        double b = sqrt(y * (1 - y));
        return 2 * (a + b - 2 * (a * y + b * x));
    }

    /**
     * Returns hav() of distance from (lat1, lng1) to (lat2, lng2) on the unit sphere.
     */
    static double havDistance(double lat1, double lat2, double dLng) {
        return hav(lat1 - lat2) + hav(dLng) * cos(lat1) * cos(lat2);
    }

    public static boolean checkSquare (LatLng tail, LatLng head, LatLng check) {
        double A[] = MathUtils.changeLatLngToXY(tail);
        double B[] = MathUtils.changeLatLngToXY(head);
        double C[] = MathUtils.changeLatLngToXY(check);
        if(((A[0] >= C[0]) && (C[0]>= B[0])) ||
                ((A[1] >= C[1]) && (C[1]>= B[1])) ||
                ((A[0] <= C[0]) && (C[0]<= B[0])) ||
                ((A[1] <= C[1]) && (C[1]<= B[1]))) {
            return true;
        }
        return false;
    }

    public static boolean isOnRoute (LatLng tail, LatLng head, LatLng check, double distance) {
        boolean isSquare = checkSquare(tail, head, check);
        if(isSquare) {
            if ((distanceToLine(check, tail, head) <= distance) ||
                    (DecodeUtils.calculateDistance(check, tail) <= distance) ||
                    (DecodeUtils.calculateDistance(check, head) <= distance)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOnAllRoute (List<LatLng> listLL, LatLng check, double distance) {
        for(int n = 0; n < listLL.size()-1; n++) {
            if(isOnRoute(listLL.get(n), listLL.get(n+1), check, distance)) {
                return true;
            }
        }
        return false;
    }
}
