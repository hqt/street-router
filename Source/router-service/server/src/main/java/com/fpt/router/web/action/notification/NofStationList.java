package com.fpt.router.web.action.notification;

import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/6/2015.
 */
public class NofStationList extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {
        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        /*System.out.println("Notification list Action");


        NofListVM nofListVM =  new NofListVM();

        NotificationVM notificationVM = new NotificationVM(0, "Tuyến xe số 18 có thay đổi thời gian", 0);
        NotificationVM notificationVM1 = new NotificationVM(0, "Tuyến xe số 19 có thay đổi thời gian", 1);
        NotificationVM notificationVM2 = new NotificationVM(0, "Tuyến xe số 20 có thay đổi thời gian", 1);
        NotificationVM notificationVM3 = new NotificationVM(0, "Tuyến xe số 21 có thay đổi thời gian", 0);
        NotificationVM notificationVM4 = new NotificationVM(0, "Tuyến xe số 22 có thay đổi thời gian", 0);

        nofListVM.getListNofActive().add(notificationVM);
        nofListVM.getListNofActive().add(notificationVM3);
        nofListVM.getListNofActive().add(notificationVM2);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);
        nofListVM.getListNofActive().add(notificationVM4);


        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);
        nofListVM.getListNofInActive().add(notificationVM1);

        context.setAttribute("nofListVM", nofListVM);*/

        return PAGE.NOTIFICATION.STATION_LIST;
    }
}
