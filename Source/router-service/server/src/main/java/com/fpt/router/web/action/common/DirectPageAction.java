package com.fpt.router.web.action.common;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 10/10/2015.
 */
public class DirectPageAction implements IAction {

    @Override
    public String execute(ApplicationContext context) {
        return (String) context.getAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE);
    }
}
