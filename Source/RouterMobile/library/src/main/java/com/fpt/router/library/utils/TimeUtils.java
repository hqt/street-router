package com.fpt.router.library.utils;

import org.joda.time.*;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        return new Time(time.getHourOfDay(), time.getMinuteOfHour(), time.getSecondOfMinute());
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
        LocalTime lct = new LocalTime(hours, minutes);
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

    public static long convertPeriodToMinute(Period period) {
        long millis = convertToMilliseconds(period);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
       /* int minute = (int) (millis / (1000 * 60));*/
        return minutes;
    }

}
