package com.fpt.router.web.action.notification.StationNof;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.StationNotificationDAO;
import com.fpt.router.artifacter.model.entity.StationNotification;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.common.URL;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/20/2015.
 */
public class StationNofUnBlockAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {

        System.out.println("shit here");

        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

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
                StationNotificationDAO dao = new StationNotificationDAO();
                StationNotification stationNof = dao.read(nofId);
                if (stationNof != null) {
                    stationNof.setState(0);
                    dao.update(stationNof);
                }
            }
        }
        return Config.WEB.REDIRECT + URL.STAFF.NOF_STATION_LIST;
    }
}
