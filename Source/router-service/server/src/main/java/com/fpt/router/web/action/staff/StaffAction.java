package com.fpt.router.web.action.staff;

import com.fpt.router.web.action.common.AuthAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Purpose: All actions of Staff need extend this action.
 * this action will check:
 *      1. authentication (from authentication action)
 *      2. Role
 * Any thing not true will back to login page again (and come to old page again by using passed parameter)
 * Created by Huynh Quang Thao on 10/11/15.
 */
public class StaffAction extends AuthAction {
    public String execute(ApplicationContext context) {
        return super.execute(context);
    }
}
