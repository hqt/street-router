package com.fpt.router.web.action.notification.trip;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.NotificationDAO;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.dao.TripDAO;
import com.fpt.router.artifacter.dao.TripNotificationDAO;
import com.fpt.router.artifacter.model.entity.Notification;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Trip;
import com.fpt.router.artifacter.model.entity.TripNotification;
import com.fpt.router.artifacter.model.helper.RouteType;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.common.URL;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.action.util.TripNofUtils;
import com.fpt.router.web.config.ApplicationContext;
import org.joda.time.LocalTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by datnt on 11/19/2015.
 */
public class TripNofApproveAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {

        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        System.out.println("Approving... Trip Notification!");

        // get parameter from browser
        String notificationIdParam = context.getParameter("nofId");
        String notificationParam = context.getParameter("notification");
        String tripNoParam = context.getParameter("tripNo");
        String routeNoParam = context.getParameter("routeNo");
        String routeTypeParam = context.getParameter("routeType");

        if (tripNoParam == null || routeNoParam == null || notificationParam == null || notificationIdParam == null) {
            return Config.AJAX_FORMAT;
        }

        int nofId = -1, tripNo = -1, routeNo = -1;
        try {
            tripNo = Integer.parseInt(tripNoParam);
            routeNo = Integer.parseInt(routeNoParam);
            nofId = Integer.parseInt(notificationIdParam);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        // reverse string
        String[] content = notificationParam.split("~");
        String[] nof = content[1].trim().split(",");

        Map<String, String> result = new HashMap<String, String>();
        for (String str : nof) {
            TripNofUtils utils = new TripNofUtils(str.trim());
            utils.reverse(result);
        }

        String startTime = null, endTime = null;
        String resultStartTime = result.get(TripNofUtils.KEY_STARTTIME);
        if (resultStartTime != null) {
            startTime = resultStartTime;
        }
        String resultEndTime = result.get(TripNofUtils.KEY_ENDTIME);
        if (resultEndTime != null) {
            endTime = resultEndTime;
        }

        // approve trip
        approve(nofId, tripNo, routeNo, RouteType.valueOf(routeTypeParam.toUpperCase()), startTime, endTime);

        return Config.AJAX_FORMAT;
    }

    public void approve(int notificationId, int tripNo, int routeNo, RouteType routeType, String startTime, String endTime) {

        TripDAO tripDAO = new TripDAO();
        RouteDAO routeDAO = new RouteDAO();
        Route route = routeDAO.getRoutebyRouteNo(routeNo, routeType);
        Trip trip = tripDAO.readTripByRouteAndNo(route, tripNo);

        // if cannot find trip -> return and do nothing
        if (trip == null) {
            System.out.println("Approve Action have something wrong");
            return;
        }

        boolean canApprove = false;
        LocalTime start = null;
        if (startTime != null) {
            start = parseTimeFromClient(startTime);
            canApprove  = true;
        }
        LocalTime end = null;
        if (endTime != null) {
            end = parseTimeFromClient(endTime);
            canApprove = true;
        }

        if (canApprove) {
            if (start != null) {
                trip.setStartTime(start);
            }
            if (end != null) {
                trip.setEndTime(end);
            }
            tripDAO.update(trip);
        }

        // delete trip notification
        TripNotificationDAO tripNotificationDAO = new TripNotificationDAO();
        TripNotification tripNof = tripNotificationDAO.read(notificationId);
        tripNotificationDAO.delete(tripNof);

        // delete notification
        NotificationDAO notificationDAO = new NotificationDAO();
        Notification nof = notificationDAO.read(notificationId);
        notificationDAO.delete(nof);

        System.out.println("Trip Notification Approved");
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
