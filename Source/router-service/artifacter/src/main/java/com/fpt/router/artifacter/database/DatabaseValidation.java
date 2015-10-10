package com.fpt.router.artifacter.database;


import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.algorithm.Connection;
import com.fpt.router.artifacter.model.algorithm.PathInfo;
import com.fpt.router.artifacter.model.algorithm.Route;
import com.fpt.router.artifacter.model.algorithm.Station;
import com.fpt.router.artifacter.model.algorithm.Trip;

import java.util.*;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/8/15.
 */
public class DatabaseValidation {

    private CityMap map;

    public DatabaseValidation(CityMap map) {
        this.map = map;
    }

    public void run() {
        testStation();
        testStationCollection();
    }


    // test 1. all station's id must from 0 and consecutive number
    public void testStation() {
        List<Station> stations = map.stations;
        /*stations.sort(new Comparator<Station>() {
            @Override
            public int compare(Station s1, Station s2) {
                return (int) (s1.id - s2.id);
            }
        });*/

        for (int i = 0; i < stations.size(); i++) {
            Station s = stations.get(i);
            if (s.id != i) {
                System.out.printf("Station with id %d named %s is not in %d order \n", s.id, s.name, i);
            }
        }
        System.out.println("finish checking station id");
        System.out.println("--------------------------");
    }


    public void testStationCollection() {
        for (Route route : map.routes) {
            if (Config.blockRoute.contains(route.routeNo)) continue;
            testStationCollection(route);
        }
    }

    private void testStationCollection(Route route) {
        Set<Station> collection1 = new TreeSet<Station>(new Comparator<Station>() {
            @Override
            public int compare(Station s1, Station s2) {
                return s1.id - s2.id;
            }
        });

        Set<Station> collection2 = new TreeSet<Station>(new Comparator<Station>() {
            @Override
            public int compare(Station s1, Station s2) {
                return s1.id - s2.id;
            }
        });

        for (PathInfo pathInfo : route.pathInfos) {
            collection1.add(pathInfo.from);
            if (pathInfo.to != null) {
                collection1.add(pathInfo.to);
            }
        }

        for (Trip trip : route.trips) {
            if (trip.connections.size() != route.pathInfos.size()) {
                System.out.println("Delta: " + (trip.connections.size() - route.pathInfos.size()));
            }
            for (Connection connection : trip.connections) {
                collection2.add(connection.pathInfo.from);

                /*// WWTFFF. Explain here
                if (connection.pathInfo.to != null) {
                    collection2.add(connection.pathInfo.to);
                }*/
            }
        }

        System.out.println("route: " + route.routeNo);

        boolean isWrong = false;

        for (Station stationA : collection1) {
            if (!collection2.contains(stationA)) {
                System.out.println(stationA.id + "\t" + stationA.code);
                isWrong = true;
            }
        }

        if (isWrong) {
            isWrong = false;
            System.out.println("****");
        }

        for (Station stationB : collection2) {
            if (!collection1.contains(stationB)) {
                System.out.println(stationB.id + "\t" + stationB.code);
                isWrong = true;
            }
        }

        if (isWrong) {
            System.out.println("###########################");
        }
    }
}
