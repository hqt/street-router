package com.fpt.router.utils;

import android.content.Context;
import android.widget.Toast;

import com.fpt.router.model.motorbike.DetailLocation;
import com.fpt.router.model.motorbike.DetailLocationTwoPoint;
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

    public static ArrayList<Leg> getListLegWithTwoPoint(String json){
        Leg leg;
        ArrayList<Leg> listLeg = new ArrayList<>();
        String EndAddress;
        String StartAddress;
        DetailLocation legDetailL;
        String Overview_polyline;

        ArrayList<Step> listStep = new ArrayList<>();
        String Instruction;
        String Maneuver;
        DetailLocation stepDetailL;
        JSONObject jo;
        JSONObject jsonO;
        JSONArray jsonA;
        try {
            jsonO = new JSONObject(json);
            jsonA = jsonO.getJSONArray("routes");

            for(int n = 0; n < jsonA.length(); n++) {
                JSONObject jsonOLeg;
                JSONArray jsonALeg;
                jsonOLeg = jsonA.getJSONObject(n);

                //get overview polyline
                jo = jsonOLeg.getJSONObject("overview_polyline");
                Overview_polyline = jo.getString("points");

                jsonALeg = jsonOLeg.getJSONArray("legs");
                jsonOLeg = jsonALeg.getJSONObject(0);
                EndAddress = jsonOLeg.getString("end_address");
                StartAddress = jsonOLeg.getString("start_address");
                legDetailL = getDetailLocation(jsonOLeg);

                //get all Step
                jsonALeg = jsonOLeg.getJSONArray("steps");
                for(int i = 0 ;  i < jsonALeg.length() ; i++){
                    jo = jsonALeg.getJSONObject(i);
                    Instruction = jo.getString("html_instructions");
                    Instruction = replaceInstruction(Instruction);
                    if(jo.has("maneuver")) {
                        Maneuver = jo.getString("maneuver");
                        Maneuver = replayManeuver(Maneuver);
                    } else {
                        Maneuver = "Keep going";
                        Maneuver = replayManeuver(Maneuver);
                    }
                    stepDetailL = getDetailLocation(jo);
                    listStep.add(new Step(Instruction, Maneuver, stepDetailL));
                }
                leg = new Leg(EndAddress, StartAddress, legDetailL, listStep, Overview_polyline);
                listLeg.add(leg);
            }

            return listLeg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static String replaceInstruction (String instruction) {
        String text = instruction;
        text = text.replace("<b>", "");
        text = text.replace("</b>", "");
        text = text.replace("<div style=\"font-size:0.9em\">", "");
        text = text.replace("</div>", "");
        text = text.replace("&nbsp;", "");
        text = text.replace("at", "tại");
        text = text.replace("At", "Tại");
        text = text.replace("onto", "trên đường");
        text = text.replace("continue", "tiếp tục đi");
        text = text.replace("Continue", "Tiếp tục đi");
        text = text.replace("Turn right", "Quẹo phải");
        text = text.replace("Turn left", "Quẹo trái");
        text = text.replace("Pass by", ". Đi qua");
        text = text.replace("Slight right", "Quẹo phải một chút");
        text = text.replace("Slight left", "Quẹo trái một chút");
        text = text.replace("on the right", "phía bên phải");
        text = text.replace("on the left", "phía bên trái");
        text = text.replace("Head west on", "Đi theo hướng tây trên đường");
        text = text.replace("Head east on", "Đi theo hướng đông trên đường");
        text = text.replace("Head south on", "Đi theo hướng nam trên đường");
        text = text.replace("Head north on", "Đi theo hướng bắc trên đường");
        text = text.replace("toward", "hướng về đường");
        text = text.replace("Sharp left", "Quẹo ngược lại về phía bên trái");
        text = text.replace("Sharp right", "Quẹo ngược lại về phía bên phải");
        text = text.replace("Enter", "Đi vào");
        text = text.replace("  ", " ");

        return text;
    }

    public static String replayManeuver(String maneuver){
        String text = maneuver;
        if(text.equals("Keep going")){
            text = text.replace("Keep going","Đi thẳng");
        }else if(text.equals("turn-left")){
            text = text.replace("turn-left","Rẽ trái");
        }else if(text.equals("turn-right")){
            text = text.replace("turn-right","Rẽ phải");
        }else if(text.equals("turn-slight-left")){
            text = text.replace("turn-slight-left","Rẽ trái một tí");
        }else if(text.equals("turn-slight-right")){
            text = text.replace("turn-slight-right","Rẽ phải một tí");
        }else if(text.equals("roundabout-right")){
            text = text.replace("roundabout-right","Vòng xoay bên phải");
        }else if(text.equals("roundabout-left")){
            text = text.replace("roundabout-right","Vòng xoay bên trái");
        }else if(text.equals("merge")){
            text = text.replace("merge","Giao nhau");
        }else{
            text = "Quay về";
        }

        return text;
    }

    public static ArrayList<Leg> getListLegWithFourPoint(String json){
        Leg leg;
        ArrayList<Leg> listLeg = new ArrayList<>();
        String EndAddress;
        String StartAddress;
        DetailLocation legDetailL;
        String Overview_polyline;

        ArrayList<Step> listStep = new ArrayList<>();
        String Instruction;
        String Maneuver;
        DetailLocation stepDetailL;
        JSONObject jo;
        JSONObject jsonO;
        JSONArray jsonA;
        try {
            jsonO = new JSONObject(json);
            jsonA = jsonO.getJSONArray("routes");
            jsonO = jsonA.getJSONObject(0);

            //get overview polyline
            jo = jsonO.getJSONObject("overview_polyline");
            Overview_polyline = jo.getString("points");

            jsonA = jsonO.getJSONArray("legs");
            for(int n = 0; n < jsonA.length(); n++) {
                JSONObject jsonOLeg;
                JSONArray jsonALeg;
                jsonOLeg = jsonA.getJSONObject(n);
                EndAddress = jsonOLeg.getString("end_address");
                StartAddress = jsonOLeg.getString("start_address");
                legDetailL = getDetailLocation(jsonOLeg);

                //get all Step
                jsonALeg = jsonOLeg.getJSONArray("steps");
                for(int i = 0 ;  i < jsonALeg.length() ; i++){
                    jo = jsonALeg.getJSONObject(i);
                    Instruction = jo.getString("html_instructions");
                    Instruction = replaceInstruction(Instruction);
                    if(jo.has("maneuver")) {
                        Maneuver = jo.getString("maneuver");
                        Maneuver = replayManeuver(Maneuver);
                    } else {
                        Maneuver = "Keep going";
                        Maneuver = replayManeuver(Maneuver);
                    }
                    stepDetailL = getDetailLocation(jo);
                    listStep.add(new Step(Instruction, Maneuver, stepDetailL));
                }
                leg = new Leg(EndAddress, StartAddress, legDetailL, listStep, Overview_polyline);
                listLeg.add(leg);
            }

            return listLeg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DetailLocationTwoPoint getDetailLoationTwoPoint(JSONObject jsonObject){
        String distance;
        String duration;
        double latitude;
        double longitude;
        JSONObject jsonO;

        try {
            jsonO = jsonObject.getJSONObject("distance");
            distance = jsonO.getString("text");
            jsonO = jsonObject.getJSONObject("duration");
            duration = jsonO.getString("text");
            jsonO = jsonObject.getJSONObject("end_location");
            latitude = jsonO.getDouble("lat");
            longitude = jsonO.getDouble("lng");
            Location end_location = new Location(latitude, longitude);
            jsonO = jsonObject.getJSONObject("start_location");
            latitude = jsonO.getDouble("lat");
            longitude = jsonO.getDouble("lng");
            Location start_location = new Location(latitude, longitude);
            DetailLocationTwoPoint detailLocationTwoPoint = new DetailLocationTwoPoint(distance,duration,end_location,start_location);
            return detailLocationTwoPoint;
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
