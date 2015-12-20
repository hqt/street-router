package com.fpt.router.library.utils;

import android.graphics.Color;

import com.fpt.router.library.model.common.Location;
import com.fpt.router.library.model.motorbike.DetailLocation;
import com.fpt.router.library.model.motorbike.Leg;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Huynh Quang Thao on 11/2/15.
 */
public class MotorMapUtils {
    public static void drawMapWithTwoPoint(GoogleMap mMap, List<Leg> input) {
        List<Leg> listLeg = input;
        //Start Point
        Leg leg = listLeg.get(0);
        Location start_location = leg.getDetailLocation().getStartLocation();
        Double latitude = start_location.getLatitude();
        Double longitude = start_location.getLongitude();
        MapUtils.drawPointColor(mMap, latitude, longitude, leg.getStartAddress(), BitmapDescriptorFactory.HUE_GREEN);


        //EndPoint
        Location end_location = leg.getDetailLocation().getEndLocation();
        latitude = end_location.getLatitude();
        longitude = end_location.getLongitude();
        MapUtils.drawPointColor(mMap, latitude, longitude, leg.getEndAddress(), BitmapDescriptorFactory.HUE_RED);
        String encodedString;
        List<LatLng> list;
        encodedString = leg.getOverview_polyline();
        list = DecodeUtils.decodePoly(encodedString);
        MapUtils.drawLine(mMap, list, Color.BLUE);
        // Move the camera to show the marker.
        LatLng latLng = DecodeUtils.middlePoint(start_location.getLatitude(), start_location.getLongitude(), end_location.getLatitude(), end_location.getLongitude());
        MapUtils.moveCamera(mMap, latLng.latitude, latLng.longitude, 10);
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
                MapUtils.drawPointColor(mMap, latitude, longitude, leg.getStartAddress(), BitmapDescriptorFactory.HUE_GREEN);
            }
            if (i == listFinalLeg.size() - 1) {
                latitude = end_location.getLatitude();
                longitude = end_location.getLongitude();
                MapUtils.drawPointColor(mMap, latitude, longitude, leg.getEndAddress(), BitmapDescriptorFactory.HUE_RED);

                latitude = start_location.getLatitude();
                longitude = start_location.getLongitude();
                MapUtils.drawPointColor(mMap, latitude, longitude, leg.getStartAddress(), BitmapDescriptorFactory.HUE_YELLOW);
            }

            //add polyline
            String encodedString = leg.getOverview_polyline();
            List<LatLng> listLatLng = DecodeUtils.decodePoly(encodedString);
            MapUtils.drawLine(mMap, listLatLng, Color.BLUE);
            LatLng latLng = DecodeUtils.middlePoint(start_location.getLatitude(), start_location.getLongitude(), end_location.getLatitude(), end_location.getLongitude());
            MapUtils.moveCamera(mMap, latLng.latitude, latLng.longitude, 10);
        }
    }
}
