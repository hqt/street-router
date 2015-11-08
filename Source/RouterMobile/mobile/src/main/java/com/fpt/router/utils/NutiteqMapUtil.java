package com.fpt.router.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.ContextThemeWrapper;

import com.fpt.router.R;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.bus.Segment;
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

import java.util.ArrayList;
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

    public static void drawMapWithBusTwoPoint(MapView mapView, LocalVectorDataSource vectorDataSource, Resources res, Projection baseProjection, Result result) {
        List<INode> iNodeList = result.nodeList;
        double latitude = 0.0;
        double longitude = 0.0;
        List<LatLng> listFinal = new ArrayList<LatLng>();
        List<Path> paths = new ArrayList<>();
        List<Location> points = new ArrayList<>();
        /**
         * Middle location
         */
        List<Segment> segments = new ArrayList<Segment>();
        for (int i = 0; i < iNodeList.size(); i++) {
            if (iNodeList.get(i) instanceof Segment) {
                Segment segment = (Segment) iNodeList.get(i);
                segments.add(segment);
            }
        }
        for (int m = 0; m < segments.size(); m++) {
            paths = segments.get(m).paths;
            for (int j = 0; j < paths.size(); j++) {
                points = paths.get(j).points;
                for (int n = 0; n < points.size(); n++) {
                    LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                    listFinal.add(latLng);
                }
            }
        }
        /**
         * start location
         */
        if (iNodeList.get(0) instanceof Path) {
            Path path = (Path) iNodeList.get(0);
            Location startLocation = path.stationFromLocation;
            latitude = startLocation.getLatitude();
            longitude = startLocation.getLongitude();
            drawMarkerNutiteq(mapView, vectorDataSource, res, latitude, longitude, R.drawable.yellow);
            LatLng startLatLng = new LatLng(path.stationFromLocation.getLatitude(), path.stationFromLocation.getLongitude());
            //moveToLocation(startLatLng, true);
            LatLng endLatLng = new LatLng(listFinal.get(0).latitude, listFinal.get(0).longitude);
            List<LatLng> startList = new ArrayList<>();
            startList.add(startLatLng);
            startList.add(endLatLng);
            points = path.points;
            if (points == null) {
                drawLineNutite(vectorDataSource, Color.parseColor("#FF5722"), startList, baseProjection, 4);
            } else {
                List<LatLng> list = new ArrayList<>();
                for (int n = 0; n < points.size(); n++) {
                    LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                    list.add(latLng);
                }
                drawLineNutite(vectorDataSource, Color.parseColor("#FF5722"), list, baseProjection, 4);
            }
        }

        /**
         * end location
         */
        if (iNodeList.get(iNodeList.size() - 1) instanceof Path) {
            Path path = (Path) iNodeList.get(iNodeList.size() - 1);
            Location endLocation = path.stationToLocation;
            latitude = endLocation.getLatitude();
            longitude = endLocation.getLongitude();
            drawMarkerNutiteq(mapView, vectorDataSource, res, latitude, longitude, R.drawable.green_1);
            LatLng startLatLng = new LatLng(listFinal.get(listFinal.size() - 1).latitude, listFinal.get(listFinal.size() - 1).longitude);
            LatLng endLatLng = new LatLng(latitude, longitude);
            List endList = new ArrayList();
            endList.add(startLatLng);
            endList.add(endLatLng);
            points = path.points;
            if (points == null) {
                drawLineNutite(vectorDataSource, Color.parseColor("#FF5722"), endList, baseProjection, 4);
            } else {
                List<LatLng> list = new ArrayList<>();
                for (int n = 0; n < points.size(); n++) {
                    LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                    list.add(latLng);
                }
                drawLineNutite(vectorDataSource, Color.parseColor("#FF5722"), list, baseProjection, 4);
            }


        }
        //add polyline
        drawLineNutite(vectorDataSource, Color.parseColor("#2196F3"), listFinal, baseProjection, 4);

        MapPos markerPos = mapView.getOptions().getBaseProjection().fromWgs84(new MapPos(listFinal.get(0).longitude, listFinal.get(0).latitude));
        mapView.setFocusPos(markerPos, 1);
        mapView.setZoom(12, 1);

    }

    public static void drawMapWithBusFourPoint(MapView mapView, LocalVectorDataSource vectorDataSource, Resources res, Projection baseProjection, Journey journey) {
        List<Result> results = journey.results;
        List<Location> points;
        List<Path> paths;
        List<INode> iNodeList;
        double latitude = 0.0;
        double longitude = 0.0;
        Result result;
        for (int k = 0; k < results.size(); k++) {
            List<LatLng> listFinal = new ArrayList<LatLng>();
            result = results.get(k);
            iNodeList = result.nodeList;

            List<Segment> segments = new ArrayList<Segment>();
            for (int i = 0; i < iNodeList.size(); i++) {
                if (iNodeList.get(i) instanceof Segment) {
                    Segment segment = (Segment) iNodeList.get(i);
                    segments.add(segment);
                }
            }
            for (int m = 0; m < segments.size(); m++) {
                paths = segments.get(m).paths;
                for (int j = 0; j < paths.size(); j++) {
                    points = paths.get(j).points;
                    for (int n = 0; n < points.size(); n++) {
                        LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                        listFinal.add(latLng);
                    }
                }
            }

            /**
             * start location
             */
            if (iNodeList.get(0) instanceof Path) {
                Path path = (Path) iNodeList.get(0);
                Location startLocation = path.stationFromLocation;
                latitude = startLocation.getLatitude();
                longitude = startLocation.getLongitude();
                drawMarkerNutiteq(mapView, vectorDataSource, res, latitude, longitude, R.drawable.yellow);
                LatLng startLatLng = new LatLng(path.stationFromLocation.getLatitude(), path.stationFromLocation.getLongitude());
                //moveToLocation(startLatLng, true);
                LatLng endLatLng = new LatLng(listFinal.get(0).latitude, listFinal.get(0).longitude);
                List<LatLng> startList = new ArrayList<>();
                startList.add(startLatLng);
                startList.add(endLatLng);
                points = path.points;
                if (points == null) {
                    drawLineNutite(vectorDataSource, Color.parseColor("#FF5722"), startList, baseProjection, 4);
                } else {
                    List<LatLng> list = new ArrayList<>();
                    for (int n = 0; n < points.size(); n++) {
                        LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                        list.add(latLng);
                    }
                    drawLineNutite(vectorDataSource, Color.parseColor("#FF5722"), list, baseProjection, 4);
                }
            }


            /**
             * end location
             */
            if (iNodeList.get(iNodeList.size() - 1) instanceof Path) {
                Path path = (Path) iNodeList.get(iNodeList.size() - 1);
                Location endLocation = path.stationToLocation;
                latitude = endLocation.getLatitude();
                longitude = endLocation.getLongitude();
                drawMarkerNutiteq(mapView, vectorDataSource, res, latitude, longitude, R.drawable.green_1);
                LatLng startLatLng = new LatLng(listFinal.get(listFinal.size() - 1).latitude, listFinal.get(listFinal.size() - 1).longitude);
                LatLng endLatLng = new LatLng(path.stationToLocation.getLatitude(), path.stationToLocation.getLongitude());
                List<LatLng> endList = new ArrayList<>();
                endList.add(startLatLng);
                endList.add(endLatLng);
                points = path.points;
                if (points == null) {
                    drawLineNutite(vectorDataSource, Color.parseColor("#FF5722"), endList, baseProjection, 4);
                } else {
                    List<LatLng> list = new ArrayList<>();
                    for (int n = 0; n < points.size(); n++) {
                        LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                        list.add(latLng);
                    }
                    drawLineNutite(vectorDataSource, Color.parseColor("#FF5722"), list, baseProjection, 4);
                }
            }
            //add polyline
            drawLineNutite(vectorDataSource, Color.parseColor("#2196F3"), listFinal, baseProjection, 4);

            MapPos markerPos = mapView.getOptions().getBaseProjection().fromWgs84(new MapPos(listFinal.get(0).longitude, listFinal.get(0).latitude));
            mapView.setFocusPos(markerPos, 1);
            mapView.setZoom(12, 1);
        }
    }
}
