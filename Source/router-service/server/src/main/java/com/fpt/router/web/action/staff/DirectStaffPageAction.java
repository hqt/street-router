package com.fpt.router.web.action.staff;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/15/2015.
 */
public class DirectStaffPageAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {
        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        return (String) context.getAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE);
    }
}
