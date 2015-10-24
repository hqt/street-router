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

    public static List<String> getFourPointWithoutOptimizeDirection(List<AutocompleteObject> listLocation) {
        String url;
        List<String> listUrl = new ArrayList<>();
        for(int x = 0; x < listLocation.size()-1; x++){
            for(int y = 1; y < listLocation.size(); y++){
                if((x != y) && (y-x != 3)) {
                    url = getTwoPointDirection(listLocation.get(x).getPlace_id(), listLocation.get(y).getPlace_id());
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
                startLocation = URLEncoder.encode(list.get(0).getPlace_id(), "UTF-8");
                endLocation = URLEncoder.encode(list.get(1).getPlace_id(), "UTF-8");
                if (list.size() == 3) {
                    waypoint_1 = URLEncoder.encode(list.get(2).getPlace_id(), "UTF-8");
                    waypoints = "&waypoints=place_id:" + waypoint_1;

                } else if (list.size() == 4) {
                    waypoint_1 = URLEncoder.encode(list.get(2).getPlace_id(), "UTF-8");
                    waypoint_2 = URLEncoder.encode(list.get(3).getPlace_id(), "UTF-8");
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

}
