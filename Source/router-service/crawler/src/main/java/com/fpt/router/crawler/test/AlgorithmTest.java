package com.fpt.router.crawler.test;


import com.fpt.router.artifacter.algorithm.TwoPointAlgorithm;
import com.fpt.router.artifacter.dao.MapDAL;
import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.artifacter.model.viewmodel.Path;
import org.joda.time.LocalTime;
import org.joda.time.Period;

/*
 *
 * Purpose:
 * Created by Huynh Quang Thao on 10/5/15.
*/
public class AlgorithmTest {
    public static void main(String[] args) {

        CityMap map = MapDAL.readDatabase();

        // start
        Location start = new Location();
        start.latitude = 10.771918;
        start.longitude = 106.698347;

        // end
        Location end = new Location();
        end.latitude = 10.853132;
        end.longitude = 106.626289;

        TwoPointAlgorithm twoPointAlgorithm = new TwoPointAlgorithm();
        LocalTime time = new LocalTime(10, 0);
        String res = twoPointAlgorithm.run(map, start, end, "Ben Thanh market", "Software Park", time,
                                            300, 2, false);
        System.out.println(res);
    }
}