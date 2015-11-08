package com.fpt.router.web.action;

import org.joda.time.LocalTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by datnt on 11/3/2015.
 */
public class asd {


    public String convertLocalTimeToString(LocalTime localTime) {

        localTime = new LocalTime(20, 30);
        Date date = localTime.toDateTimeToday().toDate();
        String patternTime = "h:mm a";
        SimpleDateFormat simpleTime = new SimpleDateFormat(patternTime);
        String result = simpleTime.format(date);

        if (result != null) {
            return result;
        }

        return "";
    }

    public static void main(String args[]) {

    }

}
