package com.fpt.router.crawler.work;

import com.fpt.router.artifacter.dao.MapDAL;
import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.entity.CityMap;
import com.fpt.router.artifacter.model.entity.Route;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/6/15.
 */
public class Work {
    public CityMap run() {

        //Parse Json Data
        /*BusCrawlerPipe crawler = new BusCrawlerPipe();
        crawler.run();
        System.out.println("ABCD");*/
        // Parse Excel Online
       /* TimeCrawlerPipe timeCrawler = new TimeCrawlerPipe();
        CityMap map = timeCrawler.run();
        Map<Integer, String> links = timeCrawler.busTimeExcelLinks;
        for (Map.Entry<Integer, String> entry : links.entrySet()) {
            System.out.println(entry.getKey() + "---> " + entry.getValue());
        }
        System.out.println("Size Map: " +timeCrawler.map.getRoutes().size());*/

        // Read Excel From Local
        BusCrawlerPipe busCrawler = new BusCrawlerPipe();
        CityMap map = busCrawler.run();
        ReadExcelFileFromLocal readExcelFileFromLocal = new ReadExcelFileFromLocal(map);
        readExcelFileFromLocal.run();

        System.out.println("Size Route on Map: " + map.getRoutes().size());

        int a = 3;
        MapDAL.insertDatabase(map);

        JPADaoImpl.closeFactory();
        return map;

    }

    public static void main(String[] args) {
        Work work = new Work();
        work.run();
    }
}
