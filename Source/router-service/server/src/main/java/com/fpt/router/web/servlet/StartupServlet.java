package com.fpt.router.web.servlet;




import com.fpt.router.artifacter.algorithm.TwoPointAlgorithm;
import com.fpt.router.artifacter.dao.*;
import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Staff;
import com.fpt.router.artifacter.model.helper.Location;
import org.joda.time.LocalTime;

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

        Location vincom = new Location();
        vincom.latitude = 10.360637;
        vincom.longitude = 106.662486;

        Location nguyen_dinh_chieu = new Location();
        nguyen_dinh_chieu.latitude = 10.775800;
        nguyen_dinh_chieu.longitude = 106.687280;

        TwoPointAlgorithm twoPointAlgorithm = new TwoPointAlgorithm();

        String res = twoPointAlgorithm.solveAndReturnJSon(map,
                vincom, nguyen_dinh_chieu,
                "Start Location", "End Location",
                new LocalTime(8, 30, 0), 900, 2, false, TwoPointAlgorithm.SearchType.TWO_POINT);
        System.out.println(res);


        /*Route route = new Route();
        route.setRouteId(5);
        TripDAO tripDAO = new TripDAO();
        tripDAO.getTripsByRoute(route);
        int a = 3;*/

        //JPADaoImpl.closeFactory();
        System.out.println("fuck");
        System.out.println("----------");

    }

}
