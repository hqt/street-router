package com.fpt.router.library.utils;

import android.util.Pair;

import com.fpt.router.library.model.motorbike.Leg;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10/3/2015.
 */
public class DecodeUtils {
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

    public static List<LatLng> getListLocationToFakeGPS (List<Leg> listLeg){
        List<LatLng> listLatLng;
        List<LatLng> listLatLngFromListLeg = new ArrayList<>();
        for(int i = 0; i < listLeg.size(); i++) {
            List<LatLng> listLatLngFromOneLeg = decodePoly(listLeg.get(i).getOverview_polyline());
            if(i != 0) {
                listLatLngFromOneLeg.remove(0);
            }
            listLatLngFromListLeg.addAll(listLatLngFromOneLeg);
        }
        listLatLng = getPointsFromListLocation(listLatLngFromListLeg);
        return listLatLng;
    }

    public static List<LatLng> getPointsFromListLocation (List<LatLng> listLatLng) {
        for(int i = 0; i < listLatLng.size() - 1; i ++) {
            int checkDistance = (int) calculateDistance(listLatLng.get(i), listLatLng.get(i+1));
            if(checkDistance > 50) {
                listLatLng.add(i+1, middlePoint(listLatLng.get(i).latitude, listLatLng.get(i).longitude, listLatLng.get(i+1).latitude, listLatLng.get(i+1).longitude));
                i--;
            }
        }
        return listLatLng;
    }

    public static double calculateDistance(LatLng locationFrom, LatLng locationTo) {
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
        double d = 6367000 * c;
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

    public static Pair<String, String> getDetailInstruction(String instructions) {
        Pair<String, String> detailInstruction;
        String delimiter = "<div style=\"font-size:0.9em\">";
        String[] temp;
        String str = instructions;
        temp = str.split(delimiter);
        String subTitle = "";
        for (int i= 1; i<temp.length;i++){
            subTitle += temp[i]+"\n";
        }
        detailInstruction = new Pair<>(temp[0], subTitle);
        return detailInstruction;
    }
}
