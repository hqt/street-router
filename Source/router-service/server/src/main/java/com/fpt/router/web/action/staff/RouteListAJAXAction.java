package com.fpt.router.web.action.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.staff.RouteListVM;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by datnt on 10/11/2015.
 */
public class RouteListAJAXAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {

        @SuppressWarnings("unchecked") List<Route> routes = (List<Route>) context.getSessionAttribute("routes"); // to get routes from session
        int limit = context.getIntParameter("pageNum"); // to get page num for sub list of routes.
        List<Route> routesLimit = getSubList(routes, limit - 1); // to sub list route

        RouteListVM routeListVM = new RouteListVM(routesLimit); // convert entity to model
        // convert this list to json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(routeListVM);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter out = context.getWriter();
        out.write(json);

        return Config.AJAX_FORMAT;

    }

    public List<Route> getSubList(List<Route> routes, int index) {

        int y = index + 9;
        if (y > routes.size()) {
            y = routes.size();
        }

        List<Route> subRoute = routes.subList(index, y);
        return subRoute;

    }
}
