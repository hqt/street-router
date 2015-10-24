package com.fpt.router.crawler.test;


import com.fpt.router.artifacter.algorithm.RaptorAlgorithm;
import com.fpt.router.artifacter.dao.MapDAL;
import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.algorithm.Station;
import com.fpt.router.artifacter.model.helper.PathType;
import com.fpt.router.artifacter.model.viewmodel.*;
import com.fpt.router.artifacter.utils.JSONUtils;
import com.google.gson.Gson;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.io.BufferedReader;
import java.io.StringReader;

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
        startPath.pathType = PathType.WALKING;
        startPath.transferTurn = 0;
        startPath.distance = 100;
        startPath.time = new Period(0, 1, 30, 0);

        // create end path
        Path endPath = new Path();
        endPath.stationFromName = "End";
        endPath.stationToName = "End";
        endPath.pathType = PathType.WALKING;
        endPath.transferTurn = 0;
        endPath.distance = 100;
        endPath.time = new Period(0, 1, 30, 0);

        Result res = algor.runClassical(map, start, end, startPath, endPath, 2, false, new LocalTime(8, 30));
        int a = 3;

        /*// convert this list to json
        ObjectMapper mapper = new ObjectMapper();
        //mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        mapper.registerModule(new JodaModule());
        //mapper.enableDefaultTyping(); // default to using DefaultTyping.OBJECT_AND_NON_CONCRETE
        //mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int b = 3;

        System.out.println(json);

        Result test;
        try {
            ObjectMapper mapper1 = new ObjectMapper();
            mapper.registerModule(new JodaModule());
            //mapper.enableDefaultTyping(); // default to using DefaultTyping.OBJECT_AND_NON_CONCRETE
            //mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            test = mapper1.readValue(json, Result.class);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        int c = 4;
       Gson gson = JSONUtils.buildGson();

        String json = gson.toJson(res);
        System.out.println(json);

       BufferedReader bufferedReader = new BufferedReader(new StringReader(json));
       Result test = gson.fromJson(bufferedReader, Result.class);
        int m = 3;

    }

}
