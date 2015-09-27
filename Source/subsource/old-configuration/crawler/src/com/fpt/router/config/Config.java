package com.fpt.router.config;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/5/15.
 */
public class Config {
    // JDBC driver name and database URL
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_MAX_URL = "jdbc:mysql://localhost:8889/BusDBMax?useUnicode=true&characterEncoding=UTF-8";
    public static final String DB_MIDDLE_URL = "jdbc:mysql://localhost:8889/BusDBMiddle?useUnicode=true&characterEncoding=UTF-8";
    public static final String DB_OFFICIAL_URL = "jdbc:mysql://localhost:8889/BusDB?useUnicode=true&characterEncoding=UTF-8";
    public static final String DB_URL = DB_OFFICIAL_URL;

    public static final String AJAX_FORMAT = "ajax_format";

    public static final boolean SHOULD_LOAD_DB = true;

    //  Database credentials
    public static final String USER = "root";
    public static final String PASS = "root";

    public static double EPS = 0.00001;

    public static final double WALKING_BUS_DISTANCE = 300;
    public static final double WALKING_DISTANCE = 550;

    public static final double WALKING_SPEED = 1.4 * 60;
    public static final double NORMAL_BUS_SPEED = 1.0 * 16 / 60 * 1000;
    public static final double SLOW_BUS_SPEED = 30;

    public static final class NoteData {
        int STATION_COUNT = 4126;
        int ROUTE_COUNT = 129;
        int MIDDLEPATH_COUNT = 5062;    // same with PathInfos
        int BUSORDER_COUNT = 10700;
        // int WALKING_EDGE = 31589;
        int WALKING_EDGE = 12767;
    }

}
