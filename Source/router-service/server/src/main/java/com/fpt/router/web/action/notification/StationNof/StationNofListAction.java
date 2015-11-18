package com.fpt.router.web.action.notification.StationNof;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.StationNotificationDAO;
import com.fpt.router.artifacter.model.entity.StationNotification;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.common.URL;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.staff.NofStationListVM;

import java.util.List;

/**
 * Created by datnt on 11/16/2015.
 */
public class StationNofListAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {

        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }
        // find station notification
        StationNotificationDAO dao = new StationNotificationDAO();
        List<StationNotification> entityList = dao.findAll();
        if (entityList != null && !entityList.isEmpty()) {
            // convert entity to model
            NofStationListVM nof = new NofStationListVM(entityList);
            // assign request attribute
            context.setAttribute("stationNofList", nof);
        } else {
            System.out.println("Shit here");
        }

        return PAGE.NOTIFICATION.STATION_LIST;
    }
}
