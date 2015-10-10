package com.fpt.router.artifacter.database;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/4/15.
 */

import com.fpt.router.artifacter.model.entity.CityMap;
import com.fpt.router.artifacter.model.entity.PathInfo;
import com.fpt.router.artifacter.model.entity.Station;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Purpose: validate model before running algorithm
 * Created by Huynh Quang Thao on 9/27/15.
 */
public class EntityValidation {

    private CityMap map;

    public EntityValidation(CityMap map) {
        this.map = map;
    }

    public void run() {
        testStation();
        // testPathInfo();
    }

    // test 1. all station's id must from 0 and consecutive number
    public void testStation() {
        List<Station> stations = map.getStations();
       /* stations.sort(new Comparator<Station>() {
            @Override
            public int compare(Station s1, Station s2) {
                return (int) (s1.getStationId() - s2.getStationId());
            }
        });*/
        for (int i = 0; i < stations.size(); i++) {
            Station s = stations.get(i);
            if (s.getStationId() != i) {
                System.out.printf("Station with id %d named %s is not in %d order \n", s.getStationId(), s.getName(), i);
            }
        }
        System.out.println("finish checking station id");
    }

    // test 2. In PathInfo. (routeId, toStationId) should be unique key
    public void testPathInfo() {
        Set<String> res = new HashSet<String>();
        for (Station station : map.getStations()) {
            for (PathInfo pathInfo : station.getTo()) {
                long routeId = pathInfo.getRoute().getRouteId();
                long toStationId = pathInfo.getTo().getStationId();
                String key = routeId + "." + toStationId;
                if (res.contains(key)) {
                    System.out.println("test PathInfo failed");
                    break;
                } else {
                    res.add(key);
                }
            }
        }
        System.out.println("finish checking routeId and toStationId on same PathInfo");
    }


}