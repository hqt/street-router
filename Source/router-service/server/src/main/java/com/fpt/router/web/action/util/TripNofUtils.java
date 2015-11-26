package com.fpt.router.web.action.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by datnt on 11/19/2015.
 */
public class TripNofUtils {

    private String notification;
    public static final String KEY_STARTTIME = "startTime";
    public static final String KEY_ENDTIME = "endTime";

    public TripNofUtils(String notification) {
        this.notification = notification;
    }

    public static final Pattern PATTERN_TRIP_STARTTIME = Pattern.compile("thời gian khởi hành từ ((\\d+:\\d+) [A|P]M) thành ((\\d+:\\d+) [A|P]M)", Pattern.UNICODE_CHARACTER_CLASS);
    public static final Pattern PATTERN_TRIP_ENDTIME = Pattern.compile("thời gian đến trạm từ ((\\d+:\\d+) [A|P]M) thành ((\\d+:\\d+) [A|P]M)", Pattern.UNICODE_CHARACTER_CLASS);

    public void reverse(Map<String, String> result) {

        Matcher startTimeMatcher = PATTERN_TRIP_STARTTIME.matcher(notification);
        Matcher endTimeMatcher = PATTERN_TRIP_ENDTIME.matcher(notification);

        if (startTimeMatcher.find()) {
            result.put(KEY_STARTTIME, startTimeMatcher.group(3));
        }
        if (endTimeMatcher.find()) {
            result.put(KEY_ENDTIME, endTimeMatcher.group(3));
        }
    }

}
