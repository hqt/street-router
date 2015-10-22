package com.fpt.router.web.action.staff;

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
    MultiPointAlgorithm multiPointAlgorithm = new MultiPointAlgorithm();
    MultiPointOptAlgorithm multiPointOptAlgorithm = new MultiPointOptAlgorithm();

    @Override
    public String execute(ApplicationContext context) {

        // get parameter from client

        // Begin - get lat long of all location
        String latStartParam = context.getParameter("latA");
        String longStartParam = context.getParameter("longA");
        String latEndParam = context.getParameter("latB");
        String longEndParam = context.getParameter("longB");
        String latMidFirstParam = context.getParameter("latC");
        String longMidFirstParam = context.getParameter("longC");
        String latMidSecondParam = context.getParameter("latD");
        String longMidSecondParam = context.getParameter("longD");
        // End - get lat long of all locations

        String isOpParam = context.getParameter("isOp");
        boolean isOp = Boolean.parseBoolean(isOpParam);

        String addressA = context.getParameter("addressA");
        String addressB = context.getParameter("addressB");
        String addressC = context.getParameter("addressC");
        String addressD = context.getParameter("addressD");
        List<String> middleAddess = new ArrayList<>();
        middleAddess.add(addressC);
        middleAddess.add(addressD);

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
            latMidFirst = Double.parseDouble(latMidFirstParam);
            longMidFirst = Double.parseDouble(longMidFirstParam);
            latMidSecond = Double.parseDouble(latMidSecondParam);
            longMidSecond = Double.parseDouble(longMidSecondParam);
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

        List<Location> middleLocations = new ArrayList<>();
        middleLocations.add(first);
        middleLocations.add(second);
        // End - Build Middle Locations

        System.out.println("first: " + start.longitude + "\t" + start.latitude);
        System.out.println("second: " + first.longitude + "\t" + first.latitude);
        System.out.println("third: " + second.longitude + "\t" + second.latitude);
        System.out.println("fourth: " + end.longitude + "\t" + end.latitude);

        int K = 2;

        List<Journey> journeys;
        if (isOp) {
            journeys = multiPointOptAlgorithm.run(StartupServlet.map, start, end, addressA, addressB, middleLocations, middleAddess, departureTime, Config.WALKING_DISTANCE, K, isOp);
        } else {
            journeys = multiPointAlgorithm.run(StartupServlet.map, start, end, addressA, addressB, middleLocations, middleAddess, departureTime, Config.WALKING_DISTANCE, K, isOp);
        }

        Gson gson = JSONUtils.buildGson();

        String json = gson.toJson(journeys);
        PrintWriter out = context.getWriter();
        System.out.println(json);
        out.write(json);
        return Config.AJAX_FORMAT;
    }
}
