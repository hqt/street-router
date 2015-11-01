package com.fpt.router.utils;

import com.fpt.router.library.model.bus.BusLocation;
import com.fpt.router.library.model.motorbike.DetailLocation;
import com.fpt.router.library.model.motorbike.DetailLocationTwoPoint;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.model.common.Location;
import com.fpt.router.library.model.motorbike.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 9/28/2015.
 */
public class JSONParseUtils {

    // constructor
    public JSONParseUtils() {

    }

    public static List<Leg> getListLegWithTwoPoint(String json){
        Leg leg;
        List<Leg> listLeg = new ArrayList<>();
        String EndAddress;
        String StartAddress;
        DetailLocation legDetailL;
        String Overview_polyline;

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
                ArrayList<Step> listStep = new ArrayList<>();
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
                    } else {
                        Maneuver = "Keep going";
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

    public static List<Leg> sortLegForFourPointWithoutOptimize(List<String> listUrl) {
        List<Leg> legA = new ArrayList<>();
        List<Leg> legB = new ArrayList<>();
        List<Leg> legC = new ArrayList<>();
        List<Leg> listLegFinal = new ArrayList<>();
        Leg leg;
        for(int m = 0; m < 2; m++) {
            legA.clear();
            legB.clear();
            legC.clear();
            legA.addAll(getListLegWithTwoPoint(NetworkUtils.download(listUrl.get(m))));
            legB.addAll(getListLegWithTwoPoint(NetworkUtils.download(listUrl.get(m + 2))));
            legC.addAll(getListLegWithTwoPoint(NetworkUtils.download(listUrl.get(m + 4))));
            for (int x = 0; x < legA.size(); x++) {
                for (int y = 0; y < legB.size(); y++) {
                    for (int z = 0; z < legC.size(); z++) {
                        if (listLegFinal.size() < 9) {
                            listLegFinal.add(legA.get(x));
                            listLegFinal.add(legB.get(y));
                            listLegFinal.add(legC.get(z));
                        } else {
                            int valueCheck = totalTime(legA.get(x), legB.get(y), legC.get(z));
                            int valueFinal = totalTime(listLegFinal.get(6), listLegFinal.get(7), listLegFinal.get(8));
                            if (valueFinal > valueCheck) {
                                listLegFinal.set(6, legA.get(x));
                                listLegFinal.set(7, legB.get(y));
                                listLegFinal.set(8, legC.get(z));
                            }
                            for (int n = 1; n >= 0; n--) {
                                valueCheck = totalTime(listLegFinal.get((n + 1) * 3), listLegFinal.get((n + 1) * 3 + 1), listLegFinal.get((n + 1) * 3 + 2));
                                valueFinal = totalTime(listLegFinal.get(n * 3), listLegFinal.get(n * 3 + 1), listLegFinal.get(n * 3 + 2));
                                if (valueFinal > valueCheck) {
                                    for (int i = 0; i < 3; i++) {
                                        leg = listLegFinal.get((n + 1) * 3 + i);
                                        listLegFinal.set((n + 1) * 3 + i, listLegFinal.get(n * 3 + i));
                                        listLegFinal.set(n * 3 + i, leg);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return listLegFinal;
    }

    public static int totalTime(Leg leg_1, Leg leg_2, Leg leg_3) {
        int total = leg_1.getDetailLocation().getDuration() + leg_2.getDetailLocation().getDuration() + leg_3.getDetailLocation().getDuration();
        return total;
    }

    public static String replaceInstruction (String instruction) {
        String text = instruction;
        text = text.replace("<b>", "");
        text = text.replace("</b>", "");
        text = text.replace("</div>", "");
        text = text.replace("&nbsp;", "");
        text = text.replace("&amp;", "");
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
            text = text.replace("turn-slight-left","Chếch sang trái");
        }else if(text.equals("turn-slight-right")){
            text = text.replace("turn-slight-right","Rẽ phải một tí");
        }else if(text.equals("roundabout-right")){
            text = text.replace("roundabout-right","Vòng xoay bên phải");
        }else if(text.equals("roundabout-left")){
            text = text.replace("roundabout-right","Vòng xoay bên trái");
        }else if(text.equals("merge")){
            text = text.replace("merge","Giao nhau");
        }else if(text.equals("keep-right")){
            text = text.replace("keep-right","Đi bên phải");
        }if(text.equals("keep-left")){
            text = text.replace("keep-left","Đi bên trái");
        }else if(text.equals("ramp-right")){
            text = text.replace("ramp-right","Đi theo lối ra");
        }else if(text.equals("straight")){
            text = text.replace("straight","Đi thẳng");
        }else{
            text = "Quay về";
        }

        return text;
    }

    public static List<Leg> getListLegWithFourPoint(String json){
        Leg leg;
        ArrayList<Leg> listLeg = new ArrayList<>();
        String EndAddress;
        String StartAddress;
        DetailLocation legDetailL;
        String Overview_polyline;

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
            ArrayList<Step> listStep = new ArrayList<>();
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
                    } else {
                        Maneuver = "Keep going";
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
        String distanceText;
        int duration;
        double latitude;
        double longitude;
        JSONObject jsonO;

        try {
            jsonO = jsonORoot.getJSONObject("distance");
            distance = jsonO.getInt("value");
            distanceText = jsonO.getString("text");
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
            detailL.setDistanceText(distanceText);
            return detailL;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> listPlaceID(List<String> listLocation) {
        List<String> listPlaceID = new ArrayList<>();
        JSONObject jsonO = null;
        JSONArray jsonA;
        for(int n = 0; n < listLocation.size(); n++) {
            List<String> listUrl = GoogleAPIUtils.getGooglePlace(listLocation.get(n));
            try {
                for(int i = 0; i < listUrl.size(); i++) {
                    jsonO = new JSONObject(NetworkUtils.download(listUrl.get(i)));
                    jsonA = jsonO.getJSONArray("predictions");
                    for(int x = 0; x < jsonA.length(); x++) {
                        jsonO = jsonA.getJSONObject(x);
                        String oldLocation = listLocation.get(n);
                        String newLocation = jsonO.getString("description");
                        if ((jsonO.getString("place_id") != null) && (oldLocation.equals(newLocation))) {
                            listPlaceID.add(jsonO.getString("place_id"));
                            break;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listPlaceID;
    }
    public static Location getLocation(JSONObject jsonObject){
        JSONArray jsonArray;
        JSONObject object;
        JSONObject geometry;
        JSONObject location;
        double latitude;
        double longitude;
        try{
            jsonArray = (JSONArray) jsonObject.get("results");
            object = jsonArray.getJSONObject(0);
            geometry = (JSONObject) object.get("geometry");
            location = (JSONObject) geometry.get("location");
            latitude = (double) location.get("lat");
            longitude = (double) location.get("lng");
            Location loca = new Location(latitude,longitude);
            return loca;

        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public static BusLocation getBusLocation (String json, String address) throws JSONException {
        JSONObject jsonO = new JSONObject(json);
        jsonO = jsonO.getJSONObject("result");
        jsonO = jsonO.getJSONObject("geometry");
        jsonO = jsonO.getJSONObject("location");
        Double lat = jsonO.getDouble("lat");
        Double lng = jsonO.getDouble("lng");
        BusLocation busLocation = new BusLocation(lat, lng, address);
        return busLocation;
    }
}
