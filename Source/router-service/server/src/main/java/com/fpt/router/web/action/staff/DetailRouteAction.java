package com.fpt.router.web.action.staff;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.PathInfoDAO;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.staff.RouteListVM;
import com.fpt.router.web.viewmodel.staff.StationListVM;

/**
 * Created by datnt on 10/11/2015.
 */
public class DetailRouteAction extends StaffAction {
    @Override
    public String execute(ApplicationContext context) {

        // Check session out. if session out, redirect staff to login view else continue...


        // get parameter route no
        String paramRouteNo = context.getParameter("routeNo");

        int routeNo = -1;
        try {
            routeNo = Integer.parseInt(paramRouteNo);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        context.setSessionAttribute("routeNo", routeNo);

        return Config.WEB.REDIRECT + "/detail";
    }
}
