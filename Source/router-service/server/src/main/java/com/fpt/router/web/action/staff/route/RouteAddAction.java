package com.fpt.router.web.action.staff.route;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.ConnectionDAO;
import com.fpt.router.artifacter.dao.PathInfoDAO;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.dao.TripDAO;
import com.fpt.router.artifacter.model.entity.Connection;
import com.fpt.router.artifacter.model.entity.PathInfo;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Trip;
import com.fpt.router.artifacter.model.helper.RouteType;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.action.staff.parser.ParseData;
import com.fpt.router.web.config.ApplicationContext;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by datnt on 11/1/2015.
 */
public class RouteAddAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {

        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        // get parameter
        String routeNoParam = context.getParameter("routeNo");
        String routeNameParam = context.getParameter("routeName");
        String routeType = context.getParameter("routeType");

        // get file
        Part jsonFile = context.getPart("jsonFile");
        Part excelFile = context.getPart("excelFile");

        if (routeNoParam == null || routeNameParam == null || routeType == null ||
                jsonFile == null || excelFile == null) {
            System.out.println("Show message: Missing Input ");
            return Config.AJAX_FORMAT;
        }

        int routeNo = -1;
        try {
            routeNo = Integer.parseInt(routeNoParam);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        Route route = null;
        if (routeNo != -1) {
            route = buildRoute(routeNo, routeNameParam, routeType, jsonFile, excelFile);
        }

        boolean done = false;
        if (route != null) {
            done = insertRoute(route);
        }

        if (done) {
            RouteDAO routeDAO = new RouteDAO();
            Route routeDetail = routeDAO.getRoutebyRouteNo(route.getRouteNo(), RouteType.valueOf(routeType.toUpperCase()));
            if (routeDetail != null) {
                int routeId = routeDetail.getRouteId();
                return Config.WEB.REDIRECT + "/route/detail" + "?routeId=" + routeId;
            }
        }

        return PAGE.ROUTE.ADD;
    }

    public Route buildRoute(int routeNo, String routeNameParam, String routeType, Part jsonFile, Part excelFile) {

        Route route = new Route();
        if (routeNo != -1) {
            // Processing add route
            route.setRouteNo(routeNo);
            route.setRouteName(routeNameParam);
            if (routeType.trim().equals(RouteType.DEPART.name())) {
                route.setRouteType(RouteType.DEPART);
            } else {
                route.setRouteType(RouteType.RETURN);
            }

            InputStream inJson = null;
            InputStream inExcel = null;
            try {
                inJson = jsonFile.getInputStream();
                inExcel = excelFile.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ParseData parseData = new ParseData();
            if (inJson != null ) {
                parseData.readJson(inJson, route);
            }
            if (inExcel != null) {
                parseData.readExcel(inExcel, route);
            }
            parseData.buildConnections(route);

            System.out.println("Done Build Route");
        }
        return route;
    }

    public boolean insertRoute(Route route) {
        RouteDAO routeDAO = new RouteDAO();
        PathInfoDAO pathInfoDAO = new PathInfoDAO();
        TripDAO tripDAO = new TripDAO();
        ConnectionDAO connectionDAO = new ConnectionDAO();

        Route existed = routeDAO.getRoutebyRouteNo(route.getRouteNo(), route.getRouteType());

        if (existed != null) {
            return false;
        }

        routeDAO.create(route);
        for (PathInfo pathInfo : route.getPathInfos()) {
            pathInfoDAO.create(pathInfo);
        }
        for (Trip trip : route.getTrips()) {
            tripDAO.create(trip);
            for (Connection con : trip.getConnections()) {
                connectionDAO.create(con);
            }
        }

        return true;
    }
}
