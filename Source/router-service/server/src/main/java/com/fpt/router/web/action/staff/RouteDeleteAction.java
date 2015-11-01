package com.fpt.router.web.action.staff;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by ngoan on 10/31/2015.
 */
public class RouteDeleteAction implements IAction {
    @Override
    public String execute(ApplicationContext context) {
        String id = context.getParameter("idRoute");

        RouteDAO dao = new RouteDAO();
        Route route = new Route();
        dao.delete(route);

        return Config.WEB.PAGE + "/route/acbd.jsp" ;
    }
}
