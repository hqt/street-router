package com.fpt.router.utils;

import android.util.Log;
import android.support.v4.util.Pair;

import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.config.AppConstants.GoogleApiCode;
import com.fpt.router.library.model.common.AutocompleteObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 10/14/15.
 */
public class GoogleAPIUtils {
    public static String getTwoPointDirection(AutocompleteObject start, AutocompleteObject end) {
        String key = AppConstants.GOOGLE_KEY;
        String startLocation = start.getName();
        String endLocation = end.getName();
        try {
            startLocation = URLEncoder.encode(startLocation, "UTF-8");
            endLocation = URLEncoder.encode(endLocation, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!start.getPlace_id().equals("")) {
            startLocation = "place_id:" + start.getPlace_id();
        }
        if (!end.getPlace_id().equals("")) {
            endLocation = "place_id:" + end.getPlace_id();
        }
        String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + startLocation +
                "&destination=" + endLocation +
                "&alternatives=true" +
                "&language=vi" +
                "&mode=driving" +
                "&key=" + key;
        return url;
    }

    public static String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {
        String key = AppConstants.GOOGLE_KEY;
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&mode=walking&alternatives=true");
        urlString.append("&key=" + key);
        Log.i("URL Make URL", urlString.toString());
        return urlString.toString();
    }

    public static List<String> getFourPointWithoutOptimizeDirection(List<AutocompleteObject> listLocation) {
        String url;
        AutocompleteObject tmp = listLocation.get(1);
        listLocation.remove(1);
        listLocation.add(tmp);
        List<String> listUrl = new ArrayList<>();
        for (int x = 0; x < listLocation.size() - 1; x++) {
            for (int y = 1; y < listLocation.size(); y++) {
                if ((x != y) && (y - x != 3)) {
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

    public static List<String> getThreePointWithoutOptimizeDirection(List<AutocompleteObject> listLocation) {
        String url;
        List<String> listUrl = new ArrayList<>();
        for (int x = 0; x < listLocation.size() - 1; x++) {
            url = getTwoPointDirection(listLocation.get(x), listLocation.get(x + 1));
            listUrl.add(url);
        }
        return listUrl;
    }

    public static List<String> getFourPointDirection(List<AutocompleteObject> listLocation, Boolean optimize) {
        String key = AppConstants.GOOGLE_KEY;
        List<String> listUrl = new ArrayList<>();
        String startLocation = null;
        String endLocation = null;
        String waypoint_1 = "";
        String waypoint_2 = "";
        int count = 0;
        if (listLocation.size() == 4) {
            count = 3;
        } else if (listLocation.size() == 3) {
            count = 2;
        }
        List<AutocompleteObject> list = listLocation;

        for (int n = 0; n < count; n++) {
            String waypoints = "";
            try {
                if (count == 3) {
                    AutocompleteObject change = list.get(1);
                    list.set(1, list.get(2));
                    list.set(2, list.get(3));
                    list.set(3, change);
                } else if (count == 2) {
                    AutocompleteObject change = list.get(1);
                    list.set(1, list.get(2));
                    list.set(2, change);
                }
                startLocation = URLEncoder.encode(list.get(0).getName(), "UTF-8");
                endLocation = URLEncoder.encode(list.get(1).getName(), "UTF-8");
                if (!list.get(0).getPlace_id().equals("")) {
                    startLocation = "place_id:" + list.get(0).getPlace_id();
                }
                if (!list.get(1).getPlace_id().equals("")) {
                    endLocation = "place_id:" + list.get(1).getPlace_id();
                }
                if (list.size() == 3) {
                    waypoint_1 = URLEncoder.encode(list.get(2).getName(), "UTF-8");
                    if (!list.get(2).getPlace_id().equals("")) {
                        waypoint_1 = "place_id:" + list.get(2).getPlace_id();
                    }
                    waypoints = "&waypoints=" + waypoint_1;

                } else if (list.size() == 4) {
                    waypoint_1 = URLEncoder.encode(list.get(2).getName(), "UTF-8");
                    waypoint_2 = URLEncoder.encode(list.get(3).getName(), "UTF-8");
                    if (!list.get(2).getPlace_id().equals("")) {
                        waypoint_1 = "place_id:" + list.get(2).getPlace_id();
                    }
                    if (!list.get(3).getPlace_id().equals("")) {
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
                    "&language=vi" +
                    "&mode=driving" +
                    "&key=" + key;
            listUrl.add(url);
        }
        return listUrl;
    }

    public static List<String> getGooglePlace(String autoCompleteText) {
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

    public static String getLocationByPlaceID(String input) {
        String key = AppConstants.GOOGLE_KEY;
        String url = "https://maps.googleapis.com/maps/api/place/details/json?" +
                "placeid=" + input +
                "&key=" + key;
        return url;
    }

    public static Pair<String, ArrayList<AutocompleteObject>> getAutoCompleteObject(String searchString) {
        ArrayList<AutocompleteObject> predictionsArr = new ArrayList<>();
        String status = null;
        try {
            //https://maps.googleapis.com/maps/api/place/autocomplete/json?input=Vict&types=geocode&language=fr&sensor=true&key=AddYourOwnKeyHere
            List<String> listUrl = GoogleAPIUtils.getGooglePlace(searchString);
            List<String> json = new ArrayList<>();
            for (int n = 0; n < listUrl.size(); n++) {
                json.add(NetworkUtils.download(listUrl.get(n)));
            }
            if (json.get(0) == null) {
                return null;
            } else {
                //turn that string into a JSON object
                for (int x = 0; x < json.size(); x++) {
                    JSONObject predictions = new JSONObject(json.get(x));
                    //now get the JSON array that's inside that object
                    status = predictions.getString("status");
                    if (status.equals(GoogleApiCode.OVER_QUERY_LIMIT)) {
                        return new Pair<>(status, null);
                    } else {
                        JSONArray ja = new JSONArray(predictions.getString("predictions"));

                        for (int y = 0; y < ja.length(); y++) {
                            JSONObject jo = (JSONObject) ja.get(y);
                            String name = jo.getString("description");
                            String place_id = jo.getString("place_id");
                            predictionsArr.add(new AutocompleteObject(name, place_id));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("YourApp", "GetPlacesTask : doInBackground", e);
        }

        return new Pair<>(status, predictionsArr);
    }
}
