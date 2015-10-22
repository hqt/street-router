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

        List<List<Location>> swap = swap(middleLocations, end);
        for (int i = 0; i < swap.size(); i++) {
            List<Location> middle = swap.get(i);
            end = middle.get(2);
            middle.remove(2);
            journeys.addAll(multiPointAlgorithm.getJourneys(map, start, end, startAddress, endAddress,
                    middle, middleAddresses,
                    departureTime, walkingDistance, K, isOptimizeK));
        }

//        for(int i = 0; i < 3; i ++) {
//            Location swap = end;
//            end = middleLocations.get(0);
//            middleLocations.remove(0);
//            middleLocations.add(swap);
//            journeys.addAll(multiPointAlgorithm.getJourneys(map, start, end, startAddress, endAddress,
//                    middleLocations, middleAddresses,
//                    departureTime, walkingDistance, K, isOptimizeK));
//            int a = 3;
//        }

        sort(journeys);

        return limitJourney(journeys);
    }

    public List<List<Location>> swap(List<Location> middleLocations, Location end) {
        List<List<Location>> resultSwapLocation = new ArrayList<List<Location>>();

        middleLocations.add(end);

        List<Location> locations1 = new ArrayList<Location>();
        locations1.add(middleLocations.get(0));
        locations1.add(middleLocations.get(1));
        locations1.add(middleLocations.get(2));

        List<Location> locations2 = new ArrayList<Location>();
        locations2.add(middleLocations.get(2));
        locations2.add(middleLocations.get(1));
        locations2.add(middleLocations.get(0));

        List<Location> locations3 = new ArrayList<Location>();
        locations3.add(middleLocations.get(0));
        locations3.add(middleLocations.get(2));
        locations3.add(middleLocations.get(1));

        resultSwapLocation.add(locations1);
        resultSwapLocation.add(locations2);
        resultSwapLocation.add(locations3);
        return resultSwapLocation;
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
