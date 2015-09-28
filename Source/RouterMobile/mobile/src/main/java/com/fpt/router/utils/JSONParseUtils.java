package com.fpt.router.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by USER on 9/28/2015.
 */
public class JSONParseUtils {

    // constructor
    public JSONParseUtils() {

    }

    public static String getLocalname(String json){
        JSONObject jsonO;
        JSONArray jsonA;
        String name = "Fail";
        try {
            jsonO = new JSONObject(json);
            jsonA = jsonO.getJSONArray("routes");
            jsonO = jsonA.getJSONObject(0);
            jsonA = jsonO.getJSONArray("legs");
            jsonO = jsonA.getJSONObject(0);
            name = jsonO.getString("start_address");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static Double getLatitude(String json){
        JSONObject jsonO;
        JSONArray jsonA;
        Double latitude = 0.0;
        try {
            jsonO = new JSONObject(json);
            jsonA = jsonO.getJSONArray("routes");
            jsonO = jsonA.getJSONObject(0);
            jsonA = jsonO.getJSONArray("legs");
            jsonO = jsonA.getJSONObject(0);
            jsonO = jsonO.getJSONObject("start_location");
            latitude = jsonO.getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return latitude;
    }

    public static Double getLongitude(String json){
        JSONObject jsonO;
        JSONArray jsonA;
        Double longitude = 0.0;
        try {
            jsonO = new JSONObject(json);
            jsonA = jsonO.getJSONArray("routes");
            jsonO = jsonA.getJSONObject(0);
            jsonA = jsonO.getJSONArray("legs");
            jsonO = jsonA.getJSONObject(0);
            jsonO = jsonO.getJSONObject("start_location");
            longitude = jsonO.getDouble("lng");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return longitude;
    }
}
