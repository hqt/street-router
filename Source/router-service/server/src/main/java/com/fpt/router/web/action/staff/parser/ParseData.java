package com.fpt.router.web.action.staff.parser;

import com.fpt.router.artifacter.dao.StationDAO;
import com.fpt.router.artifacter.model.entity.*;
import com.fpt.router.artifacter.model.helper.Location;
import com.fpt.router.artifacter.model.helper.RouteType;
import com.fpt.router.artifacter.utils.DistanceUtils;
import com.fpt.router.artifacter.utils.StringUtils;
import com.fpt.router.artifacter.utils.TimeUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by datnt on 11/9/2015.
 */
public class ParseData {

    public void readJson(InputStream in, Route route) {
        try {
            if (in == null) System.out.println("Some Thing Route in Process Parsing Json....");

            ObjectMapper om = new ObjectMapper();
            JsonNode rootNode = om.readTree(in);
            JsonNode table = rootNode.path("TABLE");

            if (table.get(0) == null) return;

            JsonNode row = table.get(0);
            Iterator<JsonNode> cols = row.getElements();

            int y = 0;
            int i = 0;
            StationDAO stationDAO = new StationDAO();
            List<PathInfo> pathInfoList = new ArrayList<>();
            while (cols.hasNext()) {
                JsonNode col = cols.next();
                Iterator<JsonNode> items = col.getElements();
                while (items.hasNext()) {
                    JsonNode item = items.next();
                    Iterator<JsonNode> datas = item.getElements();
                    Station s = new Station();
                    PathInfo pathInfo = new PathInfo();
                    while (datas.hasNext()) {
                        JsonNode data = datas.next();
                        Iterator<JsonNode> textValue = data.getElements();
                        while (textValue.hasNext()) {
                            JsonNode dataElement = textValue.next();
                            JsonNode dataTextValue = dataElement.path("DATA");
                            extractData(y, dataTextValue.getTextValue(), s, pathInfo);
                            y++;
                        }
                    }

                    // add station to route
                    Station existStation = null;
                    if (s.getCodeId() != null && !s.getCodeId().isEmpty()) {
                        existStation = stationDAO.findStationByCodeID(s.getCodeId());
                    }
                    if (existStation != null) {
                        pathInfo.setFrom(s);
                    }
                    pathInfo.setRoute(route);
                    pathInfoList.add(pathInfo);

                    route.getPathInfos().add(pathInfo);

                    if (i > 0) {
                        if (pathInfoList.get(i - 1).getPathInfoId() == pathInfoList.get(i).getPathInfoId()) {
                            pathInfoList.get(i - 1).setTo(s);
                        }
                    }

                    y = 0;
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extractData(int y, String data, Station station, PathInfo pathInfo) {
        switch (y) {
            case 3:
                pathInfo.setMiddleLocations(data);
                break;
            case 5:
                int no = -1;
                try {
                    no = Integer.parseInt(data);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
                pathInfo.setPathInfoNo(no);
                break;
            case 7:
                station.setName(data);
                break;
            case 8:
                double lo = Double.parseDouble(data);
                station.setLongitude(lo);
                break;
            case 9:
                double lat = Double.parseDouble(data);
                station.setLatitude(lat);
                break;
            case 12:
                station.setStreet(data);
                break;
            case 13:
                station.setCodeId(data);
                break;
        }
    }

    public void readExcel(InputStream in, Route route) {

        Workbook workbook = null;
        try {
            workbook = new HSSFWorkbook(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Sheet sheet = workbook.getSheetAt(0);
        TimeUtils timeUtils = new TimeUtils();
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            // Initialize Trip
            Trip trip = new Trip();
            // Add value to trip
            Row nextRow = sheet.getRow(1);
            try {
                trip.setTripNo((int) nextRow.getCell(0).getNumericCellValue());
                trip.setStartTime(timeUtils.convertExcelDate(nextRow.getCell(1).getDateCellValue()));
                trip.setEndTime(timeUtils.convertExcelDate(nextRow.getCell(2).getDateCellValue()));
            } catch (Exception ex) {
                continue;
            }
            trip.setRoute(route);
            route.getTrips().add(trip);
        }
    }

    public void buildConnections(Route route) {

        double totalDistance = DistanceUtils.distance(route);

        // find segmentDistance of pathInfo
        List<Double> pathInfoDistances = new ArrayList<Double>();
        for (PathInfo pathInfo : route.getPathInfos()) {
            // this connection is zero-length
            if (pathInfo.getTo() == null) {
                pathInfoDistances.add(0.0);
                continue;
            }
            Location startLocation = new Location(pathInfo.getFrom().getLatitude(), pathInfo.getFrom().getLongitude());
            Location endLocation = new Location(pathInfo.getTo().getLatitude(), pathInfo.getTo().getLongitude());
            double pathInfoDistance = DistanceUtils.distanceTwoLocation(startLocation, endLocation,
                    StringUtils.convertToLocations(pathInfo.getMiddleLocations()));
            pathInfoDistances.add(pathInfoDistance);
        }

        // create connections for each trip
        for (Trip trip : route.getTrips()) {
            List<Connection> connections = new ArrayList<Connection>();

            // count total totalTime for traveling whole trip
            if ((trip == null) || (trip.getStartTime() == null) || (trip.getEndTime() == null)) {
                int a = 3;
            }

            Period totalTravelTime = Period.fieldDifference(trip.getStartTime(), trip.getEndTime());
            long totalMillis = TimeUtils.convertToMilliseconds(totalTravelTime);

            // for each pathInfo. Create one connection base on PathInfo length
            for (int i = 0; i < pathInfoDistances.size(); i++) {
                // totalTime for this pathInfo
                long pathInfoTravel = (long) (totalMillis * pathInfoDistances.get(i) / totalDistance);
                Period pathInfoTravelPeriod = new Period(pathInfoTravel);

                // create connection.
                // Base on our business, previous bus departure totalTime == next bus arrival totalTime
                Connection connection = new Connection();
                connection.setTrip(trip);
                connection.setPathInfo(route.getPathInfos().get(i));
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
