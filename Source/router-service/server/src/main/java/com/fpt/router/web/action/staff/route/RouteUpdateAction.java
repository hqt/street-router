package com.fpt.router.web.action.staff.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.staff.RouteVM;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by datnt on 10/19/2015.
 */
public class RouteUpdateAction implements IAction {

    public static final RouteDAO dao = new RouteDAO();

    @Override
    public String execute(ApplicationContext context) {

        System.out.println("Updating...");

        // get parameter
        String routeIdParam = context.getParameter("routeId");
        String routeNameParam = context.getParameter("routeName");

        // cast string param to string
        if (routeIdParam != null && routeNameParam != null) {
            int routeId = -1;
            try {
                routeId = Integer.parseInt(routeIdParam);
            } catch (NumberFormatException ex) {
                System.out.println("Wrong casting");
                ex.printStackTrace();
            }
            if (routeId != -1) {
                // update route name
                RouteDAO routeDAO = new RouteDAO();
                Route route = routeDAO.read(routeId);
                System.out.println(routeNameParam);
                if (route != null) {
                    route.setRouteName(routeNameParam);
                }
                routeDAO.update(route);
            }
        }

        return Config.AJAX_FORMAT;
    }
}
