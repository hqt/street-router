package com.fpt.router.web.action.notification.trip;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.NotificationDAO;
import com.fpt.router.artifacter.dao.TripNotificationDAO;
import com.fpt.router.artifacter.model.entity.Notification;
import com.fpt.router.artifacter.model.entity.TripNotification;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.common.URL;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * Created by datnt on 11/23/2015.
 */
public class TripNofRejectAllAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {

        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        TripNotificationDAO tripNofDAO = new TripNotificationDAO();
        List<TripNotification> items = tripNofDAO.findAll();

        // reject all trip notification
        tripNofDAO.deleteAll();
        NotificationDAO nofDAO = new NotificationDAO();
        for (TripNotification trip : items) {
            Notification nof = new Notification();
            nof.setNotificationId(trip.getNotificationId());
            nof.setCreatedTime(new Date());
            nofDAO.delete(nof);
        }

        return Config.WEB.REDIRECT + URL.STAFF.NOF_TRIP_LIST;
    }
}
