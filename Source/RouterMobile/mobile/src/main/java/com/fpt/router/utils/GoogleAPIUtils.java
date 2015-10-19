package com.fpt.router.utils;

import com.fpt.router.library.config.AppConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 10/14/15.
 */
public class GoogleAPIUtils {
    public static String getTwoPointDirection(String start, String end){
        String key = AppConstants.GOOGLE_KEY;
        String startLocation = start;
        String endLocation = end;
        try {
            startLocation = URLEncoder.encode(startLocation, "UTF-8");
            endLocation = URLEncoder.encode(endLocation, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=place_id:" + startLocation +
                "&destination=place_id:" + endLocation +
                "&alternatives=true" +
                "&mode=driving" +
                "&key=" + key;
        return url;
    }

    public static List<String> getFourPointOptimizeDirection(List<String> listLocation) {
        String url;
        List<String> listUrl = new ArrayList<>();
        for(int x = 0; x < listLocation.size()-1; x++){
            for(int y = 1; y < listLocation.size(); y++){
                if((x != y) && (y-x != 3)) {
                    url = getTwoPointDirection(listLocation.get(x), listLocation.get(y));
                    listUrl.add(url);
                }
            }
        }
        url = listUrl.get(3);
        listUrl.remove(3);
        listUrl.add(url);
        return listUrl;
    }

    public static List<String> getFourPointDirection(List<String> listPlaceID, Boolean optimize){
        String key = AppConstants.GOOGLE_KEY;
        List<String> listUrl = new ArrayList<>();
        String startLocation = null;
        String endLocation = null;
        String waypoint_1 = "";
        String waypoint_2 = "";
        int count = 1;
        if(listPlaceID.size() == 4){
            count = 3;
        }
        List<String> listPlace = listPlaceID;

        for(int n = 0; n < count; n++) {
            String waypoints = "";
            try {
                if(count == 3) {
                    String change = listPlace.get(1);
                    listPlace.set(1, listPlace.get(2));
                    listPlace.set(2, listPlace.get(3));
                    listPlace.set(3, change);
                }
                startLocation = URLEncoder.encode(listPlace.get(0), "UTF-8");
                endLocation = URLEncoder.encode(listPlace.get(1), "UTF-8");
                if (listPlace.size() == 3) {
                    waypoint_1 = URLEncoder.encode(listPlace.get(2), "UTF-8");
                    waypoints = "&waypoints=place_id:" + waypoint_1;

                }else if (listPlace.size() == 4) {
                    waypoint_1 = URLEncoder.encode(listPlace.get(2), "UTF-8");
                    waypoint_2 = URLEncoder.encode(listPlace.get(3), "UTF-8");
                    if (optimize) {
                        waypoints = "&waypoints=optimize:place_id:true" + "|place_id:" + waypoint_1 + "|place_id:" + waypoint_2;
                    } else {
                        waypoints = "&waypoints=place_id:" + waypoint_1 + "|place_id:" + waypoint_2;
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "origin=place_id:" + startLocation +
                    "&destination=place_id:" + endLocation + waypoints +
                    "&alternatives=true" +
                    "&mode=driving" +
                    "&key=" + key;
            listUrl.add(url);
        }
        return listUrl;
    }

    public static String getShortePath(String start, String end,String way_point_1,String way_point_2, Boolean optimize){
        String key = AppConstants.GOOGLE_KEY;
        String startLocation = null;
        String endLocation = null;
        String waypoint_1 = "";
        String waypoint_2 = "";
        try {
            startLocation = URLEncoder.encode(start, "UTF-8");
            endLocation = URLEncoder.encode(end, "UTF-8");
            waypoint_1 = URLEncoder.encode(way_point_1,"UTF-8");
            waypoint_2 = URLEncoder.encode(way_point_2,"UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String waypoints = "&waypoints=";
        if(waypoint_1 != null || waypoint_2 != null){
            if(optimize) {
                waypoints = waypoints + "optimize:true" + "|" + waypoint_1 + "|" + waypoint_2;
            } else  {
                waypoints = waypoints + waypoint_1 + "|" + waypoint_2;
            }
        }
        String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + startLocation +
                "&destination=" + endLocation + waypoints +
                "&alternatives=true" +
                "&mode=driving" +
                "&key=" + key;

        String json = NetworkUtils.download(url);
        return json;
    }

    public static List<String> getGooglePlace(String autoCompleteText){
        String key = AppConstants.GOOGLE_KEY;
        String text = null;
        List<String> listUrl = new ArrayList<>();
        String[] listTypes = {"geocode", "address", "establishment"};
        try {
            text = URLEncoder.encode(autoCompleteText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < listTypes.length; i++) {
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?" +
                    "input=" + text +
                    "&types=" + listTypes[i] +
                    "&components=country:vn&language=vi&sensor=true&key=" + key;
            listUrl.add(url);
        }
        return listUrl;
    }

}
