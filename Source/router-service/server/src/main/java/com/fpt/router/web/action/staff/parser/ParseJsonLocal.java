package com.fpt.router.web.action.staff.parser;

import com.fpt.router.artifacter.model.entity.CityMap;
import com.fpt.router.artifacter.model.entity.PathInfo;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Station;
import com.fpt.router.artifacter.model.helper.RouteType;
import com.fpt.router.web.action.util.FileUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by datnt on 11/10/2015.
 */
public class ParseJsonLocal {

    public File folderJson;
    public Map<String, Station> stationMap = new HashMap<String, Station>();
    public CityMap map;
    public List<PathInfo> pathInfos = new ArrayList<PathInfo>();
    public String message = "Parse data from local complete";

    public int routeNo;
    public RouteType routeType;

    public ParseJsonLocal(File folderJson) {
        map = new CityMap();
        this.folderJson = folderJson;
    }

    public void parseJsonFromLocal() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // iterate folder
        if (this.folderJson.isDirectory()) {
            File[] jsonFiles = folderJson.listFiles();
            if (jsonFiles != null && jsonFiles.length > 0) {
                for (File file : jsonFiles) {
                    // deduct file name for retrieve route no, name and file type
                    FileUtils fileUtils = new FileUtils();
                    boolean oke = fileUtils.deductString(file.getName());
                    this.routeNo = fileUtils.routeNo;
                    this.routeType = fileUtils.routeType;
                    if (oke) {
                        // call thread for execute file
                        ParseJsonThread parseJsonThread = new ParseJsonThread(this.routeNo, this.routeType, file);
                        executorService.execute(parseJsonThread);
                    }
                }
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
        parseJsonFromLocal();

        // convert all to List
        List<Station> stations = new ArrayList<Station>(stationMap.values());

        map.setStations(stations);

        // set increment id station
        for(int i = 0; i < map.getStations().size(); i++){
            map.getStations().get(i).setStationId(i);
        }

        return map;
    }

    private class ParseJsonThread extends Thread {

        private int busNo;
        private RouteType routeType;
        private File file;

        public ParseJsonThread(int busNo, RouteType routeType, File file) {
            this.busNo = busNo;
            this.routeType = routeType;
            this.file = file;
        }

        @Override
        public void run() {
            getDataFromJson(file);
        }

        private Route createRoute(int routeNo, RouteType routeType) {
            Route route = new Route();
            route.setRouteNo(routeNo);
            route.setRouteType(routeType);
            return route;
        }

        private Map<String, Station> getDataFromJson(File file) {
            if (file == null) {
                return stationMap;
            }
            try {

                ObjectMapper om = new ObjectMapper();
                JsonNode rootNode = om.readTree(file);
                //JsonNode rootNode = om.readTree(in);
                JsonNode table = rootNode.path("TABLE");

                if (table.get(0) == null) {
                    return stationMap;
                }

                // get routes
                Route route = createRoute(busNo, routeType);

                JsonNode row = table.get(0);
                Iterator<JsonNode> cols = row.getElements();

                int y = 0;
                int i = 0;
                List<PathInfo> listPathInfo = new ArrayList<PathInfo>();
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
                                extractData(y, dataTextValue.getTextValue(), s, pathInfo, route);
                                y++;
                            }
                        }

                        if (!stationMap.containsKey(s.getCodeId())) {
                            stationMap.put(s.getCodeId(), s);
                        } else {
                            s = stationMap.get(s.getCodeId());
                        }

                        // set from & to station
                        pathInfo.setFrom(s);  // van set sb
                        pathInfo.setRoute(route);
                        listPathInfo.add(pathInfo);

                        route.getPathInfos().add(pathInfo);

                        if (i > 0) {
                            if (listPathInfo.get(i - 1).getPathInfoId() == listPathInfo.get(i).getPathInfoId()) {
                                listPathInfo.get(i - 1).setTo(s);
                            }
                        }

                        y = 0;
                        i++;
                    }
                }
                map.getRoutes().add(route);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stationMap;
        }

        public void extractData(int y, String data, Station station, PathInfo pathInfo, Route route) {
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
                case 11:
                    // check route name can be determine
                    String routeName = data.trim();
                    route.setRouteName(routeName);
                case 12:
                    station.setStreet(data);
                    break;
                case 13:
                    if (data.equals("QTDT073")) {
                        int a = 3;
                    }
                    station.setCodeId(data);
                    break;
            }
        }
    }



}
