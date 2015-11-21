package com.fpt.router.web.action.notification.trip;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.NotificationDAO;
import com.fpt.router.artifacter.dao.TripNotificationDAO;
import com.fpt.router.artifacter.model.entity.Notification;
import com.fpt.router.artifacter.model.entity.TripNotification;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/20/2015.
 */
public class TripNofDeleteAction extends StaffAction{

    @Override
    public String execute(ApplicationContext context) {
        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        System.out.println("Deleting Trip Notification...");

        String nofIdParam = context.getParameter("nofId");

        if (nofIdParam != null) {
            int nofId = -1;

            try {
                nofId = Integer.parseInt(nofIdParam);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            // delete trip notification
            TripNotificationDAO tripNotificationDAO = new TripNotificationDAO();
            TripNotification tripNof = tripNotificationDAO.read(nofId);
            if (tripNof != null) {
                tripNotificationDAO.delete(tripNof);
            }
            NotificationDAO notificationDAO = new NotificationDAO();
            Notification notification = notificationDAO.read(nofId);
            if (notification != null) {
                notificationDAO.delete(notification);
            }
        }

        System.out.println("Done delete trip notification");
        return Config.AJAX_FORMAT;
    }

}
