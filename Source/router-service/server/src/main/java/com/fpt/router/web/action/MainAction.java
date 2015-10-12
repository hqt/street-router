package com.fpt.router.web.action;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.web.action.common.AuthAction;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.RouteListVM;
import com.fpt.router.web.viewmodel.RouteVM;

import java.io.PrintWriter;
import java.util.List;

/**
 * Created by datnt on 10/11/2015.
 */
public class MainAction extends AuthAction {
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
