package com.fpt.router.web.servlet;




import com.fpt.router.artifacter.dao.*;
import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Staff;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/27/15.
 */
public class StartupServlet extends HttpServlet {

    public static CityMap map;

    public void init() throws ServletException {
        System.out.println("----------");
        System.out.println("Loading Database zzzz");
        JPADaoImpl.enableStaticEntityManager();
        map = MapDAL.readDatabase();
        System.out.println("station size: " + map.stations.size());
        JPADaoImpl.disableStaticEntityManager();

        /*Route route = new Route();
        route.setRouteId(5);
        TripDAO tripDAO = new TripDAO();
        tripDAO.getTripsByRoute(route);
        int a = 3;*/

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.findStaffByEmail("huynhquangthao@gmail.com");
        // new StationNotificationDAO().readByCode("af");
        //Staff staff = staffDAO.findStaffByEmail("huynhquangthao@gmail.com");
        int a = 3;
        /*new StaffDAO().findStaffByEmail("a", "a");*/

        //JPADaoImpl.closeFactory();
        System.out.println("fuck");
        System.out.println("----------");

    }

}
