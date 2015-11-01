package com.fpt.router.library.utils;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.fpt.router.library.R;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.common.Location;
import com.fpt.router.library.model.motorbike.DetailLocation;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.utils.string.DistanceUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by Nguyen Trung Nam on 10/3/2015.
 */
public class MapUtils {
    public static void drawPoint(GoogleMap map, double latitude, double longitude, String title) {
        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude))
                .title(title);

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

        // adding marker
        map.addMarker(marker);
    }

    public static Marker drawPointIcon(GoogleMap map, double latitude, double longitude, String title, int resourceImageID) {
        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude))
                .title(title);

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory.fromResource(resourceImageID));

        // adding marker
        return map.addMarker(marker);
    }

    public static Marker drawPointColor(GoogleMap map, double latitude, double longitude, String title, float colorMarker) {
        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude))
                .title(title);

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory.defaultMarker(colorMarker));

        // adding marker
        return map.addMarker(marker);
    }

    public static void drawStartPoint(GoogleMap map, double latitude, double longitude, String title){
        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude))
                .title(title);

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

        // adding marker
        map.addMarker(marker);
    }
    public static void drawBusPoint(GoogleMap map, double latitude, double longitude, String title, int bus){
        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude))
                .title(title);

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory.fromResource(bus));
        // adding marker
        map.addMarker(marker);
    }


    public static void drawEndPoint(GoogleMap map, double latitude, double longitude, String title){
        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude))
                .title(title);

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        // adding marker
        map.addMarker(marker);
    }

    public static Polyline drawLine(GoogleMap map, List<LatLng> middlePoints, int color) {
        //add polyline
        PolylineOptions options = new PolylineOptions().width(10).color(color).geodesic(true);
        for (int z = 0; z < middlePoints.size(); z++) {
            LatLng point = middlePoints.get(z);
            options.add(point);
        }

        return  map.addPolyline(options);
    }


    /**
     * draw path walk
     * @param mMap
     * @param listOfPoints
     * @param color
     */
    public static void drawDashedPolyLine(GoogleMap mMap, List<LatLng> listOfPoints, int color) {
    /* Boolean to control drawing alternate lines */
        boolean added = false;
        for (int i = 0; i < listOfPoints.size() - 1 ; i++) {
        /* Get distance between current and next point */
            double distance = getConvertedDistance(listOfPoints.get(i),listOfPoints.get(i + 1));

        /* If distance is less than 0.002 kms */
            if (distance < 0.01) {
                if (!added) {
                    mMap.addPolyline(new PolylineOptions()
                            .add(listOfPoints.get(i))
                            .add(listOfPoints.get(i + 1))
                            .color(color).width(10));
                    /*mMap.addCircle(new CircleOptions()
                            .center(listOfPoints.get(i))
                            .center(listOfPoints.get(i+1))
                            .strokeColor(Color.parseColor("#01579B"))
                            .strokeWidth(10)
                            .fillColor(Color.parseColor("#039BE5"))
                            .radius(15));*/
                    added = true;
                } else {/* Skip this piece */
                    added = false;
                }
            } else {
            /* Get how many divisions to make of this line */
                int countOfDivisions = (int) ((distance/0.01));

            /* Get difference to add per lat/lng */
                double latdiff = (listOfPoints.get(i+1).latitude - listOfPoints
                        .get(i).latitude) / countOfDivisions;
                double lngdiff = (listOfPoints.get(i + 1).longitude - listOfPoints
                        .get(i).longitude) / countOfDivisions;

            /* Last known indicates start point of polyline. Initialized to ith point */
                LatLng lastKnowLatLng = new LatLng(listOfPoints.get(i).latitude, listOfPoints.get(i).longitude);
                for (int j = 0; j < countOfDivisions; j++) {

                /* Next point is point + diff */
                    LatLng nextLatLng = new LatLng(lastKnowLatLng.latitude + latdiff, lastKnowLatLng.longitude + lngdiff);
                    if (!added) {
                        mMap.addPolyline(new PolylineOptions()
                                .add(lastKnowLatLng)
                                .add(nextLatLng)
                                .color(color).width(10));
                        /*mMap.addCircle(new CircleOptions()
                                .center(lastKnowLatLng)
                                .center(nextLatLng)
                                .strokeColor(Color.parseColor("#01579B"))
                                .strokeWidth(10)
                                .fillColor(Color.parseColor("#039BE5"))
                                .radius(15));*/
                        added = true;
                    } else {
                        added = false;
                    }
                    lastKnowLatLng = nextLatLng;
                }
            }
        }
    }

    public static double getConvertedDistance(LatLng latlng1, LatLng latlng2) {
        double distance = DistanceUtil.distance(latlng1.latitude,
                latlng1.longitude,
                latlng2.latitude,
                latlng2.longitude);
        BigDecimal bd = new BigDecimal(distance);
        BigDecimal res = bd.setScale(3, RoundingMode.DOWN);
        return res.doubleValue();
    }


    public static void moveCamera(GoogleMap map, double latitude, double longitude, float zoom) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(zoom).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public static void drawMapWithTwoPoint(GoogleMap mMap, List<Leg> input) {
        List<Leg> listLeg = input;
        //Start Point
        Leg leg = listLeg.get(0);
        Location start_location = leg.getDetailLocation().getStartLocation();
        Double latitude = start_location.getLatitude();
        Double longitude = start_location.getLongitude();
        drawPointColor(mMap, latitude, longitude, leg.getStartAddress(), BitmapDescriptorFactory.HUE_GREEN);


        //EndPoint
        Location end_location = leg.getDetailLocation().getEndLocation();
        latitude = end_location.getLatitude();
        longitude = end_location.getLongitude();
        drawPointColor(mMap, latitude, longitude, leg.getEndAddress(), BitmapDescriptorFactory.HUE_RED);
        String encodedString;
        List<LatLng> list;
        encodedString = leg.getOverview_polyline();
        list = DecodeUtils.decodePoly(encodedString);
        drawLine(mMap, list, Color.BLUE);
        // Move the camera to show the marker.
        LatLng latLng = DecodeUtils.middlePoint(start_location.getLatitude(), start_location.getLongitude(), end_location.getLatitude(), end_location.getLongitude());
        moveCamera(mMap, latLng.latitude, latLng.longitude, 13);
    }

    public static void drawMapWithTwoPointCircle(GoogleMap mMap, List<Leg> input){
        List<Leg> listLeg = input;
        //Start Point
        Leg leg = listLeg.get(0);
       /* Location start_location = leg.getDetailLocation().getStartLocation();
        Double latitude = start_location.getLatitude();
        Double longitude = start_location.getLongitude();
        drawPointColor(mMap, latitude, longitude, leg.getStartAddress(), BitmapDescriptorFactory.HUE_RED);*/

        //EndPoint
        /*Location end_location = leg.getDetailLocation().getEndLocation();
        latitude = end_location.getLatitude();
        longitude = end_location.getLongitude();
        drawPointColor(mMap, latitude, longitude, leg.getEndAddress(), BitmapDescriptorFactory.HUE_RED);*/
        String encodedString;
        List<LatLng> list;
        encodedString = leg.getOverview_polyline();
        list = DecodeUtils.decodePoly(encodedString);
       /* drawLine(mMap, list, Color.RED);*/
        MapUtils.drawDashedPolyLine(mMap, list, Color.parseColor("#FF5722"));

        // Move the camera to show the marker.
       /* LatLng latLng = DecodeUtils.middlePoint(start_location.getLatitude(), start_location.getLongitude(), end_location.getLatitude(), end_location.getLongitude());
        moveCamera(mMap, latLng.latitude, latLng.longitude, 13);*/
    }

    public static void drawMapWithFourPoint(GoogleMap mMap, List<Leg> listFinalLeg) {
        Leg leg;
        Double latitude;
        Double longitude;
        for (int i = 0; i < listFinalLeg.size(); i++) {
            leg = listFinalLeg.get(i);
            DetailLocation detalL = leg.getDetailLocation();
            Location start_location = detalL.getStartLocation();
            Location end_location = detalL.getEndLocation();
            // latitude and longitude

            if (i == 0) {
                latitude = end_location.getLatitude();
                longitude = end_location.getLongitude();
                MapUtils.drawPointColor(mMap, latitude, longitude, leg.getEndAddress(), BitmapDescriptorFactory.HUE_YELLOW);

                latitude = start_location.getLatitude();
                longitude = start_location.getLongitude();
                drawPointColor(mMap, latitude, longitude, leg.getStartAddress(), BitmapDescriptorFactory.HUE_GREEN);
            }
            if (i == listFinalLeg.size() - 1) {
                latitude = end_location.getLatitude();
                longitude = end_location.getLongitude();
                drawPointColor(mMap, latitude, longitude, leg.getEndAddress(), BitmapDescriptorFactory.HUE_RED);

                latitude = start_location.getLatitude();
                longitude = start_location.getLongitude();
                drawPointColor(mMap, latitude, longitude, leg.getStartAddress(), BitmapDescriptorFactory.HUE_YELLOW);
            }

            //add polyline
            String encodedString = leg.getOverview_polyline();
            List<LatLng> listLatLng = DecodeUtils.decodePoly(encodedString);
            drawLine(mMap, listLatLng, Color.BLUE);
            LatLng latLng = DecodeUtils.middlePoint(start_location.getLatitude(), start_location.getLongitude(), end_location.getLatitude(), end_location.getLongitude());
            moveCamera(mMap, latLng.latitude, latLng.longitude, 12);
        }
    }

    public static void navigateMap(Location location) {

    }
}
