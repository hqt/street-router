package com.fpt.router.utils;

import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.motorbike.AutocompleteObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 10/14/15.
 */
public class GoogleAPIUtils {
    public static String getTwoPointDirection(AutocompleteObject start, AutocompleteObject end){
        String key = AppConstants.GOOGLE_KEY;
        String startLocation = start.getName();
        String endLocation = end.getName();
        try {
            startLocation = URLEncoder.encode(startLocation, "UTF-8");
            endLocation = URLEncoder.encode(endLocation, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(!start.getPlace_id().equals("")) {
            startLocation = "place_id:" + start.getPlace_id();
        }
        if(!end.getPlace_id().equals("")) {
            endLocation = "place_id:" + end.getPlace_id();
        }
        String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + startLocation +
                "&destination=" + endLocation +
                "&alternatives=true" +
                "&mode=driving" +
                "&key=" + key;
        return url;
    }

    public static List<String> getFourPointWithoutOptimizeDirection(List<AutocompleteObject> listLocation) {
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

    public static List<String> getFourPointDirection(List<AutocompleteObject> listLocation, Boolean optimize){
        String key = AppConstants.GOOGLE_KEY;
        List<String> listUrl = new ArrayList<>();
        String startLocation = null;
        String endLocation = null;
        String waypoint_1 = "";
        String waypoint_2 = "";
        int count = 1;
        if(listLocation.size() == 4){
            count = 3;
        }
        List<AutocompleteObject> list = listLocation;

        for(int n = 0; n < count; n++) {
            String waypoints = "";
            try {
                if(count == 3) {
                    AutocompleteObject change = list.get(1);
                    list.set(1, list.get(2));
                    list.set(2, list.get(3));
                    list.set(3, change);
                }
                startLocation = URLEncoder.encode(list.get(0).getName(), "UTF-8");
                endLocation = URLEncoder.encode(list.get(1).getName(), "UTF-8");
                if(!list.get(0).getPlace_id().equals("")) {
                    startLocation = "place_id:" + list.get(0).getPlace_id();
                }
                if(!list.get(1).getPlace_id().equals("")) {
                    endLocation = "place_id:" + list.get(1).getPlace_id();
                }
                if (list.size() == 3) {
                    waypoint_1 = URLEncoder.encode(list.get(2).getName(), "UTF-8");
                    if(!list.get(2).getPlace_id().equals("")) {
                        waypoint_1 = "place_id:" + list.get(2).getPlace_id();
                    }
                    waypoints = "&waypoints=" + waypoint_1;

                } else if (list.size() == 4) {
                    waypoint_1 = URLEncoder.encode(list.get(2).getName(), "UTF-8");
                    waypoint_2 = URLEncoder.encode(list.get(3).getName(), "UTF-8");
                    if(!list.get(2).getPlace_id().equals("")) {
                        waypoint_1 = "place_id:" + list.get(2).getPlace_id();
                    }
                    if(!list.get(3).getPlace_id().equals("")) {
                        waypoint_2 = "place_id:" + list.get(3).getPlace_id();
                    }
                    if (optimize) {
                        waypoints = "&waypoints=optimize:place_id:true" + "|" + waypoint_1 + "|" + waypoint_2;
                    } else {
                        waypoints = "&waypoints=" + waypoint_1 + "|" + waypoint_2;
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "origin=" + startLocation +
                    "&destination=" + endLocation + waypoints +
                    "&alternatives=true" +
                    "&mode=driving" +
                    "&key=" + key;
            listUrl.add(url);
        }
        return listUrl;
    }

    public static List<String> getGooglePlace(String autoCompleteText){
        String key = AppConstants.GOOGLE_KEY;
        String text = null;
        List<String> listUrl = new ArrayList<>();
        try {
            text = URLEncoder.encode(autoCompleteText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?" +
                "input=" + text +
                "&components=country:vn&language=vi&sensor=true&key=" + key;
        listUrl.add(url);

        return listUrl;
    }

    public static String getLocationByPlaceID(String input){
        String key = AppConstants.GOOGLE_KEY;
        String url = "https://maps.googleapis.com/maps/api/place/details/json?" +
                "placeid=" + input +
                "&key=" + key;

        return url;
    }
}
