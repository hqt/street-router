package com.fpt.router;

import com.fpt.router.dal.MapDAL;
import com.fpt.router.database.DemoDB;
import com.fpt.router.model.CityMap;

import java.sql.Time;

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
