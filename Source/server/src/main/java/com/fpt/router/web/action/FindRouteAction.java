/*
package com.fpt.router.web.action;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.web.config.ApplicationContext;

*/
/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/30/15.
 *//*

public class FindRouteAction implements IAction {

    @Override
    public String execute(ApplicationContext context) {
        // get all parameters
        String latAStr = context.getParameter("latA");
        String latBStr = context.getParameter("latB");
        String longAStr = context.getParameter("longA");
        String longBStr = context.getParameter("longB");
        String addressA = context.getParameter("addressA");
        String addressB = context.getParameter("addressB");
        String findSelectMethod = context.getParameter("method");

        double latA, latB, longA, longB;
        latA = Double.parseDouble(latAStr);
        latB = Double.parseDouble(latBStr);
        longA = Double.parseDouble(longAStr);
        longB = Double.parseDouble(longBStr);

        System.out.println("latA: " + latA + "  " + "longA: " + longA);
        System.out.println("latB: " + latB + "  " + "longB: " + longB);
        System.out.println("address a: " + addressA);
        System.out.println("address b: " + addressB);
        System.out.println("find method: " + findSelectMethod);


        Location start = new Location();
        start.latitude = latA;
        start.longitude = longA;
        Location end = new Location();
        end.latitude = latB;
        end.longitude = longB;

        */
/*GraphPipe graph = new GraphPipe(ApplicationContext.map);
        String url = context.getRealPath() + "/WEB-INF/result_schema.xsd";
        String xml = graph.run(start, end, addressA, addressB, comparer, url);

        System.out.println("algorithm finish");
        PrintWriter out = context.getWriter();

        out.write(xml);*//*


        //return Config.AJAX_FORMAT;
        return "index.jsp";

       */
/* DatabasePipe database = new DatabasePipe();
        CityMap map = database.parse();

        Location start = new Location();
        start.latitude = 10.771918;
        start.longitude = 106.698347;
        Location end = new Location();
        end.latitude = 10.853132;
        end.longitude = 106.626289;

        IBusComparer comparer = new DistanceComparer();
        GraphPipe graph = new GraphPipe(map);
        String xml = graph.run(start, end, comparer);

        System.out.println("algorithm finish");
        PrintWriter out = context.getWriter();
        out.write(Config.xml);
        return Config.AJAX_FORMAT;
        *//*

    }
}*/
