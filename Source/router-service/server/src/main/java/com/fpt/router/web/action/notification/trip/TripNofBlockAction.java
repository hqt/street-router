package com.fpt.router.web.action.notification.trip;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.TripNotificationDAO;
import com.fpt.router.artifacter.model.entity.TripNotification;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.common.URL;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/20/2015.
 */
public class TripNofBlockAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {
        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        System.out.println("Blocking Notification");

        String nofIdParam = context.getParameter("nofId");

        if (nofIdParam != null) {
            int nofId = -1;

            try {
                nofId = Integer.parseInt(nofIdParam);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            if (nofId != -1) {
                // change state
                TripNotificationDAO dao = new TripNotificationDAO();
                TripNotification tripNof = dao.read(nofId);
                if (tripNof != null) {
                    tripNof.setState(1);
                    dao.update(tripNof);
                }
            }
        }

        System.out.println("Done Block Trip Notification");
        return Config.WEB.REDIRECT + URL.STAFF.NOF_TRIP_LIST;
    }
}
