package com.fpt.router.crawler.test;


import com.fpt.router.artifacter.algorithm.TwoPointAlgorithm;
import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.MapDAL;
import com.fpt.router.artifacter.dao.common.JPADaoImpl;
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
        JPADaoImpl.closeFactory();

        // start
        Location start = new Location();
        start.latitude = 10.771918;
        start.longitude = 106.698347;

        // end
        Location end = new Location();
        end.latitude = 10.853132;
        end.longitude = 106.626289;

        Location cvpm = new Location();
        cvpm.latitude = 10.855090;
        cvpm.longitude= 106.628394;

        Location an_phu = new Location();
        an_phu.latitude = 10.801913;
        an_phu.longitude = 106.764747;

        Location cho_ba_chieu = new Location();
        cho_ba_chieu.latitude = 10.801605;
        cho_ba_chieu.longitude = 106.698817;

        Location maximark = new Location();
        maximark.latitude = 10.800767;
        maximark.longitude = 106.659483;

        TwoPointAlgorithm twoPointAlgorithm = new TwoPointAlgorithm();
        LocalTime time = new LocalTime(10, 0);
        String res = twoPointAlgorithm.solveAndReturnJSon(map, cvpm, cho_ba_chieu, "Ben Thanh market", "Software Park", time,
                Config.WALKING_DISTANCE, 2, false);
        System.out.println(res);
    }
}