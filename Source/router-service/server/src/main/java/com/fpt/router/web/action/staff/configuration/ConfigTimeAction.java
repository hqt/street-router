package com.fpt.router.web.action.staff.configuration;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by datnt on 11/7/2015.
 */
public class ConfigTimeAction implements IAction {

    public static String patternTime = "hh:mm a";
    @Override
    public String execute(ApplicationContext context) {

        System.out.println("Configuring... Time");

        String[] repeatsDay = context.getParameterValues("repeatDay");
        List<Integer> rDays = new ArrayList<Integer>();
        for (String s : repeatsDay) {
            int day = -1;
            try {
                day = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            if (day != -1) {
                rDays.add(day);
            }
        }

        String time = context.getParameter("timeConfigure");

        SimpleDateFormat simpleDate = new SimpleDateFormat(patternTime);
        Date d = null;
        try {
            d = simpleDate.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (d != null && !rDays.isEmpty()) {
            pairTime(rDays, d);
        }

        System.out.println("Done configure time");

        return Config.WEB.PAGE + "/configure/index.jsp";
    }

    public void pairTime(List<Integer> repeatDay, Date d) {

        Date currentDate = new Date();

        int hour = currentDate.getHours();
        int min = currentDate.getMinutes();

    }
}
