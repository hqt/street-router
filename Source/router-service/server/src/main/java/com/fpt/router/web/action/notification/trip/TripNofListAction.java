package com.fpt.router.web.action.notification.trip;

import com.fpt.router.artifacter.dao.TripNotificationDAO;
import com.fpt.router.artifacter.model.entity.TripNotification;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.staff.NofTripListVM;
import com.fpt.router.web.viewmodel.staff.NofTripVM;

import java.util.List;

/**
 * Created by datnt on 11/19/2015.
 */
public class TripNofListAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {

        // authenticate staff
        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        // get list trip notification
        TripNotificationDAO dao = new TripNotificationDAO();
        List<TripNotification> entities = dao.findAll();
        NofTripListVM viewActive = new NofTripListVM();
        NofTripListVM viewInActive = new NofTripListVM();
        if (entities != null &&! entities.isEmpty()) {
            for (TripNotification entity : entities) {
                if (entity.getState() == 0) {
                    NofTripVM nofVM = new NofTripVM(entity);
                    nofVM.buildNotification();
                    viewActive.modelL.add(nofVM);
                } else {
                    NofTripVM nofVM = new NofTripVM(entity);
                    nofVM.buildNotification();
                    viewInActive.modelL.add(nofVM);
                }
            }
        }

        if (viewActive.modelL != null && !viewActive.modelL.isEmpty()) {
            context.setAttribute("modelActiveTripNofList", viewActive);
        }
        if (viewInActive.modelL != null && !viewInActive.modelL.isEmpty()) {
            context.setAttribute("modelInActiveTripNofList", viewInActive);
        }

        return PAGE.NOTIFICATION.TRIP_LIST;
    }
}
