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
import java.sql.Time;
import java.util.Date;

/*
*
 * Purpose:
 * Created by Huynh Quang Thao on 9/30/15.
*/

public class TwoPointRouteAction implements IAction {

    @Override
    public String execute(ApplicationContext context) {

        // get all parameters
        String latStartParam = context.getParameter("latStart");
        String longStartParam = context.getParameter("longStart");
        String latEndParam = context.getParameter("latEnd");
        String longEndParam = context.getParameter("longEnd");

        String addressStart = context.getParameter("addressStart");
        String addressEnd = context.getParameter("addressEnd");

        String hourStr = context.getParameter("hour");
        String minuteStr = context.getParameter("minute");

        // option parameter
        int walkingDistance = context.getIntParameter("walkingDistance");
        int transferTurn = context.getIntParameter("transferTurn");

        int hour = Integer.parseInt(hourStr);
        int minute = Integer.parseInt(minuteStr);

        LocalTime departureTime = new LocalTime(hour, minute, 0);

        double latA, latB, longA, longB;
        latA = Double.parseDouble(latStartParam);
        latB = Double.parseDouble(latEndParam);
        longA = Double.parseDouble(longStartParam);
        longB = Double.parseDouble(longEndParam);

        System.out.println("latA: " + latA + "  " + "longA: " + longA);
        System.out.println("latB: " + latB + "  " + "longB: " + longB);
        System.out.println("address a: " + addressStart);
        System.out.println("address b: " + addressEnd);
        System.out.println("K: " + transferTurn + "\tWalking Distance: " + walkingDistance);
        System.out.println("Time: " + hourStr + ":" + minuteStr);

        Location start = new Location();
        start.latitude = latA;
        start.longitude = longA;

        Location end = new Location();
        end.latitude = latB;
        end.longitude = longB;

        long startTime = System.currentTimeMillis();

        TwoPointAlgorithm algorithm = new TwoPointAlgorithm();
        String json = algorithm.solveAndReturnJSon(StartupServlet.map, start, end,
                addressStart, addressEnd, departureTime, walkingDistance,
                transferTurn, false, TwoPointAlgorithm.SearchType.TWO_POINT);

        long endTime = System.currentTimeMillis();

        System.out.println("Total time: " + (endTime - startTime) / 1000);

        System.out.println("algorithm finish zzzzzz");
        PrintWriter out = context.getWriter();
        out.write(json);
        //System.out.println(DummyResult.twoPointJSonStr);
        return Config.AJAX_FORMAT;
    }
}
