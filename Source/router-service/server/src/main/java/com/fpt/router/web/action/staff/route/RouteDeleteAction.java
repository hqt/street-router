package com.fpt.router.web.action.staff.route;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.PathInfoDAO;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.dao.TripDAO;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/2/2015.
 */
public class RouteDeleteAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {
        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        System.out.println("Deleting...");

        // get parameter
        String routeIdParam = context.getParameter("routeId");

        if (routeIdParam != null) {
            // cast string param to int
            int routeId = -1;
            try {
                routeId = Integer.parseInt(routeIdParam);
            } catch (NumberFormatException ex) {
                System.out.println("Casting Wrong");
                ex.printStackTrace();
            }

            // delete row
            if (routeId != -1) {
                System.out.println("Route Id " + routeId);
                // 1. delete connection
                /*ConnectionDAO conDAO = new ConnectionDAO();
                conDAO.deleteConnections(routeId);*/
                // 1. find route for check route exist or note
                RouteDAO routeDAO = new RouteDAO();
                Route route = routeDAO.read(routeId);
                if (route != null) {
                    // 2. delete trip
                    TripDAO tripDAO = new TripDAO();
                    tripDAO.deleteTripAndCon(route);
                    // 3. delete pathinfo
                    PathInfoDAO pathInfoDAO = new PathInfoDAO();
                    pathInfoDAO.deletePathInfoByRouteId(route);
                    // 4. delete route
                    routeDAO.delete(route);
                }
            }
        }

        return Config.AJAX_FORMAT;
    }
}
