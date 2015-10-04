package com.fpt.router.crawler.work;

import com.fpt.router.crawler.model.entity.CityMap;
import com.fpt.router.crawler.model.entity.PathInfo;
import com.fpt.router.crawler.model.entity.Route;
import com.fpt.router.crawler.model.entity.Station;
import com.fpt.router.crawler.model.helper.RouteType;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/6/15.
 */
public class BusCrawlerPipe {

    List<Integer> serverBusIds = new ArrayList<Integer>();
    Map<Integer, String> links = new HashMap<Integer, String>();
    List entryLink = new ArrayList();
    Pattern p = Pattern.compile("^\\[(\\d+)(-(\\d+))*\\]\\s+(.+)\\s*"); //[8-13] ben thanh cho hiep thanh
    public static final String busMapLink = "http://mapbus.ebms.vn/ajax.aspx?action=listRouteStations";
    public CityMap map;
    public Set<PathInfo> pathInfos = new HashSet<PathInfo>();
    public Set<Station> stations = new HashSet<Station>();
    public List<Route> routes = new ArrayList<Route>();
    public List<EntryLInk> listEntryLink = new ArrayList<EntryLInk>();

    public BusCrawlerPipe(){
        map = new CityMap();
    }

    public class EntryLInk {
        int serverBusId;
        int busCode;
        String busName;

        public EntryLInk(int id, int busCode, String name){
            this.serverBusId = id;
            this.busCode = busCode;
            this.busName = name;
        }
    }

    public void crawlAllLink() {
        System.out.println("Get all link ...");
        Document doc;
        try {
            doc = Jsoup.connect("http://mapbus.ebms.vn/routeoftrunk.aspx").get();
            Element listRoutes = doc.select("table tr td select").first();
            int limit = 0;
            for (Element route : listRoutes.select("option")) {
                limit++;
                // if (limit > 41) break;
                limit++;
                int serverBusId = Integer.parseInt(route.attr("value"));
                // if ((serverBusId != 16) && (serverBusId != 51)) continue;
                serverBusIds.add(serverBusId);
                // debug information
                Matcher m = p.matcher(route.text());
                if (m.matches()) {
                    String busCodeStr = m.group(1);
                    if (m.group(3) != null) busCodeStr += m.group(3);
                    int busCode = Integer.parseInt(busCodeStr);
                    String name = m.group(4);
                    System.out.println("server code: " + serverBusId + "--> " + busCode + " " + name);
                    EntryLInk entryLInk = new EntryLInk(serverBusId,busCode,name);
                    listEntryLink.add(entryLInk);
                } else {
                    System.out.println("wrong parsing. should review here !!!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("total bus routes: " + serverBusIds.size());
        System.out.println("#####################################");
    }

    public void crawAllData() {
        System.out.println("get json data from each route code ...");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for(EntryLInk entry : listEntryLink){
            CrawDataThread threadTrue = new CrawDataThread(entry.serverBusId, true, entry.busCode, entry.busName);
            CrawDataThread threadFalse = new CrawDataThread(entry.serverBusId, false, entry.busCode, entry.busName);
            executor.execute(threadTrue);
            executor.execute(threadFalse);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            System.out.println("############################");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getRoutes(int id, String name) {
        Route route = new Route();
        route.setRouteNo(id);
        route.setRouteName(name);

        map.getRoutes().add(route);
    }


    public CityMap run() {
        crawlAllLink();
        crawAllData();
        map.getStations().addAll(stations);
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

        @Override
        public void run() {
            InputStream in = readJsonFromURl(busId, isgo);
            getDataFromJson(in);
        }

        private InputStream readJsonFromURl(int busId, boolean isgo){
            InputStream in = null;
            try {
                String link = busMapLink + "&rid=" + busId + "&isgo=" + isgo;
                URL url = new URL(link);
                // Set connection timeout
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                // Open stream
                in = new BufferedInputStream(url.openStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return in;
        }

        private void getRoutes(int routeNo , String routeName, boolean isgo){
            Route route = new Route();
            route.setRouteNo(routeNo);
            route.setRouteName(routeName);
            if (isgo == true) {
                route.setRouteType(RouteType.DEPART);
            } else {
                route.setRouteType(RouteType.RETURN);
            }
            map.getRoutes().add(route);
        }

        private Set<Station> getDataFromJson(InputStream in) {
            try {
                if (in == null) System.out.println("Link contain null inputstream " + busId + " " +isgo);

                ObjectMapper om = new ObjectMapper();
                JsonNode rootNode = om.readTree(in);
                JsonNode table = rootNode.path("TABLE");

                if (table.get(0) == null) return stations;

                // get routes
                getRoutes(busNo,name,isgo);

                JsonNode row = table.get(0);
                Iterator<JsonNode> cols = row.getElements();

                int y = 0;
                int i = 0;
                while (cols.hasNext()) {
                    JsonNode col = cols.next();
                    Iterator<JsonNode> items = col.getElements();
                    while (items.hasNext()) {
                        JsonNode item = items.next();
                        Iterator<JsonNode> datas = item.getElements();
                        Station s = new Station();
                        while (datas.hasNext()) {
                            JsonNode data = datas.next();
                            Iterator<JsonNode> textValue = data.getElements();
                            while (textValue.hasNext()) {
                                JsonNode dataElement = textValue.next();
                                JsonNode dataTextValue = dataElement.path("DATA");
                                extractData(y, dataTextValue.getTextValue(), s);
                                y++;
                            }
                        }

                        s.setStationId(i);
                        stations.add(s);
                        y = 0;
                        i++;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stations;
        }

        public void extractData(int y, String data, Station station) {
            switch (y) {
                case 3:
                    PathInfo pathInfo = new PathInfo();
                    pathInfo.setMiddleLocations(data);
                    pathInfos.add(pathInfo);
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
