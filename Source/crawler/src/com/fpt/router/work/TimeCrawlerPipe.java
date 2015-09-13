package com.fpt.router.work;

import com.fpt.router.model.CityMap;
import com.fpt.router.model.Route;
import com.fpt.router.model.Trip;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Rows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
            InputStream in = download();
            parseExcelFile(in);
        }

        /*
            Download the excel file from link retrieved
         */
        public InputStream download() {
            InputStream inputStream = null;
            try {
                URL url = new URL(excelLink);
                inputStream = new BufferedInputStream(url.openStream());
                if (inputStream == null) {
                    inputStream.reset();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        private void parseExcelFile(InputStream inputStream) {

            Workbook workbook = null;
            try {
                workbook = new HSSFWorkbook(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.rowIterator();

            // Initial route
            Route routeDepart = new Route();
            routeDepart.setRouteType(Route.RouteType.DEPART);
            Route routeReturn = new Route();
            routeReturn.setRouteType(Route.RouteType.RETURN);

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();

                if (nextRow.getCell(0) != null) {
                    int blankRow = getRowBlank(nextRow);
                    if (blankRow == 0) {
                        getSumTrip(iterator);
                    }
                }

                Trip tripDepart = new Trip(); // Create Trip
                Trip tripReturn = new Trip();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    addDataToTrip(tripDepart, tripReturn, cell);
                }

                // Add trip to route
                if (tripDepart.getTripNo() != 0) {
                    routeDepart.getTrips().add(tripDepart);
                }
                if (tripReturn.getTripNo() != 0) {
                    routeReturn.getTrips().add(tripReturn);
                }

                map.routes.add(routeDepart);
                map.routes.add(routeReturn);
            }
        }

        private int getRowBlank(Row row) {
            int flagBlankRow = row.getLastCellNum();
            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                // Compare blank cell
                try {
                    if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                        flagBlankRow -= 1;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            return flagBlankRow;
        }

        private void addDataToTrip(Trip tripDepart, Trip tripReturn, Cell cell) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    int indexColumn = cell.getColumnIndex();
                    switch (indexColumn) {
                        case 0:
                            tripDepart.setTripNo((int) cell.getNumericCellValue());
                            break;
                        case 1:
                            tripDepart.setStartTime(cell.getDateCellValue());
                            break;
                        case 2:
                            tripDepart.setEndTime(cell.getDateCellValue());
                            break;
                        case 5:
                            tripReturn.setStartTime(cell.getDateCellValue());
                            break;
                        case 6:
                            tripReturn.setStartTime(cell.getDateCellValue());
                            break;
                    }
            }
        }

        private void getSumTrip(Iterator<Row> rowIterator) {
            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            //System.out.println("Numeric: " + cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_STRING:
                            //System.out.println("String: " + cell.getStringCellValue());
                    }
                }
            }
        }
    }
}

