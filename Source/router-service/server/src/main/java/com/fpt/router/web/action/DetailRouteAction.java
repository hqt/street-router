package com.fpt.router.web.action;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.PathInfoDAO;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 10/11/2015.
 */
public class DetailRouteAction implements IAction {
    @Override
    public String execute(ApplicationContext context) {

        // get parameter route id
        String paramRouteID = context.getParameter("routeId");

        int routeId = -1;
        try {
            routeId = Integer.parseInt(paramRouteID);
        } catch (NumberFormatException ex) {
            System.out.println("Cannot parse string id to int");
        }

        // process to get list station of route through pathinfo
        PathInfoDAO pathInfoDao = new PathInfoDAO();
        if (routeId != -1) {
            pathInfoDao.getListPathInfoByRouteId(routeId);
            return Config.WEB.REDIRECT + "detail"; // redirect detail route view
        }

        return ""; // stay at page
    }
}
