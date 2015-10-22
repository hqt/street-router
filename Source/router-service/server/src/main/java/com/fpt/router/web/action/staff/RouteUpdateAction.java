package com.fpt.router.web.action.staff;

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

        System.out.println("Ngô Tiến Đạt");

        int id = context.getIntParameter("id");
        String name = context.getParameter("name");


        Route route = dao.read(id); // Invoke RouteDAO to update route
        System.out.println("Route Id " +route.getRouteId());
        System.out.println("Route Name Before " +route.getRouteName());
        route.setRouteName(name);
        System.out.println("Route Name After " + route.getRouteName());
        if (route != null) {
            dao.update(route);

            RouteVM routeVM = new RouteVM(route);
            // response json to client
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = null;
            try {
                json = ow.writeValueAsString(routeVM);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter out = context.getWriter();
            out.write(json);

            System.out.println(json);

            return Config.AJAX_FORMAT;
        }

        return null;
    }
}
