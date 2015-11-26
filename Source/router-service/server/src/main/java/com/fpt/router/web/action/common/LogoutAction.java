package com.fpt.router.web.action.common;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/20/2015.
 */
public class LogoutAction implements IAction {
    @Override
    public String execute(ApplicationContext context) {

        // get session attribute of user
        if (context.getSessionAttribute("user") != null) {
            context.invalidate();
            return PAGE.COMMON.LOGIN;
        }

        return Config.AJAX_FORMAT;
    }
}
