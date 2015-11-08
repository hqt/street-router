package com.fpt.router.web.action.common;

import com.fpt.router.artifacter.config.Config;
//import com.fpt.router.web.action.api.TwoPointRouteAction;
import com.fpt.router.web.action.admin.*;
import com.fpt.router.web.action.api.MultiPointAction;
import com.fpt.router.web.action.api.TwoPointRouteAction;
import com.fpt.router.web.action.notification.*;
import com.fpt.router.web.action.staff.*;
import com.fpt.router.web.action.staff.configuration.ConfigTimeAction;
import com.fpt.router.web.action.staff.route.*;
import com.fpt.router.web.action.staff.station.StationAddAction;
import com.fpt.router.web.action.staff.station.StationListAction;
import com.fpt.router.web.action.staff.station.StationUpdateAction;
import com.fpt.router.web.action.staff.trip.TripAddAction;
import com.fpt.router.web.action.staff.trip.TripDeleteAction;
import com.fpt.router.web.action.staff.trip.TripUpdateAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/27/15.
 */
public class ActionFactory implements IActionFactory {

    @Override
    public IAction getAction(ApplicationContext context) {
        String actionCommand = context.getParameter("action");
        IAction action = null;

        // handle link such as localhost/qna/login.jsp
        // forward to WEB-INF/login.jsp
        String url = context.getServletPath();

        switch (url) {
            case "/login":
                context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/login.jsp");
                action = new DirectPageAction();
                break;
            case "/api/twopoint":
                action = new TwoPointRouteAction();
                break;
            case "/search/multi":
                action = new MultiPointAction();
                break;
            case "/staff/add":
                action = new AddStaffAction();
                break;
            case "/staff/list":
                action = new StaffListAction();
                break;
            case "/staff/update":
                action = new StaffUpdateAction();
                break;
            case "/staff/delete":
                action = new StaffDeleteAction();
                break;
            case "/route/list":
                action = new RouteListAction();
                break;
            case "/route/detail":
                action = new DetailRouteAction();
                break;
            case "/route/add":
                action = new RouteAddAction();
                break;
            case "/route/update":
                action = new RouteUpdateAction();
                break;
            case "/route/delete":
                action = new RouteDeleteAction();
                break;
            case "/station/list":
                action = new StationListAction();
                break;
            case "/station/add":
                action = new StationAddAction();
                break;
            case "/station/update":
                action = new StationUpdateAction();
                break;
            case "/trip/add":
                action = new TripAddAction();
                break;
            case "/trip/update":
                action = new TripUpdateAction();
                break;
            case "/trip/delete":
                action = new TripDeleteAction();
                break;
            case "/compare":
                action = new CompareMapAction();
                break;
            case "/notification/list":
                action = new NofListAction();
                break;
            case "/notification/block":
                action = new NofBlockAction();
                break;
            case "/notification/unblock":
                action = new NofUnblockAction();
                break;
            case "/notification/delete":
                action = new NofDeleteAction();
                break;
            case "/notification/approve":
                action = new NofApproveAction();
                break;
            case "/configuration":
                context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/configure/index.jsp");
                action = new DirectPageAction();
                break;
        }
        // handle "action" parameter that end with jsp. will go directly to jsp page
        /**
         * if (parameter.length() >= 4) { String lastStr =
         * parameter.substring(parameter.length() - 4, parameter.length()); if
         * (lastStr.equals(".jsp")) { return new JSPPageAction(); } }
         */

        System.out.println("Action Command: " +actionCommand);

        if (actionCommand != null) {
            switch (actionCommand) {
                case "login":
                    action = new LoginAction();
                    break;
                case "paging":
                    action = new RouteListAJAXAction();
                    break;
                case "detail":
                    action = new DetailRouteAction();
                    break;
                case "update":
                    action = new RouteUpdateAction();
                    break;
                case "addStation":
                    context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/station/add.jsp");
                    action = new DetailRouteAction();
                    break;
                case "configure":
                    action = new ConfigTimeAction();
            }
        }

        return action;
    }
}
