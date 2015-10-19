package com.fpt.router.library.utils;

import android.graphics.Color;

import com.fpt.router.library.model.motorbike.DetailLocation;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.model.motorbike.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

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

    public static void drawMapWithFourPoint(GoogleMap mMap, List<Leg> listFinalLeg) {
        Leg leg;
        Double latitude;
        Double longitude;
        for (int i = 0; i < listFinalLeg.size(); i++) {
            leg = listFinalLeg.get(i);
            DetailLocation detalL = leg.getDetailLocation();
            com.fpt.router.library.model.motorbike.Location start_location = detalL.getStartLocation();
            com.fpt.router.library.model.motorbike.Location end_location = detalL.getEndLocation();
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
