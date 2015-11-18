package com.fpt.router.web.action.notification.StationNof;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.NotificationDAO;
import com.fpt.router.artifacter.dao.StationNotificationDAO;
import com.fpt.router.artifacter.model.entity.Notification;
import com.fpt.router.artifacter.model.entity.StationNotification;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/17/2015.
 */
public class StationNofDeleteAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {

        String notificationId = context.getParameter("nofId");

        if (notificationId == null) {
            System.out.println("Notification Null rồi nhé");
            return Config.AJAX_FORMAT;
        }

        int nofId = -1;
        try {
            nofId = Integer.parseInt(notificationId);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        if (nofId != -1) {
            StationNotificationDAO nofStationDAO = new StationNotificationDAO();
            StationNotification stationNof = nofStationDAO.read(nofId);
            if (stationNof != null) {
                nofStationDAO.delete(stationNof);
            }
            NotificationDAO notificationDAO = new NotificationDAO();
            Notification notification = notificationDAO.read(nofId);
            if (notification != null) {
                notificationDAO.delete(notification);
            }
        }

        return Config.AJAX_FORMAT;
    }

}