package com.fpt.router.crawler.work;

import com.fpt.router.crawler.model.entity.CityMap;
import com.fpt.router.crawler.model.entity.Route;
import com.fpt.router.crawler.model.entity.Trip;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by datnt on 9/18/2015.
 */
public class ReadExcelFileFromLocal {

    public CityMap map;
    public Map<Integer, String> excelPaths;
    public static final String pathFolder = "\\localExcelFIle";

    public ReadExcelFileFromLocal(){
        map = new CityMap();
        excelPaths = new HashMap<Integer, String>();
    }

    public CityMap run(){
        File file = getFolderExcelPaths();
        processParsing(file);
        return map;
    }

    public File getFolderExcelPaths() {
        // Get Directory contain excel files
        String currentDir = System.getProperty("user.dir");
        CharSequence backFolder = "crawler";
        String folderPath = currentDir.replace(backFolder, "") + pathFolder;

        File folder = new File(folderPath);
        return folder;
    }


    public InputStream getExcelFileFromLocal(String path) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            if (inputStream == null) {
                inputStream.reset();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }


    private void processParsing(File file) {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                String fileName  = f.getName();
                CharSequence extension = ".xlsx";
                if (!fileName.contains(extension)) {
                    extension = ".xls";
                }
                String id = fileName.replace(extension, "");

                ParseExcels parseExcels = new ParseExcels(f.getAbsolutePath(), Integer.parseInt(id));
                executorService.execute(parseExcels);
            }
        }
        // wait for all thread finis
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class ParseExcels extends Thread {

        String path;
        int busid;

        public ParseExcels(String path, int busid) {
            this.path = path;
            this.busid = busid;
        }

        @Override
        public void run() {
            InputStream in = getExcelFileFromLocal(path);
            List<Route> routes = parseExcelFile(in);
            for (Route route: routes) {
                map.getRoutes().add(route);
            }
        }

        private List<Route> parseExcelFile(InputStream in) {

            Workbook workbook = null;
            try {
                CharSequence extension = ".xlsx";
                if (path.contains(extension)){
                    workbook = new XSSFWorkbook(in);
                } else {
                    workbook = new HSSFWorkbook(in);
                }
            } catch (Exception e) {
                System.out.println("Excel wrong: " + path);
                e.printStackTrace();
            }
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.rowIterator(); // get iterator row

            List<Route> routes = new ArrayList<Route>();
            Route routeDepart = new Route();
            routeDepart.setRouteType(Route.RouteType.DEPART);
            Route routeReturn = new Route();
            routeReturn.setRouteType(Route.RouteType.RETURN);

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
                        tripDepart.setStartTime(nextRow.getCell(departIndex).getDateCellValue());
                        tripDepart.setEndTime(nextRow.getCell(departIndex + 1).getDateCellValue());
                        tripReturn.setStartTime(nextRow.getCell(returnIndex).getDateCellValue());
                        tripReturn.setEndTime(nextRow.getCell(returnIndex + 1).getDateCellValue());
                    }
                } catch (Exception ex) {
                    //System.out.println("Row Error: " + nextRow.getRowNum() + " at " + i + " - " +excelLink);
                    continue;
                }
                if (tripDepart.getTripNo() != 0) {
                    routeDepart.getTrips().add(tripDepart);
                    routeDepart.setRouteId(busid);
                    routeReturn.getTrips().add(tripReturn);
                    routeReturn.setRouteId(busid);
                }
            }

            routes.add(routeDepart);
            routes.add(routeReturn);
            return routes;
        }

        public List<Integer> getCellIndex(Row nextRow) {

            List<Integer> index = new ArrayList<Integer>();
            int indexStartTime = -1;
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
                   // System.out.println("Row: " +nextRow.getRowNum() + " i " + " link: " +excelPaths);
                    continue;
//                    ex.printStackTrace();
                }
            }

            return index;
        }
    }

}
