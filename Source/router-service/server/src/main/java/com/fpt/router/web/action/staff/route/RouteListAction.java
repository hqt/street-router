package com.fpt.router.web.action.staff.route;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.staff.RouteListVM;
import com.fpt.router.web.viewmodel.staff.RouteVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 10/11/2015.
 */
public class RouteListAction extends StaffAction {



    @Override
    public String execute(ApplicationContext context) {

        RouteDAO routeDao = new RouteDAO();
        List<Route> routes = routeDao.findAll();
        // convert to model
        RouteListVM routeListVM = new RouteListVM();
        if (!routes.isEmpty()) {
            routeListVM.convertEntityToModel(routes);
        }

        context.setSessionAttribute("routes", routeListVM.getRouteListVMs());

       return Config.WEB.PAGE + "/route/index.jsp";
    }
}
