package com.hqt.algorithm;

import com.hqt.Config;
import com.hqt.model.CityMap;
import com.hqt.model.Location;
import com.hqt.model.PathInfo;
import com.hqt.model.Station;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/30/15.
 */
public class TwoPointAlgorithm {

    CityMap map;

    public String run(Location start, Location end, String startAddress, String endAddress) {

        // 1. find some nearest locations near s and e
        s = new Station();
        s.code = "start_node";
        s.name = startAddress;
        s.street = startAddress;
        s.location = start;
        s.id = map.stations.size();
        findNearestLocationFrom(s);

        e = new Station();
        e.code = "end_node";
        e.name = endAddress;
        e.street = startAddress;
        e.location = end;
        e.id = map.stations.size() + 1;
        findNearestLocationFrom(e);

        // 2. check if exist stations near start and end
        // too far from all bus stations
        if (s.pathInfos.size() == 0) {
            System.out.println("start location too far");
            return "start location too far";
        }
        if (e.pathInfos.size() == 0) {
            System.out.println("end location too far");
            return "end location too far";
        }


        // too short
        if (Converter.distance(s.location, e.location) <= Config.WALKING_BUS_DISTANCE) {
            System.out.println("two locations too short. walking is better");
            return "too short";
        }

        // 3. algorithm
        init();
        dijkstra();

        // 4. get all nearest bus stations
        List<PathInfo> res = findShortestPathToLastPlace();
        for (int i = 0; i < res.size(); i++) {
            System.out.println(res.get(i).from.id + "  " + res.get(i).from.name + " " + res.get(i).type);
        }

        // 5. generate results
        System.out.println("lan");
        Result result = generateResult(res);
        System.out.println("lan1");
        Results results = new Results();
        System.out.println("lan2");
        results.getResult().add(result);

        System.out.println("lan3");
        String xml = marshall(schemaPath, results);
        System.out.println("thao");
        System.out.println(xml);
        return xml;
    }

    private void findNearestLocationFrom(Location from) {
        for (Station station : map.stations) {
            if (Converter.distance(from.location, station.location) <= Config.WALKING_DISTANCE) {
                // create new edge from start location to this station
                PathInfo pathInfo = new PathInfo(PathInfo.PathType.WALKING_BUS, from, station, null);
                from.pathInfos.add(pathInfo);
            }
        }
    }
}
