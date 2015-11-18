package com.fpt.router.web.action.notification.StationNof;

import com.fpt.router.artifacter.dao.StationNotificationDAO;
import com.fpt.router.artifacter.model.entity.StationNotification;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.notification.NofStationList;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.staff.NofStationListVM;
import com.fpt.router.web.viewmodel.staff.NofStationVM;

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

            NofStationListVM activeItems = new NofStationListVM();
            NofStationListVM blockItems = new NofStationListVM();
            for (StationNotification entity : entityList) {
                if (entity.getState() == 0) {
                    // convert entity to model
                    NofStationVM nofActive = new NofStationVM(entity);
                    nofActive.buildNotification();
                    activeItems.modelL.add(nofActive);
                } else {
                    NofStationVM nofBlock = new NofStationVM(entity);
                    nofBlock.buildNotification();
                    blockItems.modelL.add(nofBlock);
                }
            }

            // assign request attribute
            if (!activeItems.modelL.isEmpty()) {
                context.setAttribute("activeStaNofItems", activeItems);
            }
            if (!blockItems.modelL.isEmpty()) {
                context.setAttribute("blockStaNofItems", blockItems);
            }
        } else {
            System.out.println("Shit here");
        }

        return PAGE.NOTIFICATION.STATION_LIST;
    }
}
