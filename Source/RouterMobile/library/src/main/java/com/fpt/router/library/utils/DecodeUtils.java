package com.fpt.router.library.utils;

import android.hardware.GeomagneticField;
import android.util.Pair;
import static java.lang.Math.*;

import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Segment;
import com.fpt.router.library.model.common.Location;
import com.fpt.router.library.model.entity.PathType;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.model.motorbike.Step;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10/3/2015.
 */
public class DecodeUtils {
    private GeomagneticField mGeomagneticField;

    public static List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public static List<LatLng> getListLocationToFakeGPS (List<Leg> listLeg, boolean optimize){
        List<LatLng> listLatLng;
        List<LatLng> listLatLngFromListLeg = new ArrayList<>();
        if(optimize) {
            List<LatLng> listLatLngFromOneLeg = decodePoly(listLeg.get(0).getOverview_polyline());
            listLatLngFromListLeg.addAll(listLatLngFromOneLeg);
        } else {
            for (int i = 0; i < listLeg.size(); i++) {
                List<LatLng> listLatLngFromOneLeg = decodePoly(listLeg.get(i).getOverview_polyline());
                if (i != 0) {
                    listLatLngFromOneLeg.remove(0);
                }
                listLatLngFromListLeg.addAll(listLatLngFromOneLeg);
            }
        }
        listLatLng = getPointsFromListLocation(listLatLngFromListLeg);
        return listLatLng;
    }

    public static List<LatLng> getPointsFromListLocation (List<LatLng> listLatLng) {
        for(int i = 0; i < listLatLng.size() - 1; i ++) {
            double checkDistance = calculateDistance(listLatLng.get(i), listLatLng.get(i+1));
            if(checkDistance > 20) {
                listLatLng.add(i+1, middlePoint(listLatLng.get(i).latitude, listLatLng.get(i).longitude, listLatLng.get(i+1).latitude, listLatLng.get(i+1).longitude));
                i--;
            }
        }
        return listLatLng;
    }

    static double havDistance(double lat1, double lat2, double dLng) {
        return hav(lat1 - lat2) + hav(dLng) * cos(lat1) * cos(lat2);
    }

    static double hav(double x) {
        double sinHalf = sin(x * 0.5);
        return sinHalf * sinHalf;
    }

    static double arcHav(double x) {
        return 2 * asin(sqrt(x));
    }

    public static double calculateDistance(LatLng from, LatLng to) {
        return computeAngleBetween(from, to) * 6371009;
    }

    static double computeAngleBetween(LatLng from, LatLng to) {
        return distanceRadians(toRadians(from.latitude), toRadians(from.longitude),
                toRadians(to.latitude), toRadians(to.longitude));
    }

    private static double distanceRadians(double lat1, double lng1, double lat2, double lng2) {
        return arcHav(havDistance(lat1, lat2, lng1 - lng2));
    }


    public static double calculateDistanceOld(LatLng locationFrom, LatLng locationTo) {
        double fromLat = locationFrom.latitude;
        double fromLong = locationFrom.longitude;
        double toLat = locationTo.latitude;
        double toLong = locationTo.longitude;
        double d2r = Math.PI / 180;
        double dLong = (toLong - fromLong) * d2r;
        double dLat = (toLat - fromLat) * d2r;
        double a = Math.pow(Math.sin(dLat / 2.0), 2) + Math.cos(fromLat * d2r)
                * Math.cos(toLat * d2r) * Math.pow(Math.sin(dLong / 2.0), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 1.0 * 6367000 * c;
        return Math.round(d);
    }

    public static LatLng middlePoint(Double lat1, Double lon1, Double lat2, Double lon2) {
        double dLon = Math.toRadians(lon2 - lon1);

        //convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);

        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);
        lat3 = Math.toDegrees(lat3);
        lon3 = Math.toDegrees(lon3);
        LatLng latLng = new LatLng(lat3 , lon3);

        return latLng;
    }

    public static String[] getDetailInstruction(String instructions) {
        String[] detailInstruction;
        String delimiter = "<div style=\"font-size:0.9em\">";
        String str = instructions;
        detailInstruction = str.split(delimiter);
        return detailInstruction;
    }

    /**
     * get List LatLong from List iNode
     * @param iNodeList
     * @return
     */
    public static List<LatLng> getListLocationFromNodeList(List<INode> iNodeList){
        List<LatLng> latLngs = new ArrayList<LatLng>();
        List<LatLng> latLngList = new ArrayList<LatLng>();

        if(iNodeList.get(0) instanceof Path){
            Path path = (Path) iNodeList.get(0);
            List<Location> points = path.points;
            for (int n = 0; n < points.size(); n++) {
                LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                latLngList.add(latLng);
            }
        }

        for (int i=0; i<iNodeList.size();i++){
            if(iNodeList.get(i) instanceof Segment){
                Segment segment = (Segment) iNodeList.get(i);
                List<Path> paths = segment.paths;
                for (int j = 0; j < paths.size(); j++) {
                    List<Location> points = paths.get(j).points;
                    for (int n = 0; n < points.size(); n++) {
                        LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                        latLngList.add(latLng);
                    }
                }
            }
        }

        if(iNodeList.get(iNodeList.size() - 1) instanceof Path){
            Path path = (Path) iNodeList.get(iNodeList.size() - 1);
            List<Location> points = path.points;
            for (int n = 0; n < points.size(); n++) {
                LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                latLngList.add(latLng);
            }
        }

        latLngs = getPointsFromListLocation(latLngList);
        return latLngs;
    }
    /**
     * List
     */
    public static Path covertLegToPath(Leg leg){
        Path path = new Path();
        List<LatLng> list;
        List<Location> locations = new ArrayList<>();
        String encodedString;
        path.stationFromName = leg.getStartAddress();
        path.stationToName = leg.getEndAddress();
        path.stationFromLocation = leg.getDetailLocation().getStartLocation();
        path.stationToLocation = leg.getDetailLocation().getEndLocation();
        path.type = PathType.WALKING;
        path.distance = leg.getDetailLocation().getDistance();
        path.time = TimeUtils.covertMinuteToPeriod(leg.getDetailLocation().getDuration());
        encodedString = leg.getOverview_polyline();
        list = DecodeUtils.decodePoly(encodedString);
        for (int i = 0; i<list.size();i++){
            LatLng latLng = list.get(i);
            Location location = new Location(latLng.latitude,latLng.longitude);
            locations.add(location);
        }
        path.points = locations;
        return path;
    }
}
