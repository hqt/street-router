package com.fpt.router.web.action.notification.StationNof;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.NotificationDAO;
import com.fpt.router.artifacter.dao.StationNotificationDAO;
import com.fpt.router.artifacter.model.entity.Notification;
import com.fpt.router.artifacter.model.entity.StationNotification;
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
public class StationNofRejectAllAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {

        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        StationNotificationDAO stationNofDAO = new StationNotificationDAO();
        List<StationNotification> items = stationNofDAO.findAll();

        // reject all station notification
        stationNofDAO.deleteAll();
        NotificationDAO notificationDAO = new NotificationDAO();
        for (StationNotification stationNof : items) {
            Notification nof = new Notification();
            nof.setNotificationId(stationNof.getNotificationId());
            nof.setCreatedTime(new Date());
            notificationDAO.delete(nof);
        }

        return Config.WEB.REDIRECT + URL.STAFF.NOF_STATION_LIST;
    }
}
