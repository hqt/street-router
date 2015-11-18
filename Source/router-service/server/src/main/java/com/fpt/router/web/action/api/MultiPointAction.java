package com.fpt.router.web.action.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fpt.router.artifacter.algorithm.MultiPointAlgorithm;
import com.fpt.router.artifacter.algorithm.MultiPointOptAlgorithm;
import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.StationDAO;
import com.fpt.router.artifacter.model.entity.Station;
import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.artifacter.model.viewmodel.Journey;
import com.fpt.router.artifacter.model.viewmodel.Result;
import com.fpt.router.artifacter.utils.JSONUtils;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.servlet.StartupServlet;
import com.fpt.router.web.viewmodel.api.JourneyVM;
import com.fpt.router.web.viewmodel.api.ListJourneyVM;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.joda.time.LocalTime;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 10/22/2015.
 */
public class MultiPointAction implements IAction {

    StationDAO dao = new StationDAO();


    @Override
    public String execute(ApplicationContext context) {

        // get parameter from client

        // Begin - get lat long of all location
        String latStartParam = context.getParameter("latStart");
        String longStartParam = context.getParameter("longStart");
        String latEndParam = context.getParameter("latEnd");
        String longEndParam = context.getParameter("longEnd");
        String latMidFirstParam = context.getParameter("latMidFirst");
        String longMidFirstParam = context.getParameter("longMidFirst");
        String latMidSecondParam = context.getParameter("latMidSecond");
        String longMidSecondParam = context.getParameter("longMidSecond");
        // End - get lat long of all locations

        String isOpParam = context.getParameter("isOp");
        boolean isOp = false;
        if (isOpParam.equals("true")) {
            isOp = true;
        }

        // option parameter
        int walkingDistance = context.getIntParameter("walkingDistance");
        int transferTurn = context.getIntParameter("transferTurn");


        // start
        String addressStart = context.getParameter("addressStart");
        // end
        String addressEnd = context.getParameter("addressEnd");
        // middle First
        String addressMidFirst = context.getParameter("addressMidFirst");
        System.out.println("middle first: " + addressMidFirst);
        // middle Second
        String addressMidSecond = context.getParameter("addressMidSecond");
        System.out.println("middle second: " +addressMidSecond);

        List<String> middleAddresses = new ArrayList<String>();
        if (addressMidFirst != null) {
            middleAddresses.add(addressMidFirst);
        }
        if (addressMidSecond != null) {
            middleAddresses.add(addressMidSecond);
        }

        String hourStr = context.getParameter("hour");
        String minuteStr = context.getParameter("minute");
        int hour = Integer.parseInt(hourStr);
        int minute = Integer.parseInt(minuteStr);
        LocalTime departureTime = new LocalTime(hour, minute, 0);


        // parse string param to double
        double latStart = 0, longStart = 0, latEnd = 0, longEnd = 0
                , latMidFirst = 0, longMidFirst = 0, latMidSecond = 0, longMidSecond = 0;

        try {
            latStart = Double.parseDouble(latStartParam);
            longStart = Double.parseDouble(longStartParam);
            latEnd = Double.parseDouble(latEndParam);
            longEnd = Double.parseDouble(longEndParam);
            if (latMidFirstParam != null && longMidFirstParam != null) {
                latMidFirst = Double.parseDouble(latMidFirstParam);
                longMidFirst = Double.parseDouble(longMidFirstParam);
            }
            if (latMidSecondParam != null && longMidSecondParam != null) {
                latMidSecond = Double.parseDouble(latMidSecondParam);
                longMidSecond = Double.parseDouble(longMidSecondParam);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        // Begin - Build Start Location
        Location start = new Location();
        start.latitude = latStart;
        start.longitude = longStart;

        Location end = new Location();
        end.latitude = latEnd;
        end.longitude = longEnd;
        // End - Build Start Location
        
        // Begin - Build Middle Locations
        Location first = new Location();
        first.latitude = latMidFirst;
        first.longitude = longMidFirst;
        
        Location second = new Location();
        second.latitude = latMidSecond;
        second.longitude = longMidSecond;

        List<Location> middleLocations = new ArrayList<Location>();

        if (addressMidFirst != null) {
            middleLocations.add(first);
        }
        if (addressMidSecond != null) {
            middleLocations.add(second);
        }

        // End - Build Middle Locations

        System.out.println("first: " + start.longitude + "\t" + start.latitude);
        System.out.println("second: " + first.longitude + "\t" + first.latitude);
        System.out.println("third: " + second.longitude + "\t" + second.latitude);
        System.out.println("fourth: " + end.longitude + "\t" + end.latitude);

        List<Journey> journeys;

        long startTime = System.currentTimeMillis();

        MultiPointAlgorithm multiPointAlgorithm = new MultiPointAlgorithm();
        MultiPointOptAlgorithm multiPointOptAlgorithm = new MultiPointOptAlgorithm();
        if (isOp) {
            middleAddresses.add(addressEnd);
            middleLocations.add(end);
            System.out.println("Cal multi with optimize");
            System.out.println("Waking Distance: " + Config.WALKING_DISTANCE);
            System.out.println("K: " + transferTurn);
            journeys = multiPointOptAlgorithm.run(StartupServlet.map, start, addressStart, middleLocations, middleAddresses,
                    departureTime, walkingDistance, transferTurn, isOp);
        } else {
            journeys = multiPointAlgorithm.run(StartupServlet.map, start, end, addressStart, addressEnd,
                    middleLocations, middleAddresses, departureTime, walkingDistance, transferTurn, isOp);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Total time: " + (endTime - startTime) / 1000);

        Gson gson = JSONUtils.buildGson();

        String json = gson.toJson(journeys);
        PrintWriter out = context.getWriter();
        out.write(json);
        return Config.AJAX_FORMAT;
    }
}
