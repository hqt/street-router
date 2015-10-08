package com.fpt.router.crawler.database;

import com.fpt.router.crawler.dao.MapDAL;
import com.fpt.router.crawler.dao.common.JPADaoImpl;
import com.fpt.router.crawler.model.entity.CityMap;
import com.fpt.router.crawler.work.Work;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/1/15.
 */
public class DatabaseTest {

    public static void main(String[] args) {
        read();
    }

    public static void write() {
        // write
        Work work = new Work();
        CityMap map = work.run();
        EntityValidation validation = new EntityValidation(map);
        validation.run();
        MapDAL.insertDatabase(map);
        JPADaoImpl.closeFactory();
    }

    public static void read() {
        com.fpt.router.crawler.model.algorithm.CityMap map = MapDAL.readDatabase();
        DatabaseValidation validation = new DatabaseValidation(map);
        validation.run();
        JPADaoImpl.closeFactory();
    }

}
