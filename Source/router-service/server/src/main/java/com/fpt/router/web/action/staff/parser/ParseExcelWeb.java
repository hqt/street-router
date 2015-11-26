package com.fpt.router.web.action.staff.parser;

import com.fpt.router.artifacter.model.entity.CityMap;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Trip;
import com.fpt.router.artifacter.model.helper.RouteType;
import com.fpt.router.artifacter.utils.TimeUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by datnt on 11/24/2015.
 */
public class ParseExcelWeb {

    public Map<Integer, String> busTimeExcelLinks;
    CityMap map;
    TimeUtils timeUtils = new TimeUtils();

    public ParseExcelWeb(CityMap map) {
        busTimeExcelLinks = new HashMap<Integer, String>();
        this.map = map;
    }

    private void parseAllExcelLinks() {
        // Retrieve list table data
        GetDataFromAjax getDataFromAjax = new GetDataFromAjax();
        ArrayList<String> tableData = getDataFromAjax.crawAjaxData();

        // Parse String to Document
        for (String table : tableData) {
            Document doc = Jsoup.parse(table);
            // Get element by using css selector to get data which want
            Elements elements = doc.select("table tr");
            for (int i = 1; i < elements.size() - 2; i++)
                try {
                    String tripNoString = elements.get(i).select("td").get(0).select("a").text();

                    // parse int tripNoString
                    if (tripNoString.contains("-")) {
                        tripNoString = tripNoString.replace("-", "");
                    }
                    int routeNo = Integer.parseInt(tripNoString);

                    String tripLink = elements.get(i).select("td").get(2).select("a").attr("href");
                    // put key and value to map
                    for (Route route : map.getRoutes()) {
                        if (route.getRouteNo() == routeNo) {
                            busTimeExcelLinks.put(routeNo, "http://www.buyttphcm.com.vn/" + tripLink);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Something wrong went parseAllExcelLinks");
                }
        }
    }

    private void processExcelLink() {
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (Route route : map.getRoutes()) {
            CrawDataThread thread = new CrawDataThread(route, busTimeExcelLinks.get(route.getRouteNo()));
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

        Route route;
        String excelLink;

        public CrawDataThread(Route route, String excelLink) {
            this.route = route;
            this.excelLink = excelLink;
        }

        @Override
        public void run() {
            InputStream in = download();
            parseExcelFile2(in);
        }

        /*
            Download the excel file from link retrieved
         */
        public InputStream download() {
            //flagProcessLinkExcel += 1;
            InputStream inputStream = null;
            try {
                URL url = new URL(excelLink);
                inputStream = new BufferedInputStream(url.openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        private void parseExcelFile2(InputStream inputStream) {
            Workbook workbook = null;
            try {
                CharSequence extensionExcel = ".xlsx";
                if (excelLink.contains(extensionExcel)) {
                    workbook = new XSSFWorkbook(inputStream);
                } else {
                    workbook = new HSSFWorkbook(inputStream);
                }

            } catch (Exception e) {
                System.out.println("Excel Link: " + excelLink);
                e.printStackTrace();
            }

            assert workbook != null;
            Sheet sheet = workbook.getSheetAt(0);


            List<Integer> index;
            Integer rowStart = 0;
            Integer departIndex = -1;
            Integer returnIndex = -1;
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                index = getCellIndex(sheet.getRow(i));
                if (index.size() == 2) {
                    departIndex = index.get(0);
                    returnIndex = index.get(1);
                    rowStart = sheet.getRow(i).getRowNum() + 1;
                    break;
                }
            }

            for (int i = rowStart; i < sheet.getLastRowNum(); i++) {
                // Initialize Trip
                Trip tripDepart = new Trip();
                Trip tripReturn = new Trip();
                // Add value to trip
                Row nextRow = sheet.getRow(i);
                try {
                    tripDepart.setTripNo((int) nextRow.getCell(0).getNumericCellValue());
                    tripReturn.setTripNo((int) nextRow.getCell(0).getNumericCellValue());
                    if (departIndex != -1 && returnIndex != -1) {
                        tripDepart.setStartTime(timeUtils.convertExcelDate(nextRow.getCell(departIndex ).getDateCellValue()));
                        tripDepart.setEndTime(timeUtils.convertExcelDate(nextRow.getCell(departIndex + 1).getDateCellValue()));
                        tripReturn.setStartTime(timeUtils.convertExcelDate(nextRow.getCell(returnIndex).getDateCellValue()));
                        tripReturn.setEndTime(timeUtils.convertExcelDate(nextRow.getCell(returnIndex + 1).getDateCellValue()));
                    }
                } catch (Exception ex) {
                    continue;
                }
                if (tripDepart.getTripNo() != 0) {
                    if (route.getRouteType().equals(RouteType.DEPART)) {
                        tripDepart.setRoute(route);
                        route.getTrips().add(tripDepart);
                    }
                    if (route.getRouteType().equals(RouteType.RETURN)) {
                        tripReturn.setRoute(route);
                        route.getTrips().add(tripReturn);
                    }
                }
            }
        }

        public List<Integer> getCellIndex(Row nextRow) {

            List<Integer> index = new ArrayList<Integer>();
            int indexStartTime;
            CharSequence cellValue = "ĐI";
            int i;
            for (i = 0; i < nextRow.getLastCellNum(); i++) {
                try {
                    if (nextRow.getCell(i).getCellType() == Cell.CELL_TYPE_STRING) {
                        if (nextRow.getCell(i).getStringCellValue().toUpperCase().contains(cellValue)) {
                            indexStartTime = nextRow.getCell(i).getColumnIndex();
                            index.add(indexStartTime);
                            i += 2;
                            int y = i;
                            if (nextRow.getCell(y).getStringCellValue().toUpperCase().contains(cellValue)) {
                                indexStartTime = nextRow.getCell(y).getColumnIndex();
                                index.add(indexStartTime);
                                break;
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Row: " +nextRow.getRowNum() + " i " + " link: " +excelLink);
                }
            }

            return index;
        }

    }

}
