package com.fpt.router.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.ContextThemeWrapper;

import com.fpt.router.R;
import com.fpt.router.library.model.common.Location;
import com.fpt.router.library.model.motorbike.DetailLocation;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.library.utils.MapUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.nutiteq.core.MapPos;
import com.nutiteq.datasources.LocalVectorDataSource;
import com.nutiteq.projections.Projection;
import com.nutiteq.styles.BillboardOrientation;
import com.nutiteq.styles.LineJointType;
import com.nutiteq.styles.LineStyle;
import com.nutiteq.styles.LineStyleBuilder;
import com.nutiteq.styles.MarkerStyle;
import com.nutiteq.styles.MarkerStyleBuilder;
import com.nutiteq.ui.MapView;
import com.nutiteq.utils.BitmapUtils;
import com.nutiteq.vectorelements.Line;
import com.nutiteq.vectorelements.Marker;
import com.nutiteq.wrappedcommons.MapPosVector;

import java.util.List;

/**
 * Created by Nguyen Trung Nam on 11/3/2015.
 */
public class NutiteqMapUtil {

    public static Marker drawMarkerNutiteq(MapView mapView, LocalVectorDataSource vectorDataSource, Resources res, Double lat, Double lng, int icon) {
        Bitmap androidMarkerBitmap = BitmapFactory.decodeResource(res, icon);
        com.nutiteq.graphics.Bitmap markerBitmap = BitmapUtils.createBitmapFromAndroidBitmap(androidMarkerBitmap);
        MarkerStyleBuilder markerStyleBuilder = new MarkerStyleBuilder();
        markerStyleBuilder.setBitmap(markerBitmap);
        //markerStyleBuilder.setHideIfOverlapped(false);
        markerStyleBuilder.setSize(30);
        MarkerStyle sharedMarkerStyle = markerStyleBuilder.buildStyle();

        // 3. Add marker
        MapPos markerPos = mapView.getOptions().getBaseProjection().fromWgs84(new MapPos(lng, lat));
        int i = 1;
        Marker marker = new Marker(markerPos,sharedMarkerStyle);
        vectorDataSource.add(marker);
        return marker;
    }

    public static Marker drawCurrentMarkerNutiteq(MapView mapView, LocalVectorDataSource vectorDataSource, Resources res, Double lat, Double lng, int icon) {
        Bitmap androidMarkerBitmap = BitmapFactory.decodeResource(res, icon);
        com.nutiteq.graphics.Bitmap markerBitmap = BitmapUtils.createBitmapFromAndroidBitmap(androidMarkerBitmap);
        MarkerStyleBuilder markerStyleBuilder = new MarkerStyleBuilder();
        markerStyleBuilder.setBitmap(markerBitmap);
        markerStyleBuilder.setOrientationMode(BillboardOrientation.BILLBOARD_ORIENTATION_GROUND);
        markerStyleBuilder.setAnchorPoint(0, (float)0.);
        //markerStyleBuilder.setHideIfOverlapped(false);
        markerStyleBuilder.setSize(30);
        MarkerStyle sharedMarkerStyle = markerStyleBuilder.buildStyle();

        // 3. Add marker
        MapPos markerPos = mapView.getOptions().getBaseProjection().fromWgs84(new MapPos(lng, lat));
        int i = 1;
        Marker marker = new Marker(markerPos,sharedMarkerStyle);
        vectorDataSource.add(marker);
        return marker;
    }

    public static Line drawLineNutite(LocalVectorDataSource vectorDataSource, int color, List<LatLng> listLatLng, Projection baseProjection, int width) {
        // Create layer for Line geometries and add it to MapView
        LineStyleBuilder lineStyleBuilder = new LineStyleBuilder();
        lineStyleBuilder.setColor(new com.nutiteq.graphics.Color(color));
        lineStyleBuilder.setLineJointType(LineJointType.LINE_JOINT_TYPE_ROUND);
        lineStyleBuilder.setStretchFactor(2);
        lineStyleBuilder.setWidth(width);
        MapPosVector linePoses = new MapPosVector();
        for(int i = 0; i < listLatLng.size(); i++) {
            linePoses.add(baseProjection.fromWgs84(new MapPos(listLatLng.get(i).longitude, listLatLng.get(i).latitude)));
        }

        // Add first line
        Line line = new Line(linePoses, lineStyleBuilder.buildStyle());
        vectorDataSource.add(line);
        return line;
    }

