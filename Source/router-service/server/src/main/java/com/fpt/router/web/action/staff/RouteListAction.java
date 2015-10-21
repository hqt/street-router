package com.fpt.router.web.action.staff;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.staff.RouteListVM;

import java.util.List;

/**
 * Created by datnt on 10/11/2015.
 */
public class RouteListAction extends StaffAction {



    @Override
    public String execute(ApplicationContext context) {

        /*String command = super.execute(context);
        if (command != null) return command;

        RouteDAO routeDao = new RouteDAO();
        List<Route> routes = routeDao.findAll();

        if (!routes.isEmpty()) {
            context.setSessionAttribute("routes", routes);
            context.setAttribute("subRoute", routes.subList(0,9));
        }*/

       return Config.WEB.PAGE + "/route/index.jsp";
    }
}
