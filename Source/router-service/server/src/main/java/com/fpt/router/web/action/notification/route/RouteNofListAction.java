package com.fpt.router.web.action.notification.route;

import com.fpt.router.artifacter.dao.RouteNotificationDAO;
import com.fpt.router.artifacter.model.entity.RouteNotification;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.staff.NofRouteListVM;
import com.fpt.router.web.viewmodel.staff.NofRouteVM;

import java.util.List;

/**
 * Created by datnt on 11/20/2015.
 */
public class RouteNofListAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {

        System.out.println("Route List Action...");

        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        // get list entity
        RouteNotificationDAO dao = new RouteNotificationDAO();
        List<RouteNotification> entities =  dao.findAll();

        NofRouteListVM nofsActive = new NofRouteListVM();
        NofRouteListVM nofsInActive = new NofRouteListVM();
        if (entities != null && !entities.isEmpty()) {
            for (RouteNotification entity : entities) {
                if (entity.getState() == 0) {
                    NofRouteVM nofRouteVM = new NofRouteVM(entity);
                    nofRouteVM.buildNotification();
                    nofsActive.nofRouteVMs.add(nofRouteVM);
                } else {
                    NofRouteVM nofRouteVM = new NofRouteVM(entity);
                    nofRouteVM.buildNotification();
                    nofsInActive.nofRouteVMs.add(nofRouteVM);
                }
            }
            context.setAttribute("routeNofListActive", nofsActive);
            context.setAttribute("routeNofListInActive", nofsInActive);
        }

        return PAGE.NOTIFICATION.ROUTE_LIST;
    }

}
