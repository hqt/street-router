package com.fpt.router.database;

import com.fpt.router.dal.MapDAL;
import com.fpt.router.model.CityMap;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/1/15.
 */
public class DatabaseTest {

    public static void main(String[] args) {
        DemoDB db = new DemoDB();
        CityMap map = db.cityMap;
        MapDAL.insertDatabase(map);
    }
}
