package com.fpt.router.web.action.staff;

import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.model.entity.CityMap;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;

import java.util.List;

/**
 * Created by datnt on 10/31/2015.
 */
public class CompareMapAction implements IAction {

    private CityMap mapServer;
    private CityMap mapDB;

    /*
        Compare between server and database
     */
    @Override
    public String execute(ApplicationContext context) {

        /*// get map from server
        BusCrawlerPipe busCrawler = new BusCrawlerPipe();
        ReadJsonFromLocal readJsonFromLocal = new ReadJsonFromLocal();
        mapServer = readJsonFromLocal.run();
        ReadExcelFileFromLocal readExcelFileFromLocal = new ReadExcelFileFromLocal(mapServer);
        readExcelFileFromLocal.run();

        // get map from database
        RouteDAO routeDAO = new RouteDAO();
        List<Route> routes = routeDAO.findAllRouteLazy();

        // here for processing compare two list between server and database


        System.out.println("Size of routes " + routes.size());
        int a = 3;*/
        return null;
    }
}
