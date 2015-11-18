package com.fpt.router.web.action.common;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.action.admin.AddStaffAction;
import com.fpt.router.web.action.admin.StaffDeleteAction;
import com.fpt.router.web.action.admin.StaffListAction;
import com.fpt.router.web.action.admin.StaffUpdateAction;
import com.fpt.router.web.action.api.MultiPointAction;
import com.fpt.router.web.action.api.TwoPointRouteAction;
import com.fpt.router.web.action.notification.NofApproveAction;
import com.fpt.router.web.action.notification.NofBlockAction;
import com.fpt.router.web.action.notification.NofDeleteAction;
import com.fpt.router.web.action.notification.NofUnblockAction;
import com.fpt.router.web.action.notification.StationNof.StationNofListAction;
import com.fpt.router.web.action.staff.CompareMapAction;
import com.fpt.router.web.action.staff.DetailRouteAction;
import com.fpt.router.web.action.staff.DirectStaffPageAction;
import com.fpt.router.web.action.staff.configuration.ConfigTimeAction;
import com.fpt.router.web.action.staff.parser.ParseFileAction;
import com.fpt.router.web.action.staff.parser.ParseSourceLocalAction;
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
            case URL.COMMON.LOGIN:
                context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/login.jsp");
                action = new DirectPageAction();
                break;
            case URL.API.TWO_POINT:
                action = new TwoPointRouteAction();
                break;
            case URL.API.MUlTI_POINT:
                action = new MultiPointAction();
                break;
            case URL.ADMIN.STAFF_ADD:
                action = new AddStaffAction();
                break;
            case URL.ADMIN.STAFF_LIST:
                action = new StaffListAction();
                break;
            case URL.ADMIN.STAFF_UPDATE:
                action = new StaffUpdateAction();
                break;
            case URL.ADMIN.STAFF_DELETE:
                action = new StaffDeleteAction();
                break;
            case URL.COMMON.ROUTE_LIST:
                action = new RouteListAction();
                break;
            case URL.COMMON.ROUTE_DETAIL:
                action = new DetailRouteAction();
                break;
            case URL.STAFF.ROUTE_ADD:
                context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/route/indexAdd.jsp");
                action = new DirectPageAction();
                break;
            case URL.STAFF.ROUTE_UPDATE:
                action = new RouteUpdateAction();
                break;
            case URL.STAFF.ROUTE_DELETE:
                action = new RouteDeleteAction();
                break;
            case URL.STAFF.STATION_LIST:
                action = new StationListAction();
                break;
            case URL.STAFF.STATION_ADD:
                action = new StationAddAction();
                break;
            case URL.STAFF.STATION_UPDATE:
                action = new StationUpdateAction();
                break;
            case URL.STAFF.TRIP_ADD:
                action = new TripAddAction();
                break;
            case URL.STAFF.TRIP_UPDATE:
                action = new TripUpdateAction();
                break;
            case URL.STAFF.TRIP_DELETE:
                action = new TripDeleteAction();
                break;
            case URL.STAFF.COMPARE:
                action = new CompareMapAction();
                break;
            case URL.STAFF.NOF_STATION_LIST:
                action = new StationNofListAction();
                break;
            case URL.STAFF.NOF_BLOCK:
                action = new NofBlockAction();
                break;
            case URL.STAFF.NOF_UNBLOCK:
                action = new NofUnblockAction();
                break;
            case URL.STAFF.NOF_DELETE:
                action = new NofDeleteAction();
                break;
            case URL.STAFF.NOF_APPROVE:
                action = new NofApproveAction();
                break;
            case URL.STAFF.CONFIGURE:
                context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/configure/index.jsp");
                action = new DirectStaffPageAction();
                break;
            case URL.STAFF.CONFIGURE_SOURCE:
                context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/configure/indexSource.jsp");
                action = new DirectStaffPageAction();
                break;
            case URL.STAFF.ROUTE_PARSE:
                context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/route/parse.jsp");
                action = new DirectStaffPageAction();
                break;
            case URL.STAFF.PARSE_SOURCE:
                action = new ParseSourceLocalAction();
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
                    break;
                case "Upload":
                    action = new ParseFileAction();
                    break;
                case "addRoute":
                    action = new RouteAddAction();
                    break;
                case "parse":
                    action = new ParseSourceLocalAction();
                    break;
            }
        }

        return action;
    }
}
