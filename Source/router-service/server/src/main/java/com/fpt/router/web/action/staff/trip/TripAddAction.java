package com.fpt.router.web.action.staff.trip;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.TripDAO;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Trip;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;
import org.joda.time.LocalTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by datnt on 11/1/2015.
 */
public class TripAddAction extends StaffAction {
    @Override
    public String execute(ApplicationContext context) {

        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        System.out.println("Adding Trip");
        // get parameter
        String newStartTimeParam = context.getParameter("newStartTime");
        String newEndTimeParam = context.getParameter("newEndTime");
        String routeIdParam = context.getParameter("routeId");
        // new trip
        Trip trip = new Trip();
        Route route = new Route();
        if (routeIdParam != null) {
            int routeId = -1;
            try {
                routeId = Integer.parseInt(routeIdParam);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            route.setRouteId(routeId);
        }
        trip.setRoute(route);
        if (newStartTimeParam != null && newEndTimeParam != null) {
            LocalTime startTime = parseTimeFromClient(newStartTimeParam);
            LocalTime endTime = parseTimeFromClient(newEndTimeParam);
            trip.setStartTime(startTime);
            trip.setEndTime(endTime);
        }

        // insert trip of route
        TripDAO tripDAO = new TripDAO();
        tripDAO.create(trip);

        System.out.println("Done Add Trip");

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
