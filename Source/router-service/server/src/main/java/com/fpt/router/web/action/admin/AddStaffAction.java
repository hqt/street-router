package com.fpt.router.web.action.admin;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/12/15.
 */
public class AddStaffAction implements IAction {


    @Override
    public String execute(ApplicationContext context) {
        return Config.WEB.PAGE + "/staff/indexCreate.jsp";
    }
}
