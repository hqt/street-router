package com.fpt.router.web.action.common;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.action.admin.AddStaffAction;
import com.fpt.router.web.action.admin.StaffDeleteAction;
import com.fpt.router.web.action.admin.StaffListAction;
import com.fpt.router.web.action.admin.StaffUpdateAction;
import com.fpt.router.web.action.api.MultiPointAction;
import com.fpt.router.web.action.api.TwoPointRouteAction;
import com.fpt.router.web.action.notification.StationNof.*;
import com.fpt.router.web.action.notification.trip.*;
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

        if (url.equals(URL.COMMON.LOGIN)) {
            context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/login.jsp");
            action = new DirectPageAction();
        } else if (url.equals(URL.API.TWO_POINT)) {
            action = new TwoPointRouteAction();
        } else if (url.equals(URL.API.MUlTI_POINT)) {
            action = new MultiPointAction();

        } else if (url.equals(URL.ADMIN.STAFF_ADD)) {
            action = new AddStaffAction();
        } else if (url.equals(URL.ADMIN.STAFF_LIST)) {
            action = new StaffListAction();
        } else if (url.equals(URL.ADMIN.STAFF_UPDATE)) {
            action = new StaffUpdateAction();
        } else if (url.equals(URL.ADMIN.STAFF_DELETE)) {
            action = new StaffDeleteAction();

        } else if (url.equals(URL.COMMON.ROUTE_LIST)) {
            action = new RouteListAction();
        } else if (url.equals(URL.COMMON.ROUTE_DETAIL)) {
            action = new DetailRouteAction();

        } else if (url.equals(URL.STAFF.ROUTE_ADD)) {
            context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/route/indexAdd.jsp");
            action = new DirectPageAction();
        } else if (url.equals(URL.STAFF.ROUTE_UPDATE)) {
            action = new RouteUpdateAction();
        } else if (url.equals(URL.STAFF.ROUTE_DELETE)) {
            action = new RouteDeleteAction();
        } else if (url.equals(URL.STAFF.STATION_LIST)) {
            action = new StationListAction();
        } else if (url.equals(URL.STAFF.STATION_ADD)) {
            action = new StationAddAction();
        } else if (url.equals(URL.STAFF.STATION_UPDATE)) {
            action = new StationUpdateAction();
        } else if (url.equals(URL.STAFF.TRIP_ADD)) {
            action = new TripAddAction();
        } else if (url.equals(URL.STAFF.TRIP_UPDATE)) {
            action = new TripUpdateAction();
        } else if (url.equals(URL.STAFF.TRIP_DELETE)) {
            action = new TripDeleteAction();
        } else if (url.equals(URL.STAFF.COMPARE)) {
            action = new CompareMapAction();
        } else if (url.equals(URL.STAFF.NOF_STATION_LIST)) {
            action = new StationNofListAction();
        } else if (url.equals(URL.STAFF.NOF_STATION_BLOCK)) {
            action = new StationNofBlockAction();
        } else if (url.equals(URL.STAFF.NOF_STATION_UNBLOCK)) {
            action = new StationNofUnBlockAction();
        } else if (url.equals(URL.STAFF.NOF_STATION_DELETE)) {
            action = new StationNofDeleteAction();
        } else if (url.equals(URL.STAFF.NOF_STATION_APPROVE)) {
            action = new StationNofApproveAction();
        } else if (url.equals(URL.STAFF.NOF_TRIP_LIST)) {
            action = new TripNofListAction();
        } else if (url.equals(URL.STAFF.NOF_TRIP_APPROVE)) {
            action = new TripNofApproveAction();
        } else if (url.equals(URL.STAFF.NOF_TRIP_BLOCK)) {
            action = new TripNofBlockAction();
        } else if (url.equals(URL.STAFF.NOF_TRIP_UNBLOCK)) {
            action = new TripNofUnBlockAction();
        } else if (url.equals(URL.STAFF.NOF_TRIP_DELETE)) {
            action = new TripNofDeleteAction();
        } else if (url.equals(URL.STAFF.CONFIGURE)) {
            context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/configure/index.jsp");
            action = new DirectStaffPageAction();
        } else if (url.equals(URL.STAFF.CONFIGURE_SOURCE)) {
            context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/configure/indexSource.jsp");
            action = new DirectStaffPageAction();
        } else if (url.equals(URL.STAFF.ROUTE_PARSE)) {
            context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/route/parse.jsp");
            action = new DirectStaffPageAction();
        } else if (url.equals(URL.STAFF.PARSE_SOURCE)) {
            action = new ParseSourceLocalAction();

        }


        // handle "action" parameter that end with jsp. will go directly to jsp page
        /**
         * if (parameter.length() >= 4) { String lastStr =
         * parameter.substring(parameter.length() - 4, parameter.length()); if
         * (lastStr.equals(".jsp")) { return new JSPPageAction(); } }
         */

        System.out.println("Action Command: " +actionCommand);

        if (actionCommand != null) {
            if (actionCommand.equals("login")) {
                action = new LoginAction();
            } else if (actionCommand.equals("paging")) {
                action = new RouteListAJAXAction();
            } else if (actionCommand.equals("detail")) {
                action = new DetailRouteAction();
            } else if (actionCommand.equals("update")) {
                action = new RouteUpdateAction();
            } else if (actionCommand.equals("addStation")) {
                context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/station/add.jsp");
                action = new DetailRouteAction();
            } else if (actionCommand.equals("configure")) {
                action = new ConfigTimeAction();
            } else if (actionCommand.equals("Upload")) {
                action = new ParseFileAction();
            } else if (actionCommand.equals("addRoute")) {
                action = new RouteAddAction();
            } else if (actionCommand.equals("parse")) {
                action = new ParseSourceLocalAction();
            }
        }

        return action;
    }
}
