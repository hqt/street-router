package com.fpt.router.work;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    Pattern p = Pattern.compile("^\\[(\\d+)(-(\\d+))*\\]\\s+(.+)\\s*"); //[8-13] ben thanh cho hiep thanh

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
                } else {
                    System.out.println("wrong parsing. should review here !!!");;
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
        ExecutorService executor = Executors.newFixedThreadPool(20);
        for (Integer serverBusId : serverBusIds) {

        }

        for (Map.Entry<Integer, BusRouteCrawler> entry : map.entrySet()) {
            BusRouteCrawler route = entry.getValue();
            CrawDataThread thread = new CrawDataThread(route);
            executor.execute(thread);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            System.out.println("crawling data finished. Remain routes: " + map.size());
            System.out.println("############################");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        crawlAllLink();
    }

    private class CrawDataThread extends Thread {

        public CrawDataThread(int serverRouteId) {
        }

        @Override
        public void run() {
            boolean res = run(true);
            if (res) run(false);
        }

        public boolean run(boolean turn) {
            HtmlPage html;
            try {
                WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
                String url = "http://mapbus.ebms.vn/ajax.aspx?action=listRouteStations&rid=" + route.getServerId() + "&isgo=" + turn;
                html = webClient.getPage(url);
                String content = html.getBody().getTextContent();
                if ((content != null) && (!content.equals("null"))) {
                    JSONObject o = new JSONObject(content);
                    String xml = XML.toString(o);
                    if (turn) {
                        route.setXmlDataRight(StringHelper.wrapXMLDocument(xml, "original_schema.xsd"));
                    } else {
                        route.setXmlDataLeft(StringHelper.wrapXMLDocument(xml, "original_schema.xsd"));
                    }
                    return true;
                } else {
                    System.out.println("this route " + route.getRouteId() + " doesn't return data. will be removed from system");
                    map.remove(route.getServerId());
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("error converting at " + route.getServerId());
            }
            return false;
        }
    }
}
