package com.fpt.router.utils;

import com.fpt.router.model.motorbike.detailLocation;
import com.fpt.router.model.motorbike.leg;
import com.fpt.router.model.motorbike.location;
import com.fpt.router.model.motorbike.step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by USER on 9/28/2015.
 */
public class JSONParseUtils {

    // constructor
    public JSONParseUtils() {

    }

    public static leg getLeg(String json){
        String lEndAddress;
        String lStartAddress;
        detailLocation lDetailL;
        String lOverview_polyline;

        ArrayList<step> listStep = new ArrayList<step>();
        String sInstruction;
        String sManeuver;
        detailLocation sDetailL;

        JSONObject jo;

        JSONObject jsonO;
        JSONArray jsonA;
        try {
            jsonO = new JSONObject(json);
            jsonA = jsonO.getJSONArray("routes");
            jsonO = jsonA.getJSONObject(0);

            //get overview polyline
            jo = jsonO.getJSONObject("overview_polyline");
            lOverview_polyline = jo.getString("points");
            jsonA = jsonO.getJSONArray("legs");
            jsonO = jsonA.getJSONObject(0);
            lEndAddress = jsonO.getString("end_address");
            lStartAddress = jsonO.getString("start_address");
            lDetailL = getDetailLocation(jsonO);

            //get all step
            jsonA = jsonO.getJSONArray("steps");
            for(int n = 0 ;  n < jsonA.length() ; n++){
                jo = jsonA.getJSONObject(n);
                sInstruction = jo.getString("html_instructions");
                if(jo.has("maneuver")) {
                    sManeuver = jo.getString("maneuver");
                } else {
                    sManeuver = "Keep going";
                }
                sDetailL = getDetailLocation(jo);
                listStep.add(new step(sInstruction, sManeuver, sDetailL));
            }

            leg l = new leg(lEndAddress, lStartAddress,lDetailL, listStep, lOverview_polyline);
            return l;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static detailLocation getDetailLocation(JSONObject jsonORoot){
        int distance;
        int duration;
        double latitude;
        double longitude;
        JSONObject jsonO;

        try {
            jsonO = jsonORoot.getJSONObject("distance");
            distance = jsonO.getInt("value");
            jsonO = jsonORoot.getJSONObject("duration");
            duration = jsonO.getInt("value");
            jsonO = jsonORoot.getJSONObject("end_location");
            latitude = jsonO.getDouble("lat");
            longitude = jsonO.getDouble("lng");
            location end_location = new location(latitude, longitude);
            jsonO = jsonORoot.getJSONObject("start_location");
            latitude = jsonO.getDouble("lat");
            longitude = jsonO.getDouble("lng");
            location start_location = new location(latitude, longitude);
            detailLocation detailL = new detailLocation(distance, duration, end_location, start_location);
            return detailL;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
