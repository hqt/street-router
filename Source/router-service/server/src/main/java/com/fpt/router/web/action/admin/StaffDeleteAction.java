package com.fpt.router.web.action.admin;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/5/2015.
 */
public class StaffDeleteAction extends AdminAction {

    @Override
    public String execute(ApplicationContext context) {
        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.ADMIN.name())) {
            return PAGE.COMMON.LOGIN;
        }

        String staffIdParam = context.getParameter("staffId");

        if (staffIdParam != null) {
            int staffId = -1;
            try {
                staffId = Integer.parseInt(staffIdParam);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            if (staffId != -1) {
                System.out.println("Delete staff here... who have Id" + staffId);
            }
        }

        return Config.AJAX_FORMAT;
    }
}
