package com.fpt.router.artifacter.algorithm;


import com.fpt.router.artifacter.dao.MapDAL;
import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.algorithm.CityMap;
import com.fpt.router.artifacter.model.helper.Location;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/*
 *
 * Purpose:
 * Created by Huynh Quang Thao on 10/5/15.
*/
public class AlgorithmTest {

    static final String STRING_KEY_PATTERN = ".+";
    static final Pattern LEGAL_KEY_PATTERN = Pattern.compile(STRING_KEY_PATTERN);

    public static void main(String[] args) {

        JPADaoImpl.enableStaticEntityManager();
        CityMap map = MapDAL.readDatabase();
        JPADaoImpl.disableStaticEntityManager();

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

        // dai hoc nong lam
        Location dai_hoc_nong_lam_2 = new Location();
        dai_hoc_nong_lam.latitude = 10.872103;
        dai_hoc_nong_lam.longitude = 106.7928171;


        // duc ba
        Location duc_ba = new Location();
        duc_ba.latitude = 10.779786;
        duc_ba.longitude = 106.698994;
        String addressDucBa = "Đức Bà";

        // au co
        Location au_co = new Location();
        au_co.latitude = 10.797649;
        au_co.longitude = 106.637749;

        // tran chanh chieu
        Location tran_chanh_chieu = new Location();
        tran_chanh_chieu.latitude = 10.751997;
        tran_chanh_chieu.longitude = 106.653127;

        Location ha_lo = new Location();
        ha_lo.latitude = 10.869391;
        ha_lo.longitude = 106.805186;

        Location vincom = new Location();
        vincom.latitude = 10.360637;
        vincom.longitude = 106.662486;

        Location nguyen_dinh_chieu = new Location();
        nguyen_dinh_chieu.latitude = 10.775800;
        nguyen_dinh_chieu.longitude = 106.687280;

        MultiPointOptAlgorithm multiPointOptAlgorithm = new MultiPointOptAlgorithm();

        List<Location> middleLocations = new ArrayList<Location>();
        middleLocations.add(au_co);
        middleLocations.add(cvpm);
        middleLocations.add(au_co);

        List<String> middleAddress = new ArrayList<String>();
        middleAddress.add("au co");
        middleAddress.add("tran chanh chieu");
        middleAddress.add("ha lo");

/*
        List<Journey> journeys = multiPointOptAlgorithm.run(map, cvpm, "Software Park",
                middleLocations, middleAddress, time, 500, 2, true);
        Gson gson = JSONUtils.buildGson();

        String json = gson.toJson(journeys);

        System.out.println(json);
*/

        TwoPointAlgorithm twoPointAlgorithm = new TwoPointAlgorithm();

        String res = twoPointAlgorithm.solveAndReturnJSon(map,
                vincom, nguyen_dinh_chieu,
                "Start Location", "End Location",
                time, 600, 2, false, TwoPointAlgorithm.SearchType.TWO_POINT);
        System.out.println(res);


    }
}