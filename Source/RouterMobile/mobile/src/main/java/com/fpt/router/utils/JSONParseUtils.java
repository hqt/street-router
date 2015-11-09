package com.fpt.router.utils;

import com.fpt.router.R;
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

    public static List<Leg> getListLegWithTwoPoint(String json) {
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

            for (int n = 0; n < jsonA.length(); n++) {
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
                for (int i = 0; i < jsonALeg.length(); i++) {
                    jo = jsonALeg.getJSONObject(i);
                    Instruction = jo.getString("html_instructions");
                    Instruction = replaceInstruction(Instruction);
                    if (jo.has("maneuver")) {
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
        List<Leg> listLegFinal = new ArrayList<>();
        Leg leg;
        for (int m = 0; m < 2; m++) {
            List<Leg> legA = getListLegWithTwoPoint(NetworkUtils.download(listUrl.get(m)));
            List<Leg> legB = getListLegWithTwoPoint(NetworkUtils.download(listUrl.get(m + 2)));
            List<Leg> legC = getListLegWithTwoPoint(NetworkUtils.download(listUrl.get(m + 4)));
            for (int x = 0; x < legA.size(); x++) {
                for (int y = 0; y < legB.size(); y++) {
                    for (int z = 0; z < legC.size(); z++) {
                        listLegFinal.add(legA.get(x));
                        listLegFinal.add(legB.get(y));
                        listLegFinal.add(legC.get(z));
                    }
                }
            }
        }

        listLegFinal = sortFourPoint(listLegFinal);
        listLegFinal = getQualityOfList(listLegFinal, 4, 3);
        return listLegFinal;
    }

    public static List<Leg> sortLegForThreePointWithoutOptimize(List<String> listUrl) {
        List<Leg> listLegFinal = new ArrayList<>();
        List<Leg> legA = getListLegWithTwoPoint(NetworkUtils.download(listUrl.get(0)));
        List<Leg> legB = getListLegWithTwoPoint(NetworkUtils.download(listUrl.get(1)));
        for (int a = 0; a < legA.size(); a++) {
            for (int b = 0; b < legB.size(); b++) {
                listLegFinal.add(legA.get(a));
                listLegFinal.add(legB.get(b));
            }
        }
        listLegFinal = sortThreePoint(listLegFinal);
        listLegFinal = getQualityOfList(listLegFinal, 3, 3);
        return listLegFinal;
    }

    public static int totalTime(Leg...leg) {
        int total = 0;
        for(int i = 0; i < leg.length; i++){
            total = total + leg[i].getDetailLocation().getDuration();
        }
        return total;
    }

    public static List<Leg> sortThreePoint(List<Leg> listLeg) {
        List<Leg> listLegFinal = listLeg;
        for (int a = 0; a < listLegFinal.size() / 2 - 1; a++) {
            for (int b = a + 1; b < listLegFinal.size() / 2; b++) {
                int leg_1 = totalTime(listLegFinal.get(a * 2), listLegFinal.get(a * 2 + 1));
                int leg_2 = totalTime(listLegFinal.get(b * 2), listLegFinal.get(b * 2 + 1));
                if (leg_1 > leg_2) {
                    for (int i = 0; i < 2; i++) {
                        Leg leg = listLegFinal.get(a * 2 + i);
                        listLegFinal.set(a * 2 + i, listLegFinal.get(b * 2 + i));
                        listLegFinal.set(b * 2 + i, leg);
                    }
                }
            }
        }
        return listLegFinal;
    }

    public static List<Leg> sortFourPoint(List<Leg> listLeg) {
        List<Leg> listLegFinal = listLeg;
        for (int x = 0; x < listLegFinal.size() / 3 - 1; x++) {
            for (int y = x + 1; y < listLegFinal.size() / 3; y++) {
                int value_1 = totalTime(listLegFinal.get(x * 3),
                        listLegFinal.get(x * 3 + 1), listLegFinal.get(x * 3 + 2));
                int value_2 = totalTime(listLegFinal.get(y * 3),
                        listLegFinal.get(y * 3 + 1), listLegFinal.get(y * 3 + 2));
                if (value_1 > value_2) {
                    for (int i = 0; i < 3; i++) {
                        Leg leg = listLegFinal.get(x * 3 + i);
                        listLegFinal.set(x * 3 + i, listLegFinal.get(y * 3 + i));
                        listLegFinal.set(y * 3 + i, leg);
                    }
                }
            }
        }
        return listLegFinal;
    }

    public static List<Leg> getQualityOfList(List<Leg> listLeg, int point, int get) {
        List<Leg> listLegFinal = listLeg;
        int total = (point - 1) * get;
        for (int i = 0; i < listLegFinal.size(); i++) {
            if (i >= total) {
                listLegFinal.remove(i);
                i--;
            }
        }
        return listLegFinal;
    }

    public static String replaceInstruction(String instruction) {
        String text = instruction;
        text = text.replace("<b>", "");
        text = text.replace("</b>", "");
        text = text.replace("</div>", "");
        text = text.replace("&nbsp;", "");
        text = text.replace("&amp;", "");
        text = text.replace("  ", " ");

        return text;
    }

    public static String replayManeuver(String maneuver) {
        String text = maneuver;
        if(text.equals("turn-sharp-left")){
            text = "Ngoặt trái";
        }
        if(text.equals("uturn-right")){
            text = "Quay đầu về bên phải";
        }
        if(text.equals("turn-slight-right")){
            text = "Chếch sang phải";
        }
        if(text.equals("merge")) {
            text = "Nhập vào";
        }
        if(text.equals("roundabout-left")){
            text = "Tại vòng xuyến";
        }
        if(text.equals("roundabout-right")) {
            text = "Tại vòng xuyến";
        }
        if(text.equals("uturn-left")) {
            text = "Quay đầu về bên trái";
        }
        if(text.equals("turn-slight-left")){
            text = "Chếch bên trái";
        }
        if(text.equals("turn-left")){
            text = "Rẻ trái";
        }
        if(text.equals("ramp-right")){
            text = "Đi theo lối ra bên phải";
        }
        if(text.equals("turn-right")){
            text = "Rẻ phải";
        }
        if(text.equals("fork-right")){
            text = "Ngã ba bên phải";
        }
        if(text.equals("straight")) {
            text = "Đi thẳng";
        }
        if(text.equals("fork-left")){
            text = "Ngã ba bên trái";
        }
        if(text.equals("ferry-train")) {
            text = "Qua sông";
        }
        if(text.equals("turn-sharp-right")) {
            text = "Ngoặt phải";
        }
        if(text.equals("ramp-left")){
            text = "Đi theo lối ra bên trái";
        }
        if(text.equals("ferry")){
            text = "Đi phà";
        }
        if(text.equals("keep-left")) {
            text = "Đi về bên trái";
        }
        if(text.equals("keep-right")) {
            text = "Đi về bên Phải";
        }
        if(text.equals("Keep going")){
            text = "Đi thẳng";
        }
        return text;
    }

    public static List<Leg> getListLegWithFourPoint(String json) {
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
            for (int n = 0; n < jsonA.length(); n++) {
                ArrayList<Step> listStep = new ArrayList<>();
                JSONObject jsonOLeg;
                JSONArray jsonALeg;
                jsonOLeg = jsonA.getJSONObject(n);
                EndAddress = jsonOLeg.getString("end_address");
                StartAddress = jsonOLeg.getString("start_address");
                legDetailL = getDetailLocation(jsonOLeg);

                //get all Step
                jsonALeg = jsonOLeg.getJSONArray("steps");
                for (int i = 0; i < jsonALeg.length(); i++) {
                    jo = jsonALeg.getJSONObject(i);
                    Instruction = jo.getString("html_instructions");
                    Instruction = replaceInstruction(Instruction);
                    if (jo.has("maneuver")) {
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


    public static DetailLocationTwoPoint getDetailLoationTwoPoint(JSONObject jsonObject) {
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
            DetailLocationTwoPoint detailLocationTwoPoint = new DetailLocationTwoPoint(distance, duration, end_location, start_location);
            return detailLocationTwoPoint;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static DetailLocation getDetailLocation(JSONObject jsonORoot) {
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
        for (int n = 0; n < listLocation.size(); n++) {
            List<String> listUrl = GoogleAPIUtils.getGooglePlace(listLocation.get(n));
            try {
                for (int i = 0; i < listUrl.size(); i++) {
                    jsonO = new JSONObject(NetworkUtils.download(listUrl.get(i)));
                    jsonA = jsonO.getJSONArray("predictions");
                    for (int x = 0; x < jsonA.length(); x++) {
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

    public static Location getLocation(JSONObject jsonObject) {
        JSONArray jsonArray;
        JSONObject object;
        JSONObject geometry;
        JSONObject location;
        double latitude;
        double longitude;
        try {
            jsonArray = (JSONArray) jsonObject.get("results");
            object = jsonArray.getJSONObject(0);
            geometry = (JSONObject) object.get("geometry");
            location = (JSONObject) geometry.get("location");
            latitude = (double) location.get("lat");
            longitude = (double) location.get("lng");
            Location loca = new Location(latitude, longitude);
            return loca;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BusLocation getBusLocation(String json, String address) throws JSONException {
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
