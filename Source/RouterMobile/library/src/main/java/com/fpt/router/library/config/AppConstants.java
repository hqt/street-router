package com.fpt.router.library.config;

/**
 * Created by Huynh Quang Thao on 10/13/15.
 */
public class AppConstants {
    private static final int SERVER_PORT = 8080;
    private static final String SERVER_IP = "http://192.168.137.42";
    public static final String SERVER_ADDRESS = SERVER_IP + ":" + SERVER_PORT;

    private static final String GOOGLE_KEY_1 = "AIzaSyBkY1ok25IxoD6nRl_hunFAtTbh1EOss5A";
    private static final String GOOGLE_KEY_2 = "AIzaSyDfRreMPCuDwSRaZdrnk64Ou_4YV-pTheQ";
    private static final String GOOGLE_KEY_3 = "AIzaSyDQAnDaneVMCW_k_yxhUCZp6EIbw0PLb5A";
    public static final String GOOGLE_KEY = GOOGLE_KEY_2;

    public static final class PATH {
        private static final String MESSAGE_PATH = "/com/fpt/router/wear";
        public static final String MESSAGE_PATH_FOUR_POINT = MESSAGE_PATH + "/fourpoint";
        public static final String MESSAGE_PATH_TWO_POINT = MESSAGE_PATH + "/twopoint";
        public static final String MESSAGE_PATH_LEG = MESSAGE_PATH + "/leg";
        public static final String MESSAGE_PATH_GPS = MESSAGE_PATH + "/gps";
    }

    public static final class API {
        public static final String SEARCH_BUS_ROUTE = SERVER_ADDRESS + "/api/twopoint";
    }
}
