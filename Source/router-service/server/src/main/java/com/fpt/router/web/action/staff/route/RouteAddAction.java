package com.fpt.router.web.action.staff.route;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/1/2015.
 */
public class RouteAddAction implements IAction {

    @Override
    public String execute(ApplicationContext context) {
        return Config.WEB.PAGE + "/route/indexAdd.jsp";
    }
}
