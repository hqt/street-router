package com.fpt.router.work;

import com.fpt.router.model.CityMap;
import com.fpt.router.model.Route;
import com.fpt.router.model.Trip;
import org.apache.poi.hdgf.streams.Stream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
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
    public int flagProcessLinkExcel = 0;

    public TimeCrawlerPipe() {
        busTimeExcelLinks = new HashMap<Integer, String>();
        map = new CityMap();
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
            for (int i = 1; i < elements.size() - 2; i++) {
                try {
                    String tripNoString = elements.get(i).select("td").get(0).select("a").text();

                    // parse int tripNoString
                    if (tripNoString.contains("-")) {
                        tripNoString = tripNoString.replace("-", "");
                    }
                    int tripNo = Integer.parseInt(tripNoString);

                    String tripLink = elements.get(i).select("td").get(2).select("a").attr("href");
                    StringBuilder linkExcel = new StringBuilder();
                    linkExcel.append("http://www.buyttphcm.com.vn/");
                    linkExcel.append(tripLink);
                    // put key and value to map
                    busTimeExcelLinks.put(tripNo, linkExcel.toString());
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
            List<Route> routes = parseExcelFile(in);
            for (Route route : routes) {
                map.getRoutes().add(route);
            }
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

        private List<Route> parseExcelFile(InputStream inputStream) {

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
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.rowIterator(); // get iterator row

            List<Route> routes = new ArrayList<Route>();
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


                for (int i = nextRow.getFirstCellNum() ; i < nextRow.getLastCellNum(); i++) {
                    int lastCellNum = nextRow.getLastCellNum();

                    if (busId == 22){
                        handleSpecialHandle(tripDepart,tripReturn,nextRow);
                        continue;
                    }

                    switch (i) {
                        case 0:
                            if (nextRow.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                tripDepart.setTripNo((int) nextRow.getCell(i).getNumericCellValue());
                                tripReturn.setTripNo((int) nextRow.getCell(i).getNumericCellValue());
                            }
                            break;
                        case 1:
                            try {
                                if (nextRow.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    tripDepart.setStartTime(nextRow.getCell(i).getDateCellValue());
                                }
                            } catch (Exception ex) {
                                continue;
                            }
                            break;
                        case 2:
                            if (nextRow.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                tripDepart.setEndTime(nextRow.getCell(i).getDateCellValue());
                            }
                            break;
                    }

                    try{
                        if (i == lastCellNum - 2) {
                            if (nextRow.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                tripReturn.setStartTime(nextRow.getCell(i).getDateCellValue());
                            }
                        }
                        if (i == lastCellNum - 1) {
                            if (nextRow.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                tripReturn.setEndTime(nextRow.getCell(i).getDateCellValue());
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println("Index-" + i + " - " +excelLink + " - " + nextRow.getCell(i) + " - " + nextRow.getRowNum());
                        continue;
                    }

                }

                /*while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    addDataToTrip(tripDepart, tripReturn, cell);
                }*/

                if (tripDepart.getTripNo() != 0) {
                    routeDepart.getTrips().add(tripDepart);
                    routeDepart.setRouteId(busId);
                }
                if (tripReturn.getTripNo() != 0) {
                    routeReturn.getTrips().add(tripReturn);
                    routeReturn.setRouteId(busId);
                }
            }
            routes.add(routeDepart);
            routes.add(routeReturn);

            return routes;
        }

        private int getRowBlank(Row row) {
            int flagBlankRow = row.getLastCellNum();
            for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
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

        public void handleSpecialHandle(Trip tripDepart,Trip tripReturn, Row nextRow){
            try {
                if (nextRow.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    tripDepart.setTripNo((int) nextRow.getCell(0).getNumericCellValue());
                    tripReturn.setTripNo((int) nextRow.getCell(0).getNumericCellValue());
                }
                if (nextRow.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    double cellvalue = nextRow.getCell(1).getNumericCellValue();
                    tripDepart.setStartTime(DateUtil.getJavaDate(cellvalue));
                    tripDepart.setEndTime(DateUtil.getJavaDate(cellvalue + 0.041666666666666664));
                }
                if (nextRow.getCell(5).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    double cellvalue = nextRow.getCell(5).getNumericCellValue();
                    tripReturn.setStartTime(DateUtil.getJavaDate(cellvalue));
                    tripReturn.setEndTime(DateUtil.getJavaDate(cellvalue + 0.041666666666666664));
                }

            } catch (Exception ex) {
                System.out.println("Skip Error Row at " +nextRow.getRowNum());
                ex.printStackTrace();
            }
        }

        private void addDataToTrip(Trip tripDepartTime, Trip tripReturnTime, Cell cell) {

            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    int indexColumn = cell.getColumnIndex();
                    switch (indexColumn) {
                        case 0:
                            tripDepartTime.setTripNo((int) cell.getNumericCellValue());
                            tripReturnTime.setTripNo((int) cell.getNumericCellValue());
                        case 1:
                            tripDepartTime.setStartTime(cell.getDateCellValue());
                            break;
                        case 2:
                            tripDepartTime.setEndTime(cell.getDateCellValue());
                            break;
                        case 5:
                            tripReturnTime.setStartTime(cell.getDateCellValue());
                            break;
                        case 6:
                            tripReturnTime.setEndTime(cell.getDateCellValue());
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

