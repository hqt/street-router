package com.fpt.router.web.action.util;

import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.helper.RouteType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by datnt on 11/10/2015.
 */
public class FileUtils {


    public int routeNo;
    public RouteType routeType;
    public String fileType;
    public String excelFileName;

    public boolean deductString(String fileName) {
        boolean done = true;

        String regexJsonFile = "^(\\d+)-(\\w+).(json)";
        Pattern p = Pattern.compile(regexJsonFile);

        String routeNoStr = "";
        String routeTypeStr = "";
        String fileTypeStr = "";

        Matcher m = p.matcher(fileName);
        if (m.find()) {
            routeNoStr = m.group(1);
            routeTypeStr = m.group(2).toLowerCase();
            fileTypeStr = m.group(3).toLowerCase();
        } else {
            done = false;
            System.out.println("File not match");
        }

        int routeNo = -1;
        try {
            routeNo = Integer.parseInt(routeNoStr);
        } catch (NumberFormatException ex){
            System.out.println("Cannot cast route no from file name to integer");
            done = false;
        }
        if (routeNo != -1) {
            this.routeNo = routeNo;
        }

        if (routeTypeStr.equals("depart")) {
            this.routeType = RouteType.DEPART;
        } else if (routeTypeStr.equals("return")) {
            this.routeType = RouteType.RETURN;
        } else {
            System.out.println("wrong route type");
            done = false;
        }

        if (fileTypeStr.equals("json") || fileTypeStr.equals(".xls")) {
            this.fileType = fileTypeStr;
        } else {
            System.out.println("wrong file type");
            done = false;
        }

        return done;
    }

    public String buildExcelFileName(Route route) {
        String routeType = route.getRouteType().toString();
        return excelFileName = route.getRouteNo() + "-" + routeType.toLowerCase() + ".xls";
    }
}
