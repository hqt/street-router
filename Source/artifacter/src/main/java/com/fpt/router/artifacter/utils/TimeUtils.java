package com.fpt.router.artifacter.utils;

import org.joda.time.*;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

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

    public LocalTime convertExcelDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        LocalTime lct = new LocalTime(hours,minutes);
        return lct;
    }

    public static double toMinute(double second) {
        return ((second / 60) * 100.0) / 100.0;
    }

    public static long convertHourToMillis(int hour) {
        return convertMinuteToMillis(hour * 60);
    }

    public static long convertMinuteToMillis(int minute) {
        return 60L * 1000 * minute;
    }

    public static long convertToMilliseconds(Period period) {
        long millis = convertHourToMillis(period.getHours()) +
                convertMinuteToMillis(period.getMinutes()) +
                period.getSeconds() * 1000 +
                period.getMillis();
        return millis;
    }

}
