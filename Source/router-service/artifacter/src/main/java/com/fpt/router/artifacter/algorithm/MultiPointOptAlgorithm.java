package com.fpt.router.artifacter.algorithm;

import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.artifacter.model.viewmodel.Journey;
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

    CityMap map;
    double walkingDistance;
    Location start;
    Location end;
    TwoPointAlgorithm twoPointAlgorithm = new TwoPointAlgorithm();
    MultiPointAlgorithm multiPointAlgorithm = new MultiPointAlgorithm();

    public List<Journey> run(CityMap map, Location start, Location end, String startAddress, String endAddress,
                      List<Location> middleLocations, List<String> middleAddresses,
                      LocalTime departureTime, double walkingDistance, int K, boolean isOptimizeK) {

        this.map = map;
        this.start = start;
        this.end = end;
        this.walkingDistance = walkingDistance;

        List<Journey> journeys = new ArrayList<Journey>();
        for(int i = 0; i < 3; i ++) {
            Location swap = end;
            end = middleLocations.get(0);
            middleLocations.remove(0);
            middleLocations.add(swap);
            journeys.addAll(multiPointAlgorithm.getJourneys(map, start, end, startAddress, endAddress,
                     middleLocations, middleAddresses,
                     departureTime, walkingDistance, K, isOptimizeK));
        }

        sort(journeys);

        return limitJourney(journeys);
    }



    public void sort(List<Journey> journeys) {
        Collections.sort(journeys, new Comparator<Journey>() {
            @Override
            public int compare(Journey j1, Journey j2) {
                if (j1.minutes == j2.minutes) {
                    return (int) (j1.totalDistance - j2.totalDistance);
                }

                return j1.minutes - j2.minutes;
            }
        });
    }

    public List<Journey> limitJourney(List<Journey> journeys) {
        if (journeys.size() > 5) {
            return journeys.subList(0, 5);
        }
        return journeys;
    }
}
