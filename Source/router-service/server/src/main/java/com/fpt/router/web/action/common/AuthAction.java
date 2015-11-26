package com.fpt.router.web.action.common;

import com.fpt.router.artifacter.model.entity.Staff;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.admin.StaffVM;


/**
 * Purpose: All actions need authentication need extends this Action
 * If user doesn't login yet. return to login page. After login successfully will return to old page (by parameter has passed)
 * Created by Huynh Quang Thao on 10/11/15.
 */
public class AuthAction implements IAction {

    @Override
    public String execute(ApplicationContext context) {

        StaffVM staff = (StaffVM) context.getSessionAttribute("user");

        if (staff == null) {
            return null;
        }

        Role role = null;
        int roleNumber = staff.getRole();
        switch (roleNumber) {
            case 0:
                role = Role.ADMIN;
                break;
            case 1:
                role = Role.STAFF;
                break;
        }

        return role != null ? role.name() : null;
    }
}
