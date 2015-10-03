package com.fpt.router.crawler;

import com.fpt.router.crawler.dao.MapDAL;
import com.fpt.router.crawler.database.DemoDB;
import com.fpt.router.crawler.model.entity.CityMap;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/5/15.
 */
public class Main {
    public static void main(String[] args) {

        DemoDB demoDB = new DemoDB();
        CityMap cityMap = demoDB.cityMap;
        //insert data into database
        MapDAL.insertDatabase(cityMap);
    }
}
