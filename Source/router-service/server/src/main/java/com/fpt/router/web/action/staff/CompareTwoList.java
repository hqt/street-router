package com.fpt.router.web.action.staff;

import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.model.entity.CityMap;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.crawler.work.ReadExcelFileFromLocal;
import com.fpt.router.crawler.work.ReadJsonFromLocal;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by datnt on 10/31/2015.
 */
public class CompareTwoList {

    public static CityMap mapServer;

    public static void main(String args[]) {

        // get map from server
        ReadJsonFromLocal readJsonFromLocal = new ReadJsonFromLocal();
        mapServer = readJsonFromLocal.run();
        ReadExcelFileFromLocal readExcelFileFromLocal = new ReadExcelFileFromLocal(mapServer);
        readExcelFileFromLocal.run();
        List<Route> routesServer = mapServer.getRoutes();

        // get routes to database
        RouteDAO routeDAO = new RouteDAO();
        List<Route> routesDB = routeDAO.findAllRouteLazy();

        // To compare two list between server and database
        HashSet<Route> hsSimilar = new HashSet<>(routesServer);
        hsSimilar.retainAll(routesDB);

        HashSet<Route> hsDifference = new HashSet<>();
        hsDifference.addAll(routesServer);
        hsDifference.addAll(routesDB);
        hsDifference.removeAll(hsSimilar);

        for (Route r : hsSimilar) {
            System.out.println("Id " + r.getRouteNo());
            System.out.println("Name " + r.getRouteName());
        }

        for (Route r : hsDifference) {
            System.out.println("Id " + r.getRouteNo());
            System.out.println("Name " + r.getRouteName());
        }

        int a = 3;
    }

}
