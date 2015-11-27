package com.fpt.router.web.action.staff.parser;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.PathInfoDAO;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.dao.StationDAO;
import com.fpt.router.artifacter.dao.TripDAO;
import com.fpt.router.artifacter.model.entity.*;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.notification.StationNof.StationNofAddThread;
import com.fpt.router.web.action.notification.route.RouteNofAddThread;
import com.fpt.router.web.action.notification.trip.TripNofAddThread;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.action.staff.comparer.CompareRoute;
import com.fpt.router.web.action.staff.comparer.CompareStation;
import com.fpt.router.web.config.ApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 11/10/2015.
 */
public class ParseSourceLocalAction extends StaffAction {

    public static String message = "";

    @Override
    public String execute(ApplicationContext context) {
        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        System.out.println("We here for parse source and compare data in order to create notification");

        // get path folder
        String pathJsonFolder = context.getParameter("jsonPathFolder");
        String pathExcelFolder = context.getParameter("excelPathFolder");

        File jsonFolder = new File(pathJsonFolder);
        File excelFolder = new File(pathExcelFolder);

        if ((jsonFolder.exists() && jsonFolder.isDirectory() || (excelFolder.exists() && excelFolder.isDirectory()))) {

            ParseJsonLocal parseJsonLocal = new ParseJsonLocal(jsonFolder);
            CityMap mapSource = parseJsonLocal.run();

            ParseExcelLocal parseExcelLocal = new ParseExcelLocal(mapSource, excelFolder);
            mapSource = parseExcelLocal.run();

            // compare station source and database
            StationDAO stationDAO = new StationDAO();
            List<Station> stationsDB = stationDAO.findAll();
            CompareStation compareStation = new CompareStation(stationsDB, mapSource.getStations());
            compareStation.run();

            List<Route> routesDB = buildRouteFull();
            // compare route between route from source and route from database.
            CompareRoute compareRoute = new CompareRoute(routesDB, mapSource.getRoutes());
            compareRoute.run();

            // retrieve notification.
            List<RouteNotification> routeVarious = compareRoute.listRouteNof;
            List<TripNotification> tripVarious = compareRoute.listTripNof;
            List<StationNotification> stationVarious = compareStation.listStationNof;

            // Thread insert notification into database.
            RouteNofAddThread routeNofAddThread = new RouteNofAddThread(routeVarious);
            routeNofAddThread.run();
            TripNofAddThread tripNofAddThread = new TripNofAddThread(tripVarious);
            tripNofAddThread.run();
            StationNofAddThread stationAddThread = new StationNofAddThread(stationVarious);
            stationAddThread.run();


        } else {
            message = "Path Json or Excel is not available";
            context.setAttribute("msgPathFolder", message);
            return Config.AJAX_FORMAT;
        }

        return Config.WEB.REDIRECT + "/route/list";
    }

    public static List<Route> buildRouteFull() {

        List<Route> result = new ArrayList<Route>();

        RouteDAO routeDAO = new RouteDAO();
        TripDAO tripDAO = new TripDAO();
        PathInfoDAO pathInfoDAO = new PathInfoDAO();

        List<Route> routes = routeDAO.findAll();

        if (routes != null && !routes.isEmpty()) {
            result.addAll(routes);
            for (int i = 0; i < result.size(); i++) {
                List<Trip> trips = tripDAO.getTripsByRoute(result.get(i));
                result.get(i).setTrips(trips);
                List<PathInfo> pathInfos = pathInfoDAO.getPathInfosByRoute(result.get(i));
                result.get(i).setPathInfos(pathInfos);
            }
        }

        return result;
    }
}
