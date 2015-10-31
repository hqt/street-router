package com.fpt.router.crawler.work;

import com.fpt.router.artifacter.model.entity.CityMap;
import com.fpt.router.artifacter.model.entity.PathInfo;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Station;
import com.fpt.router.artifacter.model.helper.RouteType;
import org.apache.commons.io.input.ReaderInputStream;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by datnt on 10/8/2015.
 */
public class ReadJsonFromLocal {

    private static String pathJsonFolder = "";
    private static String pathJsonLink = "";
    private static Map<Integer, String> links = new HashMap<Integer, String>();
    public Map<String, Station> stationMap = new HashMap<String, Station>();
    public CityMap map;
    public List<PathInfo> pathInfos = new ArrayList<PathInfo>();

    public void processCrawData() {

    }

    public ReadJsonFromLocal() {
        map = new CityMap();
    }

    public String getPathFolderJson() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("json").getFile());
        pathJsonFolder = file.getAbsolutePath();
        return pathJsonFolder;
    }

    public String getPathLinkJson() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("text").getFile());
        pathJsonLink = file.getAbsolutePath();
        return pathJsonLink;
    }

    public void crawLinkJson() {
        File f = new File(pathJsonLink + "/link.txt");

        if (f.exists()) {
            String line;
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
                int i = 1;
                while ((line = br.readLine()) != null) {
                    String[] split = line.split(";");
                    int busNo = Integer.parseInt(split[1]);
                    String routeName = split[2];
                    links.put(busNo, routeName);
                    i++;
                }
                System.out.println("Number of link: " + i);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }

    }

    public void crawJsonFromLocal() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        int limit = 0;
        for (Map.Entry<Integer, String> entry : links.entrySet()) {
            if (limit > 4) {
                break;
            }
            limit++;
            CrawDataThread crawDataThreadTrue = new CrawDataThread(entry.getKey(), true, entry.getValue());
            CrawDataThread crawDataThreadFalse = new CrawDataThread(entry.getKey(), false, entry.getValue());
            executorService.execute(crawDataThreadTrue);
            executorService.execute(crawDataThreadFalse);
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
        getPathLinkJson();
        getPathFolderJson();
        crawLinkJson();
        crawJsonFromLocal();

        // convert all to List
        List<Station> stations = new ArrayList<Station>(stationMap.values());
        map.setStations(stations);

        // set increment id station
        for(int i = 0; i < map.getStations().size(); i++){
            map.getStations().get(i).setStationId(i);
        }

        return map;
    }

    private class CrawDataThread extends Thread {

        private int busId;
        private boolean isgo;
        private int busNo;
        private String name;

        public CrawDataThread(int busId, boolean isgo, int busNo, String name) {
            this.busId = busId;
            this.isgo = isgo;
            this.busNo = busNo;
            this.name = name;
        }

        public CrawDataThread(int busNo, boolean isgo, String name) {
            this.busNo = busNo;
            this.isgo = isgo;
            this.name = name;
        }

        @Override
        public void run() {
            File f = readJsonFromLocal(busNo, isgo);
            getDataFromJson(f);
        }

        private File readJsonFromLocal(int busNo, boolean isgo) {

            // 1-false.json: correct name
            String linkLocal = pathJsonFolder + "/" + busNo + "-" + isgo + ".json";
            //System.out.println(linkLocal);
            File f = new File(linkLocal);

            if (f.exists()) {
                return f;
            }

            return null;
        }


        private Route getRoutes(int routeNo, String routeName, boolean isgo) {
            Route route = new Route();
            route.setRouteNo(routeNo);
            route.setRouteName(routeName);
            if (isgo == true) {
                route.setRouteType(RouteType.DEPART);
            } else {
                route.setRouteType(RouteType.RETURN);
            }
            map.getRoutes().add(route);
            return route;
        }

        private Map<String, Station> getDataFromJson(File file) {
            if (file == null) {
                System.out.println(file.getName());
                return stationMap;
            }
            try {
                //if (in == null) System.out.println("Link contain null inputstream " + busNo + " " + isgo);

                ObjectMapper om = new ObjectMapper();
                JsonNode rootNode = om.readTree(file);
                //JsonNode rootNode = om.readTree(in);
                JsonNode table = rootNode.path("TABLE");

                if (table.get(0) == null) {
                    System.out.println(file.getName());
                    return stationMap;
                }

                // get routes
                Route route = getRoutes(busNo, name, isgo);

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
                                extractData(y, dataTextValue.getTextValue(), s, pathInfo);
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
                pathInfos.addAll(listPathInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stationMap;
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
    }


}
