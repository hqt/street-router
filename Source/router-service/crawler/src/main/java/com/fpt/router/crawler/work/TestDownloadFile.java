package com.fpt.router.crawler.work;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
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

    public static void downloadJsonFile(int busNo, boolean type) {
        try {

            String link = "http://mapbus.ebms.vn/ajax.aspx?action=listRouteStations&rid=" + busNo + "&" + type + "=false";

            URL url = new URL(link);
            BufferedReader buf = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            //Set connection timeout
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(15000);

            String pathFolder = "D:\\Capstone\\street-router2\\Source\\router-service\\crawler\\src\\main\\resources\\json";
            String pathFile = pathFolder + "\\" + busNo + "-" + type + "." + "json";
            FileOutputStream fos = new FileOutputStream(pathFile);

            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.flush();
            fos.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void downloadJsonFile2(int busNo, boolean type) {

        String link = "http://mapbus.ebms.vn/ajax.aspx?action=listRouteStations&rid=" + busNo + "&" + "isgo=" + type;
        System.out.println(link);



        String pathFolder = "D:\\Capstone\\street-router2\\Source\\router-service\\crawler\\src\\main\\resources\\json";
        String pathFile = pathFolder + "\\" + busNo + "-" + type + "." + "json";
        System.out.println(pathFile);
        File f = new File(pathFile);

        InputStream inputStream;
        try {

            URL url = new URL(link);
            inputStream = new BufferedInputStream(url.openStream());

            if (!f.exists()) {
                f.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(f);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }

            System.out.println(busNo + "-" + type + " Download Done!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writefile(String content) {
        File file = new File("C:\\Users\\datnt\\Desktop\\abc.txt");
        if (file == null) {
            System.out.println("Ân Càng Thâm, Oán Càng Sâu");
        }
        try {
            PrintWriter fw = new PrintWriter("link.txt", "UTF-8");
            fw.println(content);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        /*TestDownloadFile testDownloadFile = new TestDownloadFile();*/

        //downloadJsonFile();
        /*testDownloadFile.getAllExcelLink();
        testDownloadFile.processDownload();*/
        /*String content = "Cái éo gì đây";
        writefile(content);*/
    }
}
