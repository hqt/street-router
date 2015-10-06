package com.fpt.router.crawler.work;

import com.fpt.router.crawler.model.entity.*;
import com.fpt.router.crawler.model.helper.Location;
import com.fpt.router.crawler.model.helper.RouteType;
import com.fpt.router.crawler.utils.DistanceUtils;
import com.fpt.router.crawler.utils.StringUtils;
import com.fpt.router.crawler.utils.TimeUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.LocalTime;
import org.joda.time.Period;

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
    public TimeUtils timeUtils = new TimeUtils();
    public DistanceUtils distanceUtils = new DistanceUtils();
    public static final String pathFolder = "\\localExcelFIle";

    public ReadExcelFileFromLocal(){
        map = new CityMap();
        excelPaths = new HashMap<Integer, String>();
    }

    public ReadExcelFileFromLocal(CityMap map) {
        this.map = map;
    }

    public CityMap run(){
        File file = getFolderExcelPaths();
        processParsing(file);
        buildConnections();
        return map;
    }

    public void buildConnections() {

        for (Route r: map.getRoutes()){

            if (r.getRouteNo() == 613) continue;
            if (r.getRouteNo() == 75) continue;

            double totalDistance = DistanceUtils.distance(r);

            // find distance of pathInfo
            List<Double> pathInfoDistances = new ArrayList<Double>();
            for (PathInfo pathInfo : r.getPathInfos()) {
                if (pathInfo.getTo() == null) continue;
                Location startLocation = new Location(pathInfo.getFrom().getLatitude(), pathInfo.getFrom().getLongitude());
                Location endLocation = new Location(pathInfo.getTo().getLatitude(), pathInfo.getTo().getLongitude());
                double pathInfoDistance = DistanceUtils.distanceTwoLocation(startLocation, endLocation,
                        StringUtils.convertToLocations(pathInfo.getMiddleLocations()));
                pathInfoDistances.add(pathInfoDistance);
            }

            // create connections for each trip
            for (Trip trip : r.getTrips()) {
                List<Connection> connections = new ArrayList<Connection>();

                // count total time for traveling whole trip
                if ((trip == null) || (trip.getStartTime() == null) || (trip.getEndTime() == null)) {
                    int a = 3;
                }

                Period totalTravelTime = Period.fieldDifference(trip.getStartTime(), trip.getEndTime());
                long totalMillis = TimeUtils.convertToMilliseconds(totalTravelTime);

                // for each pathInfo. Create one connection base on PathInfo length
                for (int i = 0; i < pathInfoDistances.size(); i++) {
                    // time for this pathInfo
                    long pathInfoTravel = (long) (totalMillis * pathInfoDistances.get(i) / totalDistance);
                    Period pathInfoTravelPeriod = new Period(pathInfoTravel);

                    // create connection.
                    // Base on our business, previous bus departure time == next bus arrival time
                    Connection connection = new Connection();
                    connection.setTrip(trip);
                    if (i == 0) {
                        connection.setArrivalTime(trip.getStartTime());
                    } else {
                        connection.setArrivalTime(connections.get(i-1).getDepartureTime());
                    }
                    if (i == (pathInfoDistances.size()-1)) {
                        connection.setDepartureTime(trip.getEndTime());
                    } else {

                        LocalTime departureTime = connection.getArrivalTime().plus(pathInfoTravelPeriod);
                        connection.setDepartureTime(departureTime);
                    }

                    connections.add(connection);
                    trip.setConnections(connections);
                }
            }
        }
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
        boolean isgo;

        public ParseExcels(String path, int busid) {
            this.path = path;
            this.busid = busid;
        }

        @Override
        public void run() {
            InputStream in = getExcelFileFromLocal(path);
            parseExcelFile(in);
        }

        private void parseExcelFile(InputStream in) {

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
            routeDepart.setRouteType(RouteType.DEPART);
            Route routeReturn = new Route();
            routeReturn.setRouteType(RouteType.RETURN);

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
                        tripDepart.setStartTime(timeUtils.convertExcelDate(nextRow.getCell(departIndex).getDateCellValue()));
                        tripDepart.setEndTime(timeUtils.convertExcelDate(nextRow.getCell(departIndex + 1).getDateCellValue()));
                        tripReturn.setStartTime(timeUtils.convertExcelDate(nextRow.getCell(returnIndex).getDateCellValue()));
                        tripReturn.setEndTime(timeUtils.convertExcelDate(nextRow.getCell(returnIndex + 1).getDateCellValue()));
                    }
                } catch (Exception ex) {
                    //ex.printStackTrace();
                    //System.out.println("Row Error: " + nextRow.getRowNum() + " at " + i + " - " +excelLink);
                    continue;
                }


                for (Route r : map.getRoutes()) {
                    // add trip to route
                    if (r.getRouteNo() == busid) {

                        // add trip to route
                        if(tripDepart.getTripNo() != 0) {
                            if (r.getRouteType() == RouteType.DEPART) {
                                r.getTrips().add(tripDepart);
                            }
                            if (r.getRouteType() == RouteType.RETURN) {
                                r.getTrips().add(tripReturn);
                            }
                        }
                    }
                }
            }
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
