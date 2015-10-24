package com.fpt.router.artifacter.algorithm;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.artifacter.model.viewmodel.Journey;
import com.fpt.router.artifacter.utils.NoResultHelper;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/1/15.
 */
public class MultiPointOptAlgorithm {


    public List<Journey> run(CityMap map, Location start, String startAddress,
                             List<Location> middleLocations, List<String> middleAddresses,
                      LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK) {


        List<Journey> res = new ArrayList<Journey>();

        String message = null;

        // four point optimize algorithm
        for (int i = 0; i < middleLocations.size(); i++) {
            MultiPointAlgorithm algorithm = new MultiPointAlgorithm();

            // get temp middle locations.
            List<Location> tmpMiddleLocations = new ArrayList<Location>(middleLocations);
            tmpMiddleLocations.remove(i);

            // get temp middle addresses
            List<String> tmpMiddleAddresses = new ArrayList<String>(middleAddresses);
            tmpMiddleAddresses.remove(i);

            Location end = middleLocations.get(i);
            String endAddress = middleAddresses.get(i);

            List<Journey> journeys = algorithm.run(map, start, end, startAddress, endAddress, tmpMiddleLocations, tmpMiddleAddresses,
                    departureTime, walkingDistance, K, isOptimizeK);

            if (!journeys.get(0).code.equals(Config.CODE.SUCCESS)) {
                System.out.println(journeys.get(0).code);
                message = journeys.get(0).code;
            } else {
                res.addAll(journeys);
            }
        }

        if (res.size() == 0) {
           return NoResultHelper.NoJourneyFound(message);
        }



        Collections.sort(res, new Comparator<Journey>() {
            @Override
            public int compare(Journey j1, Journey j2) {
                if (j1.minutes != j2.minutes) {
                    return j1.minutes - j2.minutes;
                }
                return (int) (j1.totalDistance - j2.totalDistance);
            }
        });

        if (res.size() > Config.BUS_RESULT_LIMIT) {
            return res.subList(0, Config.BUS_RESULT_LIMIT);
        }

        return res;
    }
}