    public static void drawMapWithTwoPoint(MapView mapView, LocalVectorDataSource vectorDataSource, Resources res, Projection baseProjection, List<Leg> input) {
        List<Leg> listLeg = input;
        //Start Point
        Leg leg = listLeg.get(0);
        Location start_location = leg.getDetailLocation().getStartLocation();
        Double latitude = start_location.getLatitude();
        Double longitude = start_location.getLongitude();
        drawMarkerNutiteq(mapView, vectorDataSource, res, latitude, longitude, R.drawable.blue_1);

        //EndPoint
        Location end_location = leg.getDetailLocation().getEndLocation();
        latitude = end_location.getLatitude();
        longitude = end_location.getLongitude();
        drawMarkerNutiteq(mapView, vectorDataSource, res, latitude, longitude, R.drawable.green_1);
        String encodedString;
        List<LatLng> list;
        encodedString = leg.getOverview_polyline();
        list = DecodeUtils.decodePoly(encodedString);
        drawLineNutite(vectorDataSource, 0xFFFF0000, list, baseProjection, 6);
        drawLineNutite(vectorDataSource, 0xFFFFFFFF, list, baseProjection, 4);

        // Move the camera to show the marker.
        LatLng latLng = DecodeUtils.middlePoint(start_location.getLatitude(),
                start_location.getLongitude(), end_location.getLatitude(), end_location.getLongitude());
        MapPos markerPos = mapView.getOptions().getBaseProjection().fromWgs84(new MapPos(latLng.longitude, latLng.latitude));
        mapView.setFocusPos(markerPos, 1);
        mapView.setZoom(12, 1);
    }

    public static void drawMapWithFourPoint(MapView mapView, LocalVectorDataSource vectorDataSource, Resources res, Projection baseProjection, List<Leg> input) {
        Leg leg;
        Double latitude;
        Double longitude;
        for (int i = 0; i < input.size(); i++) {
            leg = input.get(i);
            DetailLocation detalL = leg.getDetailLocation();
            Location start_location = detalL.getStartLocation();
            Location end_location = detalL.getEndLocation();
            // latitude and longitude

            if (i == 0) {
                latitude = end_location.getLatitude();
                longitude = end_location.getLongitude();
                drawMarkerNutiteq(mapView, vectorDataSource, res, latitude, longitude, R.drawable.yellow);

                //MapUtils.drawPointColor(mMap, latitude, longitude, leg.getEndAddress(), BitmapDescriptorFactory.HUE_YELLOW);

                latitude = start_location.getLatitude();
                longitude = start_location.getLongitude();
                drawMarkerNutiteq(mapView, vectorDataSource, res, latitude, longitude, R.drawable.blue_1);

                //MapUtils.drawPointColor(mMap, latitude, longitude, leg.getStartAddress(), BitmapDescriptorFactory.HUE_GREEN);
            }
            if (i == input.size() - 1) {
                latitude = end_location.getLatitude();
                longitude = end_location.getLongitude();
                drawMarkerNutiteq(mapView, vectorDataSource, res, latitude, longitude, R.drawable.green_1);
                //MapUtils.drawPointColor(mMap, latitude, longitude, leg.getEndAddress(), BitmapDescriptorFactory.HUE_RED);

                latitude = start_location.getLatitude();
                longitude = start_location.getLongitude();
                drawMarkerNutiteq(mapView, vectorDataSource, res, latitude, longitude, R.drawable.yellow);
                //MapUtils.drawPointColor(mMap, latitude, longitude, leg.getStartAddress(), BitmapDescriptorFactory.HUE_YELLOW);
            }

            //add polyline
            String encodedString = leg.getOverview_polyline();
            List<LatLng> listLatLng = DecodeUtils.decodePoly(encodedString);
            drawLineNutite(vectorDataSource, 0xFFFF0000, listLatLng, baseProjection, 6);
            drawLineNutite(vectorDataSource, 0xFFFFFFFF, listLatLng, baseProjection, 4);

            //MapUtils.drawLine(mMap, listLatLng, Color.BLUE);
            LatLng latLng = DecodeUtils.middlePoint(start_location.getLatitude(),
                    start_location.getLongitude(), end_location.getLatitude(), end_location.getLongitude());
            MapPos markerPos = mapView.getOptions().getBaseProjection().fromWgs84(new MapPos(latLng.longitude, latLng.latitude));
            mapView.setFocusPos(markerPos, 1);
            mapView.setZoom(12, 1);        }
    }

}