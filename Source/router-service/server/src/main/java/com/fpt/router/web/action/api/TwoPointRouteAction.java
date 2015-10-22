package com.fpt.router.web.action.api;

import com.fpt.router.artifacter.algorithm.TwoPointAlgorithm;
import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.config.DummyResult;
import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.servlet.StartupServlet;
import org.joda.time.LocalTime;

import java.io.PrintWriter;

/*
*
 * Purpose:
 * Created by Huynh Quang Thao on 9/30/15.
*/

public class TwoPointRouteAction implements IAction {

    @Override
    public String execute(ApplicationContext context) {

        // get all parameters
        String latAStr = context.getParameter("latA");
        String latBStr = context.getParameter("latB");
        String longAStr = context.getParameter("longA");
        String longBStr = context.getParameter("longB");

        String addressA = context.getParameter("addressA");
        String addressB = context.getParameter("addressB");

        String hourStr = context.getParameter("hour");
        String minuteStr = context.getParameter("minute");

        int hour = Integer.parseInt(hourStr);
        int minute = Integer.parseInt(minuteStr);

        LocalTime departureTime = new LocalTime(hour, minute, 0);

        double walkingDistance = Config.WALKING_DISTANCE;

        int K = 2;

        double latA, latB, longA, longB;
        latA = Double.parseDouble(latAStr);
        latB = Double.parseDouble(latBStr);
        longA = Double.parseDouble(longAStr);
        longB = Double.parseDouble(longBStr);

        System.out.println("latA: " + latA + "  " + "longA: " + longA);
        System.out.println("latB: " + latB + "  " + "longB: " + longB);
        System.out.println("address a: " + addressA);
        System.out.println("address b: " + addressB);
        System.out.println("Time: " + hourStr + ":" + minuteStr);

        Location start = new Location();
        start.latitude = latA;
        start.longitude = longA;

        Location end = new Location();
        end.latitude = latB;
        end.longitude = longB;

         TwoPointAlgorithm algorithm = new TwoPointAlgorithm();
          String json = algorithm.solveAndReturnJSon(StartupServlet.map, start, end, addressA, addressB, departureTime, walkingDistance, K, false);

        System.out.println("algorithm finish zzzzzz");
        PrintWriter out = context.getWriter();
        //System.out.println(DummyResult.twoPointJSonStr);
        if (json.length() < 100) System.out.println(json);
        out.write(json);


        return Config.AJAX_FORMAT;


    }
}
