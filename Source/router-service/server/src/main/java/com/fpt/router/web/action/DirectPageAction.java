package com.fpt.router.web.action;

import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.config.Config;

/**
 * Created by datnt on 10/10/2015.
 */
public class DirectPageAction implements IAction {

    @Override
    public String execute(ApplicationContext context) {
        return Config.PREFIX + "/login.jsp";
    }
}
