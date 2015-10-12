package com.fpt.router.web.action;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.RouteVM;

import java.util.List;

/**
 * Created by datnt on 10/11/2015.
 */
public class DetailRouteAction implements IAction {
    @Override
    public String execute(ApplicationContext context) {

        List<Route> routes = (List<Route>) context.getAttribute("routes");


        String id = context.getParameter("routeId");
        long routeId = -1;
        try {
             routeId = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        for (Route route : routes) {
            if (route.getRouteId() == routeId) {
                context.setAttribute("route", route);
            }
        }

        return Config.WEB.PAGE + "detail.jsp"; // return detail view
    }
}
