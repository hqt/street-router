package com.fpt.router.work;

import com.fpt.router.model.CityMap;
import com.fpt.router.model.Route;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/6/15.
 */
public class TimeCrawlerPipe {

    public Map<Integer, String> busTimeExcelLinks;
    CityMap map;

    public TimeCrawlerPipe() {
        busTimeExcelLinks = new HashMap<Integer, String>();
        map = new CityMap();
    }

    private void parseAllExcelLinks() {
        // Retrieve list table data
        GetDataFromAjax getDataFromAjax = new GetDataFromAjax();
        ArrayList<String> tableData = getDataFromAjax.crawAjaxData();

        // Parse String to Document
        for (String table: tableData){
            Document doc = Jsoup.parse(table);
            // Get element by using css selector to get data which want
            Elements elements = doc.select("table tr");
            for (int i = 1; i < elements.size() - 2; i++) {
                try {
                    String tripNoString = elements.get(i).select("td").get(0).select("a").text();
                    // parse int tripNoString
                    if (tripNoString.contains("-")) {
                        tripNoString = tripNoString.replace("-","");
                    }
                    int tripNo = Integer.parseInt(tripNoString);

                    String tripLink = elements.get(i).select("td").get(2).select("a").attr("href");
                    StringBuilder linkExcel = new StringBuilder();
                    linkExcel.append("http://www.buyttphcm.com.vn/");
                    linkExcel.append(tripLink);
                    // put key and value to map
                    busTimeExcelLinks.put(tripNo,linkExcel.toString());
                } catch (Exception e) {
                    continue;
                }
            }
        }
    }

    private void processExcelLink() {
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (Map.Entry<Integer, String> entry : busTimeExcelLinks.entrySet()) {
            CrawDataThread thread = new CrawDataThread(entry.getKey(), entry.getValue());
            executor.execute(thread);
        }

        // waiting for all thread finish
        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public CityMap run() {
        parseAllExcelLinks();
        processExcelLink();
        return map;
    }

    private class CrawDataThread extends Thread {

        int busId;
        String excelLink;

        public CrawDataThread(int busId, String excelLink) {
            this.busId = busId;
            this.excelLink = excelLink;
        }

        @Override
        public void run() {
            download();
            parseExcelFile();
        }

        private InputStream download() {
            return null;
        }

        private void parseExcelFile() {
            Route route = null;
            // implement here

            // map.routes.add(route);
        }
    }
}

