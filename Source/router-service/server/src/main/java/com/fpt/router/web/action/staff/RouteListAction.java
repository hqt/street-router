package com.fpt.router.web.action.staff;

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

        String command = super.execute(context);
        if (command != null) return command;

        RouteDAO routeDao = new RouteDAO();
        List<Route> routes = routeDao.findAll();
        System.out.println("Size of route" +routes.size());
        RouteListVM routeListVM = new RouteListVM(routes);

        context.setAttribute("routes", routeListVM.getRouteListVMs().subList(0,10));

        /*PrintWriter out = context.getWriter();

        out.write("Th?o");

        //return Config.AJAX_FORMAT;*/

       return "WEB-INF/page/" + "index.jsp";
    }
}
