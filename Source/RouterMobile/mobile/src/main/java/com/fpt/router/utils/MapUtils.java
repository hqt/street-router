package com.fpt.router.utils;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
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
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

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

}
