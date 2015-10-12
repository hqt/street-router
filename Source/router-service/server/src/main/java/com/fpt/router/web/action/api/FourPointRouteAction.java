package com.fpt.router.web.action.api;

import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/12/15.
 */
public class FourPointRouteAction implements IAction {

    @Override
    public String execute(ApplicationContext context) {

        // get all parameters
        String latFromStr = context.getParameter("latFromStr");
        String latBStr = context.getParameter("latB");
        String longAStr = context.getParameter("longA");
        String longBStr = context.getParameter("longB");

        String addressA = context.getParameter("addressA");
        String addressB = context.getParameter("addressB");

        return null;


    }
}
