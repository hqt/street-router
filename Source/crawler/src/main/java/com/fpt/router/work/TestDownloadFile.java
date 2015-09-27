package com.fpt.router.work;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by datnt on 9/18/2015.
 */
public class TestDownloadFile {

    public static final String url = "http://www.buyttphcm.com.vn/Images/EXCEL_FILE/144_2014-08-18.xls";
    public static final Map<Integer, String> busTimeExcelLinks = new HashMap<Integer, String>();

    public void getAllExcelLink() {

        // Retrieve list table data
        GetDataFromAjax getDataFromAjax = new GetDataFromAjax();
        ArrayList<String> tableData = getDataFromAjax.crawAjaxData();

        // Parse String to Document
        for (String table : tableData) {
            Document doc = Jsoup.parse(table);
            // Get element by using css selector to get data which want
            Elements elements = doc.select("table tr");
            for (int i = 1; i < elements.size() - 2; i++) {
                try {
                    String tripNoString = elements.get(i).select("td").get(0).select("a").text();

                    // parse int tripNoString
                    if (tripNoString.contains("-")) {
                        tripNoString = tripNoString.replace("-", "");
                    }
                    int tripNo = Integer.parseInt(tripNoString);

                    String tripLink = elements.get(i).select("td").get(2).select("a").attr("href");
                    // put key and value to map
                    busTimeExcelLinks.put(tripNo, "http://www.buyttphcm.com.vn/" + tripLink);
                } catch (Exception e) {
                    continue;
                }
            }
        }
    }

    public void donwload(String link, String busId) {
        try {
            URL url = new URL(link);
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            //Set connection timeout
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(15000);

            String currentDir = System.getProperty("user.dir");
            CharSequence crawler = "crawler";
            String pathFolder = currentDir.replace(crawler, "");

            CharSequence typeFileXls = ".xlsx";

            FileOutputStream fos;
            if (link.contains(typeFileXls)) {
                fos = new FileOutputStream(pathFolder + "\\localExcelFIle" + "\\" + busId + ".xlsx");
            } else {
                fos = new FileOutputStream(pathFolder + "\\localExcelFIle" + "\\" + busId + ".xlsx");
            }

            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.flush();
            fos.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processDownload() {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for (Map.Entry<Integer, String> entry : busTimeExcelLinks.entrySet()) {
            DownloadExcel downloadExcel = new DownloadExcel(entry.getKey(), entry.getValue());
            executorService.execute(downloadExcel);
        }

        // wait for all thread finish
        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class DownloadExcel extends Thread {

        int busId;
        String link;

        public DownloadExcel(int busid, String link) {
            this.busId = busid;
            this.link = link;
        }

        @Override
        public void run() {
            donwload(link, String.valueOf(busId));
        }
    }

    public static void main(String args[]) {
        TestDownloadFile testDownloadFile = new TestDownloadFile();
        testDownloadFile.getAllExcelLink();
        testDownloadFile.processDownload();
    }
}
