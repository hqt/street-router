package com.fpt.router.work;

import com.fpt.router.model.CityMap;

import java.util.Map;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/6/15.
 */
public class Work {
    public void run() {
        BusCrawlerPipe crawler = new BusCrawlerPipe();
        crawler.run();

        TimeCrawlerPipe timeCrawler = new TimeCrawlerPipe();
        CityMap map = timeCrawler.run();
        Map<Integer, String> links = timeCrawler.busTimeExcelLinks;
        for (Map.Entry<Integer, String> entry : links.entrySet()) {
            System.out.println(entry.getKey() + "---> " + entry.getValue());
        }
        System.out.println("Size Map: " +timeCrawler.map.getRoutes().size());
    }

    public static void main(String[] args) {
        Work work = new Work();
        work.run();
    }
}
