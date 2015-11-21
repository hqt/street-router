package com.fpt.router.web.action.notification.StationNof;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.NotificationDAO;
import com.fpt.router.artifacter.dao.StationDAO;
import com.fpt.router.artifacter.dao.StationNotificationDAO;
import com.fpt.router.artifacter.model.entity.Notification;
import com.fpt.router.artifacter.model.entity.Station;
import com.fpt.router.artifacter.model.entity.StationNotification;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.action.util.StationNofUtils;
import com.fpt.router.web.config.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by datnt on 11/16/2015.
 */
public class StationNofApproveAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {

        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        System.out.println("Begin Approve Station Notification");

        String nofIdParam = context.getParameter("nofId");
        String stationNoParam = context.getParameter("stationNo");
        String stationIdParam = context.getParameter("stationId");
        String notification = context.getParameter("notification");

        if (nofIdParam == null || stationIdParam == null || notification == null) {
            return Config.AJAX_FORMAT;
        }

        int stationId = -1;
        int notificationId = -1;
        try {
            stationId = Integer.parseInt(stationIdParam);
            notificationId = Integer.parseInt(nofIdParam);
        } catch (NumberFormatException ex) {
            System.out.println("Cannot Parsing stationId from notification action");
            ex.printStackTrace();
        }

         // retrieve content value from notification
        String[] content = notification.split(":");
        String[] nof = content[1].split(",");

        Map<String, String> result = new HashMap<String, String>();
        for (String str : nof) {
            StationNofUtils utils = new StationNofUtils(str);
            result.putAll(utils.reverse());
        }
        String name = null, street = null, lat = null, lon = null;
        for (Map.Entry<String, String> entry : result.entrySet()) {
            if (entry.getKey().equals(StationNofUtils.KEY_STATION_NAME)) {
                name = entry.getValue();
            }
            if (entry.getKey().equals(StationNofUtils.KEY_STATION_STREET)) {
                street = entry.getValue();
            }
            if (entry.getKey().equals(StationNofUtils.KEY_STATION_LAT)) {
                lat = entry.getValue();
            }
            if (entry.getKey().equals(StationNofUtils.KEY_STATION_LON)) {
                lon = entry.getValue();
            }
        }

        approve(notificationId, stationId, name, street, lat, lon);

        return Config.AJAX_FORMAT;
    }

    public void approve(int notificationId , int stationId, String name, String street, String lat, String lon) {

        StationDAO stationDAO = new StationDAO();
        Station station = stationDAO.read(stationId);
        boolean canUpdate = false;
        if (station != null) {
            if (name != null) {
                station.setName(name);
                canUpdate = true;
            }
            if (street != null) {
                station.setStreet(street);
                canUpdate = true;
            }
            if (lat != null) {
                double latitude = Double.parseDouble(lat);
                station.setLatitude(latitude);
                canUpdate = true;
            }
            if (lon != null) {
                double longitude = Double.parseDouble(lon);
                station.setLongitude(longitude);
                canUpdate = true;
            }
        }
        if (canUpdate) {
            stationDAO.update(station);
        }

        // delete station notification approved
        StationNotificationDAO staNofDAO = new StationNotificationDAO();
        StationNotification statNof = staNofDAO.read(notificationId);
        if (statNof != null) {
            staNofDAO.delete(statNof);
        }

        NotificationDAO notificationDAO = new NotificationDAO();
        Notification nof = notificationDAO.read(notificationId);
        if (nof != null) {
            notificationDAO.delete(nof);
        }

        System.out.println("Approve Station Notification Finish");
    }
}
