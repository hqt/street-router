package com.fpt.router.web.action.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by datnt on 11/17/2015.
 */
public class NotificationUtils {

    private String notification;
    public static final String KEY_STATION_NAME = "stationName";
    public static final String KEY_STATION_STREET = "stationStreet";
    public static final String KEY_STATION_LAT = "stationLat";
    public static final String KEY_STATION_LON = "stationLon";
    public Map<String, String> result;

    public NotificationUtils(String notification) {
        this.notification = notification;
        this.result = new HashMap<String, String>();
    }

    public final Pattern PATTERN_STATION_NAME = Pattern.compile("tên trạm ([\\w\\s]+)", Pattern.UNICODE_CHARACTER_CLASS);
    public final Pattern PATTERN_STATION_STREET = Pattern.compile("tên đường ([\\w\\s]+)", Pattern.UNICODE_CHARACTER_CLASS);
    public final Pattern PATTERN_STATION_LAT = Pattern.compile("vĩ độ (\\d+\\.\\d+)");
    public final Pattern PATTERN_STATION_LON = Pattern.compile("kinh độ (\\d+\\.\\d+)");

    public Map<String, String> reverse() {

        Matcher stationNameMatcher = PATTERN_STATION_NAME.matcher(notification.trim());
        Matcher stationStreetMatcher = PATTERN_STATION_STREET.matcher(notification.trim());
        Matcher stationLatMatcher = PATTERN_STATION_LAT.matcher(notification.trim());
        Matcher stationLonMatcher = PATTERN_STATION_LON.matcher(notification.trim());
        if (stationNameMatcher.find()) {
            result.put(KEY_STATION_NAME, stationNameMatcher.group(1));
        }

        if (stationStreetMatcher.find()) {
            result.put(KEY_STATION_STREET, stationStreetMatcher.group(1));
        }

        if (stationLatMatcher.find()) {
            result.put(KEY_STATION_LAT, stationLatMatcher.group(1));
        }

        if (stationLonMatcher.find()) {
            result.put(KEY_STATION_LON, stationLonMatcher.group(1));
        }

        return result;
    }

}
