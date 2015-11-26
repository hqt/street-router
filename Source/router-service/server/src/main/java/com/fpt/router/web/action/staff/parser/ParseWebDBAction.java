package com.fpt.router.web.action.staff.parser;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.PathInfoDAO;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.dao.StationDAO;
import com.fpt.router.artifacter.dao.TripDAO;
import com.fpt.router.artifacter.model.entity.*;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.common.URL;
import com.fpt.router.web.action.notification.StationNof.StationNofAddThread;
import com.fpt.router.web.action.notification.route.RouteNofAddThread;
import com.fpt.router.web.action.notification.trip.TripNofAddThread;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.action.staff.comparer.CompareRoute;
import com.fpt.router.web.action.staff.comparer.CompareStation;
import com.fpt.router.web.config.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 11/24/2015.
 */
public class ParseWebDBAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {

        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        System.out.println("Parsing data from web ......");

        CityMap map = new CityMap();
        ParseJsonWeb parseJsonWeb = new ParseJsonWeb(map);
        parseJsonWeb.run();
        ParseExcelWeb parseExcelWeb = new ParseExcelWeb(map);
        parseExcelWeb.run();

        // read database
        StationDAO stationDAO = new StationDAO();
        List<Station> stationsDB = stationDAO.findAll();
        List<Route> routesDB = buildRouteFull();

        // compare data
        CompareRoute compareRoute = new CompareRoute(routesDB, map.getRoutes());
        compareRoute.run();
        CompareStation compareStation = new CompareStation(stationsDB, map.getStations());
        compareStation.run();

        List<TripNotification> listTripNof = compareRoute.listTripNof;
        List<RouteNotification> listRouteNof = compareRoute.listRouteNof;
        List<StationNotification> listStationNof = compareStation.listStationNof;

        // insert various into storage
        StationNofAddThread stationNofAddThread = new StationNofAddThread(listStationNof);
        stationNofAddThread.run();
        RouteNofAddThread routeNofAddThread = new RouteNofAddThread(listRouteNof);
        routeNofAddThread.run();
        TripNofAddThread tripNofAddThread = new TripNofAddThread(listTripNof);
        tripNofAddThread.run();

        System.out.println("Done parse data from web");
        return Config.WEB.REDIRECT + URL.STAFF.NOF_STATION_LIST;
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
