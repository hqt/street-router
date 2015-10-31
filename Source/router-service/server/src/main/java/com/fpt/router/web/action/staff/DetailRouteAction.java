package com.fpt.router.web.action.staff;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Station;
import com.fpt.router.artifacter.model.entity.Trip;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.staff.RouteVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 10/11/2015.
 */
public class DetailRouteAction extends StaffAction {
    @Override
    public String execute(ApplicationContext context) {

        System.out.println("In Detail Route Action");

        String idParam = context.getParameter("routeId");

        System.out.println("idParam " +idParam);

        if (idParam != null) {
            // cast string id param to int
            int routeId = -1;
            try {
                routeId = Integer.parseInt(idParam);
            } catch (NumberFormatException ex) {
                System.out.println("Cannot cast route id " + idParam + " to int");
                ex.printStackTrace();
            }

            List<Station> stations = new ArrayList<>();
            List<Trip> trips = new ArrayList<>();
            Route route = null;
            if (routeId != -1) {
                // get list station that route passed
                RouteDAO routeDAO = new RouteDAO();
                route = routeDAO.getRouteLazy(routeId);
            }

            // set request attribute
            if (route != null) {
                // convert entity to view model
                RouteVM routeVM = new RouteVM(route);
                context.setAttribute("routeVM", routeVM);
                context.setAttribute("focusId", "ÐTC B");
            }
        }

        return Config.WEB.PAGE + "/route/indexDetail.jsp";
    }
}
