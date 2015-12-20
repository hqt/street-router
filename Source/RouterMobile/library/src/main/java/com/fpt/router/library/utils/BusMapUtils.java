package com.fpt.router.library.utils;

import android.graphics.Color;

import com.fpt.router.library.model.motorbike.Leg;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.bus.Segment;
import com.fpt.router.library.model.common.Location;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 11/2/15.
 */
public class BusMapUtils {
    public static void drawBusPoint(GoogleMap map, double latitude, double longitude, String title, int bus) {
        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude))
                .title(title);
        // Changing marker icon
        marker.icon(BitmapDescriptorFactory.fromResource(bus));
        // adding marker
        map.addMarker(marker);
    }

    public static void drawMapWithTwoPointCircle(GoogleMap mMap, List<Leg> input) {
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

    public static void drawMapWithTwoPoint(GoogleMap mMap, Result result) {
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
            MapUtils.drawStartPoint(mMap, latitude, longitude, path.stationFromName);
            LatLng startLatLng = new LatLng(path.stationFromLocation.getLatitude(), path.stationFromLocation.getLongitude());
            //moveToLocation(startLatLng, true);
            LatLng endLatLng = new LatLng(listFinal.get(0).latitude, listFinal.get(0).longitude);
            List<LatLng> startList = new ArrayList<>();
            startList.add(startLatLng);
            startList.add(endLatLng);
            points = path.points;
            if (points == null) {
                MapUtils.drawDashedPolyLine(mMap, startList, Color.parseColor("#FF5722"));
            } else {
                List<LatLng> list = new ArrayList<>();
                for (int n = 0; n < points.size(); n++) {
                    LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                    list.add(latLng);
                }
                MapUtils.drawDashedPolyLine(mMap, list, Color.parseColor("#FF5722"));
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
            MapUtils.drawEndPoint(mMap, latitude, longitude, path.stationToName);
            LatLng startLatLng = new LatLng(listFinal.get(listFinal.size() - 1).latitude, listFinal.get(listFinal.size() - 1).longitude);
            LatLng endLatLng = new LatLng(latitude, longitude);
            List endList = new ArrayList();
            endList.add(startLatLng);
            endList.add(endLatLng);
            points = path.points;
            if (points == null) {
                MapUtils.drawDashedPolyLine(mMap, endList, Color.parseColor("#FF5722"));
            } else {
                List<LatLng> list = new ArrayList<>();
                for (int n = 0; n < points.size(); n++) {
                    LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                    list.add(latLng);
                }
                MapUtils.drawDashedPolyLine(mMap, list, Color.parseColor("#FF5722"));
            }


        }
        //add polyline
        MapUtils.drawLine(mMap, listFinal, Color.BLUE);

                /*MapUtils.drawCircle(mMap,list);*/
        MapUtils.moveCamera(mMap, latitude, longitude, 10);
    }

    public static void drawMapWithFourPoint(GoogleMap mMap, Journey journey) {
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
                MapUtils.drawStartPoint(mMap, latitude, longitude, path.stationFromName);
                LatLng startLatLng = new LatLng(path.stationFromLocation.getLatitude(), path.stationFromLocation.getLongitude());
                //moveToLocation(startLatLng, true);
                LatLng endLatLng = new LatLng(listFinal.get(0).latitude, listFinal.get(0).longitude);
                List<LatLng> startList = new ArrayList<>();
                startList.add(startLatLng);
                startList.add(endLatLng);
                points = path.points;
                if (points == null) {
                    MapUtils.drawDashedPolyLine(mMap, startList, Color.parseColor("#FF5722"));
                } else {
                    List<LatLng> list = new ArrayList<>();
                    for (int n = 0; n < points.size(); n++) {
                        LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                        list.add(latLng);
                    }
                    MapUtils.drawDashedPolyLine(mMap, list, Color.parseColor("#FF5722"));
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
                MapUtils.drawEndPoint(mMap, latitude, longitude, path.stationToName);
                LatLng startLatLng = new LatLng(listFinal.get(listFinal.size() - 1).latitude, listFinal.get(listFinal.size() - 1).longitude);
                LatLng endLatLng = new LatLng(path.stationToLocation.getLatitude(), path.stationToLocation.getLongitude());
                List<LatLng> endList = new ArrayList<>();
                endList.add(startLatLng);
                endList.add(endLatLng);
                points = path.points;
                if (points == null) {
                    MapUtils.drawDashedPolyLine(mMap, endList, Color.parseColor("#FF5722"));
                } else {
                    List<LatLng> list = new ArrayList<>();
                    for (int n = 0; n < points.size(); n++) {
                        LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                        list.add(latLng);
                    }
                    MapUtils.drawDashedPolyLine(mMap, list, Color.parseColor("#FF5722"));
                }
            }
            //add polyline
            MapUtils.drawLine(mMap, listFinal, Color.BLUE);
            MapUtils.moveCamera(mMap, latitude, longitude, 10);
        }
    }

}
