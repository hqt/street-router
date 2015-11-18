package com.fpt.router.web.action.staff.parser;

import com.fpt.router.artifacter.model.entity.CityMap;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Trip;
import com.fpt.router.artifacter.utils.TimeUtils;
import com.fpt.router.web.action.util.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by datnt on 11/10/2015.
 */
public class ParseExcelLocal {

    public CityMap map;
    public File folderExcel;

    public ParseExcelLocal(CityMap map, File folderExcel) {
        this.folderExcel = folderExcel;
        this.map = map;
    }

    public void parseExcelFromLocal() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        String folderExcelPath = folderExcel.getAbsolutePath();
        FileUtils fileUtils = new FileUtils();
        for (int i = 0 ; i < map.getRoutes().size(); i++) {
            String filePath = folderExcelPath + "\\" + fileUtils.buildExcelFileName(map.getRoutes().get(i));
            File file = new File(filePath);
            if (file.exists()) {
                ParseExcelThread parseExcelThread = new ParseExcelThread(map.getRoutes().get(i), file);
                executorService.execute(parseExcelThread);
            }
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            System.out.println("############################");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public CityMap run() {
        parseExcelFromLocal();
        return map;
    }

    // process thread for parsing excel
    private class ParseExcelThread extends Thread {

        private Route route;
        private File file;

        public ParseExcelThread(Route route, File file) {
            this.route = route;
            this.file = file;
        }

        @Override
        public void run() {
           readExcel(file, route);
        }

        private void readExcel(File file, Route route) {
            try {
                InputStream in = new FileInputStream(file);
                Workbook workbook = new HSSFWorkbook(in);

                Sheet sheet = workbook.getSheetAt(0);
                TimeUtils timeUtils = new TimeUtils();
                for (int i = 1; i < sheet.getLastRowNum(); i++) {
                    // Initialize Trip
                    Trip trip = new Trip();
                    // Add value to trip
                    Row nextRow = sheet.getRow(i);
                    try {
                        trip.setTripNo((int) nextRow.getCell(0).getNumericCellValue());
                        trip.setStartTime(timeUtils.convertExcelDate(nextRow.getCell(1).getDateCellValue()));
                        trip.setEndTime(timeUtils.convertExcelDate(nextRow.getCell(2).getDateCellValue()));
                    } catch (Exception ex) {
                        System.out.println("Wrong Excel Template");
                        continue;
                    }
                    trip.setRoute(route);
                    route.getTrips().add(trip);
                }
            } catch (Exception e) {
                System.out.println("Route No: " + route.getRouteNo());
                e.printStackTrace();
            }
        }
    }

}
