package com.fpt.router.crawler.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fpt.router.artifacter.algorithm.RaptorAlgorithm;
import com.fpt.router.artifacter.dao.MapDAL;
import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.algorithm.Station;
import com.fpt.router.artifacter.model.helper.PathType;
import com.fpt.router.artifacter.model.viewmodel.Path;
import com.fpt.router.artifacter.model.viewmodel.Result;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.io.IOException;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/8/15.
 */
public class RaptorAlgorithmTest {
    public static void main(String[] args) {
        CityMap map = MapDAL.readDatabase();
        JPADaoImpl.closeFactory();
        System.out.println("route size: " + map.routes.size());
        RaptorAlgorithm algor = new RaptorAlgorithm();

        Station start = map.getStationById(1931);
        Station end = map.getStationById(3460);

        start = map.getStationById(1445);
         end = map.getStationById(466);

        Path startPath = new Path();
        startPath.stationFromName = "Start";
        startPath.stationToName = start.name;
        startPath.type = PathType.WALKING;
        startPath.transferTurn = 0;
        startPath.distance = 100;
        startPath.time = new Period(0, 1, 30, 0);

        // create end path
        Path endPath = new Path();
        endPath.stationFromName = "End";
        endPath.stationToName = "End";
        endPath.type = PathType.WALKING;
        endPath.transferTurn = 0;
        endPath.distance = 100;
        endPath.time = new Period(0, 1, 30, 0);

        Result res = algor.run(map, start, end, startPath, endPath, 2, false, new LocalTime(8, 30));
        int a = 3;
        // convert this list to json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int b = 3;
        System.out.println(json);
    }
}
