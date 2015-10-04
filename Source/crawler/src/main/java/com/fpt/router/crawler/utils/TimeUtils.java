package com.fpt.router.crawler.utils;

import org.joda.time.LocalTime;

import java.sql.Time;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class TimeUtils {

    /**
     * Reference for Brad Richards reference about hole in Time object
     * http://stackoverflow.com/questions/9422753/converting-joda-localtime-to-sql-time
     */
    public static Time convert(LocalTime time) {
        return new java.sql.Time(time.getHourOfDay(), time.getMinuteOfHour(), time.getSecondOfMinute());
    }

    /**
     * Reference for Brad Richards reference about hole in Time object
     * http://stackoverflow.com/questions/9422753/converting-joda-localtime-to-sql-time
     */
    public static LocalTime convert(Time time) {
        return new LocalTime(time.getHours(), time.getMinutes(), time.getSeconds());
    }


}
