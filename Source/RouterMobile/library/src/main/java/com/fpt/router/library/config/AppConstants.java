package com.fpt.router.library.config;

/**
 * Created by Huynh Quang Thao on 10/13/15.
 */
public class AppConstants {

    public static String SERVER_ADDRESS = "http://192.168.1.1:8080";
    public static double EPS = 0.0001;

    private static final String GOOGLE_KEY_1 = "AIzaSyBkY1ok25IxoD6nRl_hunFAtTbh1EOss5A";
    private static final String GOOGLE_KEY_2 = "AIzaSyDfRreMPCuDwSRaZdrnk64Ou_4YV-pTheQ";
    private static final String GOOGLE_KEY_3 = "AIzaSyDQAnDaneVMCW_k_yxhUCZp6EIbw0PLb5A";
    private static final String GOOGLE_KEY_4 = "AIzaSyCRk-SLZE8HAJe_p67z-fxKgYfZzDKMyj0";
    private static final String GOOGLE_KEY_5 = "AIzaSyAdLWUhmoL7awJylFZRmrQv8-CEkjLZaT8";
    private static final String GOOGLE_KEY_6 = "AIzaSyD28mTMy7swcMpn1WRRrA4Rz8B-n7dbZZM";
    public static final String GOOGLE_KEY = GOOGLE_KEY_3;

    public static final class NUTITEQ {
        public static final String NUTITEQ_KEY = "XTUMwQ0ZHWnE5RjVURWRZK2M3R3NCR3pFU0tkYnloVklBaFVBa3JZdXk2RTQyb0V3enZTZi9wMlgrM1lqZDBrPQoKcHJvZHVjdHM9c2RrLWFuZHJvaWQtMy4qCnBhY2thZ2VOYW1lPWNvbS5mcHQucm91dGVyCndhdGVybWFyaz1udXRpdGVxCnVzZXJLZXk9ZWYzZDA5Y2IwOGRlN2I0NTdjZWNhYjBlZTZjMDkwZGEK";
        public static final String NOTITEQ_VN_CODE = "VN";
        public static final String OPEN_STREET_MAP = "nutiteq.osm";
        public static final String GLOBAL_STREET_MAP = "nutiteq.mbstreets";

        public static final class PackageStatus {
            public static final String PACKAGE_ACTION_READY = "package_action_ready";
            public static final String PACKAGE_ACTION_WAITING = "package_action_waiting";
            public static final String PACKAGE_ACTION_COPYING = "package_action_copying";
            public static final String PACKAGE_ACTION_DOWNLOADING = "package_action_downloading";
            public static final String PACKAGE_ACTION_REMOVING = "package_action_removing";
        }
    }

    public static final class FPT_SERVICE {
        private static final String FPT_SERVER = "http://118.69.135.22";
        public static final String TEXT_TO_SPEECH = FPT_SERVER + "/synthesis/file?voiceType=female&text=";
    }

    public static final class PATH {
        private static final String MESSAGE_PATH = "/com/fpt/router/wear";
        public static final String MESSAGE_PATH_MOTOR = MESSAGE_PATH + "/motor/";
        public static final String MESSAGE_PATH_BUS_FOUR_POINT = MESSAGE_PATH + "/bus/fourpoint";
        public static final String MESSAGE_PATH_BUS_TWO_POINT = MESSAGE_PATH + "/bus/twopoint";
        public static final String MESSAGE_PATH_GPS = MESSAGE_PATH + "/gps";
    }

    public static final class Vibrator {
        public static final int DELAY_VIBRATE = 500;
        public static final int OFF_VIBRATE = 1000;
        public static final int ON_VIBRATE = 500;
    }

    public static final class FileCache {
        public static final String FOLDER_NAME = "fuckthao";
        public static final int FOLDER_SIZE = 100;
        public static final int SYSTEM_SIZE = FOLDER_SIZE * 1024 * 1024;
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
        public static final int SEARCH_LOCATION = 5;
    }

    public static final class GoogleApiCode {
        public static final String OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
        public static final String NOT_FOUND = "NOT_FOUND";
        public static final String NO_RESULT = "ZERO_RESULTS";
        public static final String NO_NETWORK = "NO_NETWORK_CONNECTION";
    }

    public static class SearchBus {
        public static boolean IS_REAL_BUS_SERVER = true;
        public static boolean IS_USED_REAL_WALKING = true;
    }

    public static final int NEAR_CIRCULAR_RANGE = 50;

    public static void buildAPILink() {
        API.SEARCH_BUS_ROUTE = SERVER_ADDRESS + "/api/twopoint";
        API.SEARCH_BUS_ROUTE_FOUR_POINT = SERVER_ADDRESS + "/search/multi";
    }

    public static int DISTANCE_DEMO = 0;
    public static float ALPHA = 0.1f;
    public static double CHECK_DISTANCE = 20;
}
