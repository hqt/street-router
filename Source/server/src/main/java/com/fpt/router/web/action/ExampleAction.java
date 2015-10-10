package com.fpt.router.web.action;

import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 10/7/2015.
 */
public class ExampleAction implements IAction {


    @Override
    public String execute(ApplicationContext context) {
        System.out.println("ExampleAction, Here for do something");
        /*Route route = new Route();
        route.setRouteNo(1);
        route.setRouteName("Bến Thành - Chợ Rẫy");
        context.setAttribute("route", route);*/
        return "WEB-INF/login.jsp";
    }
}
