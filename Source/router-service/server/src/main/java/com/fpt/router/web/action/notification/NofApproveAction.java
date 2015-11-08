package com.fpt.router.web.action.notification;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/6/2015.
 */
public class NofApproveAction implements IAction {
    @Override
    public String execute(ApplicationContext context) {
        System.out.println("Approving Notification");
        return Config.AJAX_FORMAT;
    }
}
