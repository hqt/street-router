package com.fpt.router.web.action.notification;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/6/2015.
 */
public class NofBlockAction extends StaffAction {

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
                /*NofListVM nofListVM = (NofListVM) context.getSessionAttribute("nofListVM");

                NotificationVM nofInActive = new NotificationVM();
                for (int i = 0; i < nofListVM.getListNofActive().size(); i++) {
                    if (nofListVM.getListNofActive().get(i).getId() == nofId) {
                        nofInActive = nofListVM.getListNofActive().get(i);
                        nofListVM.getListNofActive().remove(i);
                        break;
                    }
                }

                nofListVM.getListNofInActive().add(nofInActive);*/

            }
        }

        System.out.println("Done Block");
        return Config.AJAX_FORMAT;
    }
}
