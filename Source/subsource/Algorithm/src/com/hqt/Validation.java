package com.hqt;

import com.hqt.model.CityMap;
import com.hqt.model.Station;

import java.util.Comparator;
import java.util.List;

/**
 * Purpose: validate model before running algorithm
 * Created by Huynh Quang Thao on 9/27/15.
 */
public class Validation {

    private CityMap map;

    public Validation(CityMap map) {
        this.map = map;
    }

    public void run() {
        testStation();
    }

    // test 1. all station's id must from 0 and consecutive number
    public void testStation() {
        List<Station> stations = map.stations;
        stations.sort(new Comparator<Station>() {
            @Override
            public int compare(Station s1, Station s2) {
                return s1.id - s2.id;
            }
        });
        for (int i = 0; i < stations.size(); i++) {
            Station s = stations.get(i);
            if (s.id != i) {
                System.out.printf("Station with id %d named %s is not in %d order \n", s.id, s.name, i);
            }
        }
    }
}
