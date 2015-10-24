package com.fpt.router.crawler.test;


import com.fpt.router.artifacter.algorithm.MultiPointOptAlgorithm;
import com.fpt.router.artifacter.algorithm.TwoPointAlgorithm;
import com.fpt.router.artifacter.algorithm.TwoPointAlgorithm.SearchType;
import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.MapDAL;
import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.artifacter.model.viewmodel.Journey;
import com.fpt.router.artifacter.model.viewmodel.Path;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * Purpose:
 * Created by Huynh Quang Thao on 10/5/15.
*/
public class AlgorithmTest {
    public static void main(String[] args) {

        CityMap map = MapDAL.readDatabase();
        JPADaoImpl.closeFactory();

        LocalTime time = new LocalTime(8, 30, 0);

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
        String addressChoBaChieu = "Chợ Bà Chiểu";

        Location maximark = new Location();
        maximark.latitude = 10.800767;
        maximark.longitude = 106.659483;

        Location cho_kim_bien = new Location();
        cho_kim_bien.latitude = 0;
        cho_kim_bien.longitude = 10;

        // doi dien dh_nl
        Location doi_dien_dai_hoc_nong_lam = new Location();
        doi_dien_dai_hoc_nong_lam.latitude = 10.867109;
        doi_dien_dai_hoc_nong_lam.longitude = 106.787324;

        // ben xe dai hoc nong lam
        Location ben_xe_nong_lam = new Location();
        ben_xe_nong_lam.latitude = 10.868261;
        ben_xe_nong_lam.longitude = 106.787673;

        // dai hoc nong lam
        Location dai_hoc_nong_lam = new Location();
        dai_hoc_nong_lam.latitude = 10.868261;
        dai_hoc_nong_lam.longitude = 106.787673;

        // duc ba
        Location duc_ba = new Location();
        duc_ba.latitude = 10.779786;
        duc_ba.longitude = 106.698994;
        String addressDucBa = "Duc Ba";

       /* MultiPointOptAlgorithm multiPointOptAlgorithm = new MultiPointOptAlgorithm();
        List<Location> middleLocations = new ArrayList<Location>();
        middleLocations.add(duc_ba);
        middleLocations.add(cho_ba_chieu);
        middleLocations.add(dai_hoc_nong_lam);

        List<String> middleAddress = new ArrayList<String>();
        middleAddress.add(addressDucBa);
        middleAddress.add(addressChoBaChieu);
        middleAddress.add("dai hoc nong lam");

        List<Journey> journeys = multiPointOptAlgorithm.run(map, cvpm, "Software Park",
                middleLocations, middleAddress, time, 1500, 2, true);
        int abcd = 3;*/


        TwoPointAlgorithm twoPointAlgorithm = new TwoPointAlgorithm();

        String res = twoPointAlgorithm.solveAndReturnJSon(map, cvpm, doi_dien_dai_hoc_nong_lam, "Start Location", "End Location", time,
                Config.WALKING_DISTANCE, 2, false, SearchType.TWO_POINT);
        System.out.println(res);


    }
}