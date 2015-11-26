package com.fpt.router.web.action.notification.route;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.NotificationDAO;
import com.fpt.router.artifacter.dao.RouteNotificationDAO;
import com.fpt.router.artifacter.model.entity.Notification;
import com.fpt.router.artifacter.model.entity.RouteNotification;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/21/2015.
 */
public class RouteNofDeleteAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {

        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        System.out.println("Deleting Route Notification ...");

        // get parameter
        String nofIdParam = context.getParameter("nofId");

        if (nofIdParam != null) {

            int nofId = -1;

            try {
                nofId = Integer.parseInt(nofIdParam);
            } catch (NumberFormatException ex) {
                System.out.println("Cannot Cast Notification Id in Route Notification List Action line 30");
                ex.printStackTrace();
            }

            if (nofId != -1) {
                // here for delete Route notification
                RouteNotificationDAO routeNofDAO = new RouteNotificationDAO();
                RouteNotification routeNof =  routeNofDAO.read(nofId);
                if (routeNof != null) {
                    routeNofDAO.delete(routeNof);
                }
                NotificationDAO nofDAO = new NotificationDAO();
                Notification nof = nofDAO.read(nofId);
                if (nof != null) {
                    nofDAO.delete(nof);
                }
            }
        }

        System.out.println("Done Delete route notification");
        return Config.AJAX_FORMAT;
    }
}
