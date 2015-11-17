package com.fpt.router.web.action.staff.route;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.web.action.common.AuthAction;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.staff.RouteListVM;

import java.util.List;

/**
 * Created by datnt on 10/11/2015.
 */
public class RouteListAction extends AuthAction {

    @Override
    public String execute(ApplicationContext context) {
        String authenticated = super.execute(context);
        if (authenticated == null) {
            return Config.WEB.REDIRECT + "/login";
        }

        RouteDAO routeDao = new RouteDAO();
        List<Route> routes = routeDao.findAll();
        // convert to model
        RouteListVM routeListVM = new RouteListVM();
        if (!routes.isEmpty()) {
            routeListVM.convertEntityToModel(routes);
        }
        context.setSessionAttribute("routes", routeListVM.getRouteListVMs());
        return PAGE.ROUTE.LIST;
    }
}
