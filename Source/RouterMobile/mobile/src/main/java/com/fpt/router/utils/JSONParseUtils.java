package com.fpt.router.utils;

import com.fpt.router.model.motorbike.DetailLocation;
import com.fpt.router.model.motorbike.Leg;
import com.fpt.router.model.motorbike.Location;
import com.fpt.router.model.motorbike.Step;

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

    public static Leg getLeg(String json){
        String lEndAddress;
        String lStartAddress;
        DetailLocation lDetailL;
        String lOverview_polyline;

        ArrayList<Step> listStep = new ArrayList<Step>();
        String sInstruction;
        String sManeuver;
        DetailLocation sDetailL;

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

            //get all Step
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
                listStep.add(new Step(sInstruction, sManeuver, sDetailL));
            }

            Leg l = new Leg(lEndAddress, lStartAddress,lDetailL, listStep, lOverview_polyline);
            return l;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DetailLocation getDetailLocation(JSONObject jsonORoot){
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
            Location end_location = new Location(latitude, longitude);
            jsonO = jsonORoot.getJSONObject("start_location");
            latitude = jsonO.getDouble("lat");
            longitude = jsonO.getDouble("lng");
            Location start_location = new Location(latitude, longitude);
            DetailLocation detailL = new DetailLocation(distance, duration, end_location, start_location);
            return detailL;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
