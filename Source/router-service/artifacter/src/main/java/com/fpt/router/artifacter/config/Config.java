package com.fpt.router.artifacter.config;

import org.joda.time.LocalTime;

import java.util.HashSet;
import java.util.Set;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/5/15.
 */
public class Config {
    // JDBC driver name and database URL
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_MAX_URL = "jdbc:mysql://localhost:8889/BusDBMax?useUnicode=true&characterEncoding=UTF-8";
    public static final String DB_MIDDLE_URL = "jdbc:mysql://localhost:8889/BusDBMiddle?useUnicode=true&characterEncoding=UTF-8";
    public static final String DB_OFFICIAL_URL = "jdbc:mysql://localhost:8889/RouterDB?useUnicode=true&characterEncoding=UTF-8";
    public static final String DB_URL = DB_OFFICIAL_URL;

    public static final String AJAX_FORMAT = "ajax_format";

    public static final boolean SHOULD_LOAD_DB = true;

    public static LocalTime MAXIMUM_TIME = new LocalTime(22, 30);

    //  Database credentials
    public static final String USER = "root";
    public static final String PASS = "root";

    public static final double EPS = 0.00001;

    public static final class NearBusStationLimit {
        public static final int HIGH = 8;
        public static final int MEDIUM = 3;
        public static final int LOW = 3;
    }

    public static final int BUS_RESULT_LIMIT = 6;

    public static final double WALKING_BUS_DISTANCE = 300;
    public static final double WALKING_DISTANCE = 600;

    private static final double BUS_SPEED_KM_H = 20.0;
    private static final double HUMAN_SPEED_KM_H = 5.0;
    public static final double BUS_SPEED_M_S = BUS_SPEED_KM_H * 1000 / 3600;
    public static final double HUMAN_SPEED_M_S = HUMAN_SPEED_KM_H * 1000 / 3600;

    public static final class NoteData {
        int STATION_COUNT = 4126;
        int ROUTE_COUNT = 129;
        int MIDDLEPATH_COUNT = 5062;    // same with PathInfos
        int BUSORDER_COUNT = 10700;
        // int WALKING_EDGE = 31589;
        int WALKING_EDGE = 12767;
    }

    public static class WEB {
        public static final String SESSION_USER = "USER";
        public static final int ITEM_PER_PAGE = 10;
        public static final String PAGE = "/WEB-INF/page";
        public static final String REDIRECT = "redirect.";
        public static final String DIRECT_PAGE_ATTRIBUTE = "direct_page_attribute";
    }

    public static class CODE {
        public static final String SUCCESS = "success";
        public static final String FAIL = "fail";
    }

    // bug from those route. should be avoid
    public static Set<Integer> blockRoute = new HashSet<Integer>();

    // using for debugging purpose
    public static Set<Integer> allowRoute = new HashSet<Integer>();

    static {
        blockRoute.add(26);
        blockRoute.add(613);
        blockRoute.add(75);
        blockRoute.add(80);
        blockRoute.add(82);

        allowRoute.add(3);
        allowRoute.add(20);
        allowRoute.add(72);
        allowRoute.add(18);
    }

}
