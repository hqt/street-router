package com.fpt.router.web.action.staff.trip;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.TripDAO;
import com.fpt.router.artifacter.model.entity.Trip;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;
import org.joda.time.LocalTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by datnt on 11/3/2015.
 */
public class TripUpdateAction implements IAction {
    @Override
    public String execute(ApplicationContext context) {

        // get parameter
        String tripIdParam = context.getParameter("tripId");
        String tripStartTimeParam = context.getParameter("startTime");
        String tripEndTimeParam = context.getParameter("endTime");

        // update trip of route
        if (tripIdParam != null) {
            int tripId = -1;
            try {
                tripId = Integer.parseInt(tripIdParam);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            if (tripId != - 1) {
                // check trip already exist in db or not
                TripDAO tripDAO = new TripDAO();
                Trip trip = tripDAO.read(tripId);
                if (trip != null) {
                    // process time got from client
                    LocalTime startTime = parseTimeFromClient(tripStartTimeParam);
                    LocalTime endTime = parseTimeFromClient(tripEndTimeParam);
                    if (startTime != null && endTime != null) {
                        // update trip
                        trip.setStartTime(startTime);
                        trip.setEndTime(endTime);
                        tripDAO.update(trip);
                    }
                }
            }
        }

        return Config.AJAX_FORMAT;
    }

    public LocalTime parseTimeFromClient(String timeClient) {
        String patternTime = "h:mm a";
        SimpleDateFormat simpleTime = new SimpleDateFormat(patternTime);
        int hour = -1;
        int minute = -1;

        try {
            Date d = simpleTime.parse(timeClient);
            hour = d.getHours();
            minute = d.getMinutes();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (hour != -1 && minute != -1) {
            return new LocalTime(hour, minute);
        }
        return null;
    }
}
