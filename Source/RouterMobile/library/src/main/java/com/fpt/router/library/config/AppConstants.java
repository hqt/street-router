package com.fpt.router.library.config;

/**
 * Created by Huynh Quang Thao on 10/13/15.
 */
public class AppConstants {

    public static String SERVER_ADDRESS = "http://192.168.1.1:8080";

    private static final String GOOGLE_KEY_1 = "AIzaSyBkY1ok25IxoD6nRl_hunFAtTbh1EOss5A";
    private static final String GOOGLE_KEY_2 = "AIzaSyDfRreMPCuDwSRaZdrnk64Ou_4YV-pTheQ";
    private static final String GOOGLE_KEY_3 = "AIzaSyDQAnDaneVMCW_k_yxhUCZp6EIbw0PLb5A";
    private static final String GOOGLE_KEY_4 = "AIzaSyCRk-SLZE8HAJe_p67z-fxKgYfZzDKMyj0";
    private static final String GOOGLE_KEY_5 = "AIzaSyAdLWUhmoL7awJylFZRmrQv8-CEkjLZaT8";
    private static final String GOOGLE_KEY_6 = "AIzaSyD28mTMy7swcMpn1WRRrA4Rz8B-n7dbZZM";
    public static final String GOOGLE_KEY = GOOGLE_KEY_3;

    public static final class PATH {
        private static final String MESSAGE_PATH = "/com/fpt/router/wear";
        public static final String MESSAGE_PATH_FOUR_POINT = MESSAGE_PATH + "/fourpoint";
        public static final String MESSAGE_PATH_TWO_POINT = MESSAGE_PATH + "/twopoint";
        public static final String MESSAGE_PATH_LEG = MESSAGE_PATH + "/leg";
        public static final String MESSAGE_PATH_GPS = MESSAGE_PATH + "/gps";
    }

    public static final class Vibrator {
        public static final int DELAY_VIBRATE = 500;
        public static final int OFF_VIBRATE = 1000;
        public static final int ON_VIBRATE = 2000;
    }

    public static final class API {
        public static String SEARCH_BUS_ROUTE = SERVER_ADDRESS + "/api/twopoint";
        public static String SEARCH_BUS_ROUTE_FOUR_POINT = SERVER_ADDRESS + "/search/multi";
    }

    public static final class SearchField {
        public static final int FROM_LOCATION = 1;
        public static final int TO_LOCATION = 2;
        public static final int WAY_POINT_1 = 3;
        public static final int WAY_POINT_2 = 4;
    }

    public static final class GoogleApiCode {
        public static final String OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
        public static final String NOT_FOUND = "NOT_FOUND";
        public static final String NO_RESULT = "ZERO_RESULTS";
    }

    public static void buildAPILink() {
        API.SEARCH_BUS_ROUTE = SERVER_ADDRESS + "/api/twopoint";
        API.SEARCH_BUS_ROUTE_FOUR_POINT = SERVER_ADDRESS + "/search/multi";
    }
}
