package com.fpt.router.library.utils;

import android.graphics.Color;

import com.fpt.router.library.model.motorbike.Leg;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Huynh Quang Thao on 11/2/15.
 */
public class BusMapUtils {

    public static void drawBusPoint(GoogleMap map, double latitude, double longitude, String title, int bus){
        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude))
                .title(title);

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory.fromResource(bus));
        // adding marker
        map.addMarker(marker);
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

}
