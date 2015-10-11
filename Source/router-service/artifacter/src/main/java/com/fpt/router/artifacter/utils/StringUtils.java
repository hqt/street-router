package com.fpt.router.artifacter.utils;

import com.fpt.router.artifacter.model.helper.Location;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class StringUtils {
    public static List<Location> convertToLocations(String str) {
        List<Location> locations = new ArrayList<Location>();
        if (str.trim().length() == 0) return  locations;
        String[] locationStr = str.trim().split("\\s+");
        for (int i = 0; i < locationStr.length; i++) {
            try {
                String[] pair = locationStr[i].split(",");
                Double longitude = Double.parseDouble(pair[0]);
                Double latitude = Double.parseDouble(pair[1]);
                Location location = new Location(latitude, longitude);
                locations.add(location);
            } catch (NumberFormatException e) {
                System.out.println("error format ..." + e.getLocalizedMessage());
            }
        }
        return locations;
    }

    public static String convertLocationToString(List<Location> locations) {
        String res = "";
        DecimalFormat df = new DecimalFormat("0.#######");
        if (locations == null) return res;
        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            res += df.format(location.longitude) + "," + df.format(location.latitude) + " ";
        }
        return res;
    }

}
