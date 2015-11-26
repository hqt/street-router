package com.fpt.router.utils;

import android.graphics.Point;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nguyen Trung Nam on 11/6/2015.
 */
public class MathUtils {
    private static final int NUMBER_OF_HALF_WINDS = 16;
    private static final double EARTH_RADIUS_KM = 6371.0D;

    public MathUtils() {
    }

    public static int mod(int a, int b) {
        return (a % b + b) % b;
    }

    public static float mod(float a, float b) {
        return (a % b + b) % b;
    }

    public static int getHalfWindIndex(float heading) {
        float partitionSize = 22.5F;
        float displacedHeading = mod(heading + partitionSize / 2.0F, 360.0F);
        return (int) (displacedHeading / partitionSize);
    }

    public static float getBearing(double latitude1, double longitude1, double latitude2, double longitude2) {
        latitude1 = Math.toRadians(latitude1);
        longitude1 = Math.toRadians(longitude1);
        latitude2 = Math.toRadians(latitude2);
        longitude2 = Math.toRadians(longitude2);
        double dLon = longitude2 - longitude1;
        double y = Math.sin(dLon) * Math.cos(latitude2);
        double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(dLon);
        double bearing = Math.atan2(y, x);
        return mod((float) Math.toDegrees(bearing), 360.0F);
    }

    public static float getDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double dLat = Math.toRadians(latitude2 - latitude1);
        double dLon = Math.toRadians(longitude2 - longitude1);
        double lat1 = Math.toRadians(latitude1);
        double lat2 = Math.toRadians(latitude2);
        double sqrtHaversineLat = Math.sin(dLat / 2.0D);
        double sqrtHaversineLon = Math.sin(dLon / 2.0D);
        double a = sqrtHaversineLat * sqrtHaversineLat + sqrtHaversineLon * sqrtHaversineLon * Math.cos(lat1) * Math.cos(lat2);
        double c = 2.0D * Math.atan2(Math.sqrt(a), Math.sqrt(1.0D - a));
        return (float) (6371.0D * c);
    }

    public static double pointToLineDistance(LatLng A, LatLng B, LatLng P) {
        double normalLength = Math.sqrt((B.latitude - A.latitude) * (B.latitude - A.latitude) +
                (B.longitude - A.longitude) * (B.longitude - A.longitude));
        return Math.abs((P.latitude - A.latitude) * (B.longitude - A.longitude) -
                (P.longitude - A.longitude) * (B.latitude - A.latitude)) / normalLength;
    }

    public static double checkSide(LatLng head, LatLng tail, LatLng point) {
        double[] tailxy = MathUtils.changeLatLngToXY(tail);
        double[] headxy = MathUtils.changeLatLngToXY(head);
        double[] checkxy = MathUtils.changeLatLngToXY(point);
        double check = (tailxy[1] - headxy[1]) * (checkxy[0] - headxy[0])
                - (checkxy[1] - headxy[1]) * (tailxy[0] - checkxy[0]);
        return check;
    }

    public static double[] changeLatLngToXY(LatLng latLng) {
        double mapWidth    = 200;
        double mapHeight   = 100;
        double result[] = new double[2];
        // get x value
        double x = (latLng.longitude+180)*(mapWidth/360);
        result[1] = x;
        // convert from degrees to radians
        double latRad = latLng.latitude*Math.PI/180;

        // get y value
        double mercN = Math.log(Math.tan((Math.PI/4)+(latRad/2)));
        double y     = (mapHeight/2)-(mapWidth*mercN/(2*Math.PI));
        result[0] = y;
        return result;
    }
}
