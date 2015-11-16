package com.fpt.router.web.action.staff.parser;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.model.entity.PathInfo;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Station;
import com.fpt.router.artifacter.model.helper.RouteType;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;
import com.sun.corba.se.spi.orbutil.fsm.Input;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by datnt on 11/8/2015.
 */
public class ParseFileAction implements IAction {

    @Override
    public String execute(ApplicationContext context) {

        System.out.println("Parsing...");
        Part filePart = context.getPart("file");

        if (filePart != null) {
            InputStream in = null;
            try {
                in = filePart.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (in != null) {
                readFile(in);
            }
        }

        return Config.AJAX_FORMAT;
    }

    private void readFile(InputStream in) {
        try {
            if (in == null) System.out.println("Link contain null inputstream");

            ObjectMapper om = new ObjectMapper();
            JsonNode rootNode = om.readTree(in);
            JsonNode table = rootNode.path("TABLE");

            if (table.get(0) == null) return;

            JsonNode row = table.get(0);
            Iterator<JsonNode> cols = row.getElements();

            int y = 0;
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

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extractData(int y, String data, Station station, PathInfo pathInfo) {
        System.out.println(data);
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
