package com.fpt.router.crawler.database;

import com.fpt.router.crawler.dao.MapDAL;
import com.fpt.router.crawler.dao.StationDAO;
import com.fpt.router.crawler.dao.TempDAO;
import com.fpt.router.crawler.dao.common.GenericDao;
import com.fpt.router.crawler.dao.common.JPADaoImpl;
import com.fpt.router.crawler.model.entity.CityMap;
import com.fpt.router.crawler.model.entity.Station;
import com.fpt.router.crawler.model.entity.Temp;
import com.fpt.router.crawler.utils.TimeUtils;
import com.fpt.router.crawler.work.Work;
import org.joda.time.*;

import java.util.Date;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/1/15.
 */
public class DatabaseTest {

    public static void main(String[] args) {


       /* // write
        Work work = new Work();
        CityMap map = work.run();
        Validation validation = new Validation(map);
        validation.run();
        MapDAL.insertDatabase(map);
        JPADaoImpl.closeFactory();*/

        // read
        com.fpt.router.crawler.model.algorithm.CityMap map = MapDAL.readDatabase();

        int a = 3;


    }
}
