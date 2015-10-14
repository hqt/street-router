package com.fpt.router.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.fpt.router.framework.RouterApplication;
import com.fpt.router.library.config.PrefStore;
import com.fpt.router.library.model.bus.BusLocation;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by huynhthao on 9/19/15.
 */
public class NetworkUtils {

    public static String TAG = LogUtils.makeLogTag(NetworkUtils.class);

    /**
     * Reference this link : {@see <a href=
     * "http://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html"
     * >Android Developer</a>} Using to detect network on Android Device if Wifi | 3G -> can synchronize data
     */
    public static boolean isNetworkConnected() {
        Context ctx = RouterApplication.getAppContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable()
                && wifi.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
            return true;
        } else if (mobile.isAvailable()
                && mobile.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }

    /** should use this method. because base on user setting */
    public static boolean isDeviceNetworkConnected() {
        // TrungDQ: Bug 1: inverted setting for check box in SettingActivity
        if (!PrefStore.isMobileNetwork()) {
            return isWifiConnect();
        } else {
            return isNetworkConnected();
        }
    }

    /** wifi connect or not (not including 3G) */
    public static boolean isWifiConnect() {
        Context ctx = RouterApplication.getAppContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifi.isAvailable()
                && wifi.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }

    public static String download(String urlStr) {
        URL url = null;
        InputStream is = null;
        String result = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            StringBuffer sb = new StringBuffer();
            is = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";

            // start to read data from server
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            result = sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                    Log.i(TAG, "Error closing InputStream");
                }
            }
        }
        return result;
    }

    /** sleep for predefine second as we working on slow network @_@ */
    public static void stimulateNetwork(int milisecond) {
        try {
            Thread.sleep(milisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** use this method to determine thread signature when running on multi-thread */
    public static long getThreadId() {
        Thread t = Thread.currentThread();
        return t.getId();
    }

    /** get signature of current thread */
    public static String getThreadSignature() {
        Thread t = Thread.currentThread();
        long id = t.getId();
        String name = t.getName();
        long priority = t.getPriority();
        String groupname = t.getThreadGroup().getName();
        return (name + ":(id)" + id + ":(priority)" + priority
                + ":(group)" + groupname);
    }

    public static String getJsonFromServer(List<BusLocation> busLocations){
        BusLocation A = busLocations.get(0);
        BusLocation B = busLocations.get(1);
        Calendar now = Calendar.getInstance();
        String addressA = null;
        String addressB = null;
        try{
            addressA = URLEncoder.encode(A.getAddress(),"UTF-8");
            addressB = URLEncoder.encode(B.getAddress(),"UTF-8");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }

        String url = "http://192.168.43.33:8080/api/twopoint?latA="+A.getLatitude()
                +"&latB="+B.getLatitude()+"&longA="+A.getLongitude()
                +"&longB="+B.getLongitude()+"&hour="+now.get(Calendar.HOUR_OF_DAY)+"&minute="+now.get(Calendar.MINUTE)
                +"&addressA="+addressA+"&addressB="+addressB;
        String json = NetworkUtils.download(url);
        return json ;
    }

    public static String getLocationGoogleAPI(String addressOfLocation){
        String key = "AIzaSyDfRreMPCuDwSRaZdrnk64Ou_4YV-pTheQ";
        String address = addressOfLocation;
        try {
            address = URLEncoder.encode(address,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://maps.google.com/maps/api/geocode/json?address="+address;
        String json = NetworkUtils.download(url);
        return json;
    }

    public static String linkGoogleDrirectionForTwoPoint(String start, String end){
        String key = "AIzaSyDfRreMPCuDwSRaZdrnk64Ou_4YV-pTheQ";
        String startLocation = start;
        String endLocation = end;
        try {
            startLocation = URLEncoder.encode(startLocation, "UTF-8");
            endLocation = URLEncoder.encode(endLocation, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                     "origin=place_id:" + startLocation +
                     "&destination=place_id:" + endLocation +
                     "&alternatives=true" +
                     "&mode=driving" +
                     "&key=" + key;
        return url;
    }

    public static List<String> linkFourPointWithoutOptimize(List<String> listLocation) {
        String url;
        List<String> listUrl = new ArrayList<>();
        for(int x = 0; x < listLocation.size()-1; x++){
            for(int y = 1; y < listLocation.size(); y++){
                if((x != y) && (y-x != 3)) {
                    url = linkGoogleDrirectionForTwoPoint(listLocation.get(x), listLocation.get(y));
                    listUrl.add(url);
                }
            }
        }
        return listUrl;
    }

    public static List<String> linkCuaNam(List<String> listPlaceID, Boolean optimize){
        String key = "AIzaSyDfRreMPCuDwSRaZdrnk64Ou_4YV-pTheQ";
        List<String> listUrl = new ArrayList<>();
        String startLocation = null;
        String endLocation = null;
        String waypoint_1 = "";
        String waypoint_2 = "";
        int count = 1;
        if(listPlaceID.size() == 4){
            count = 3;
        }
        List<String> listPlace = listPlaceID;
        String waypoints = "";
        for(int n = 0; n < count; n++) {
            try {
                if(count == 3) {
                    String change = listPlace.get(n+1);
                    listPlace.set(n+1, listPlace.get(1));
                    listPlace.set(1, change);
                }
                startLocation = URLEncoder.encode(listPlace.get(0), "UTF-8");
                endLocation = URLEncoder.encode(listPlace.get(1), "UTF-8");
                if (listPlace.size() == 3) {
                    waypoint_1 = URLEncoder.encode(listPlace.get(2), "UTF-8");
                    waypoints = waypoints + "&waypoints=place_id:" + waypoint_1;

                }else if (listPlace.size() == 4) {
                    waypoint_1 = URLEncoder.encode(listPlace.get(2), "UTF-8");
                    waypoint_2 = URLEncoder.encode(listPlace.get(3), "UTF-8");
                    if (optimize) {
                        waypoints = waypoints + "&waypoints=optimize:place_id:true" + "|place_id:" + waypoint_1 + "|place_id:" + waypoint_2;
                    } else {
                        waypoints = waypoints + "&waypoints=place_id:" + waypoint_1 + "|place_id:" + waypoint_2;
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "origin=place_id:" + startLocation +
                    "&destination=place_id:" + endLocation + waypoints +
                    "&alternatives=true" +
                    "&mode=driving" +
                    "&key=" + key;
            listUrl.add(url);
        }
        return listUrl;
    }

    public static String getShortePath(String start, String end,String way_point_1,String way_point_2, Boolean optimize){
        String key = "AIzaSyDfRreMPCuDwSRaZdrnk64Ou_4YV-pTheQ";
        String startLocation = null;
        String endLocation = null;
        String waypoint_1 = "";
        String waypoint_2 = "";
        try {
            startLocation = URLEncoder.encode(start, "UTF-8");
            endLocation = URLEncoder.encode(end, "UTF-8");
            waypoint_1 = URLEncoder.encode(way_point_1,"UTF-8");
            waypoint_2 = URLEncoder.encode(way_point_2,"UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String waypoints = "&waypoints=";
        if(waypoint_1 != null || waypoint_2 != null){
            if(optimize) {
                waypoints = waypoints + "optimize:true" + "|" + waypoint_1 + "|" + waypoint_2;
            } else  {
                waypoints = waypoints + waypoint_1 + "|" + waypoint_2;
            }
        }
        String url = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + startLocation +
                "&destination=" + endLocation + waypoints +
                "&alternatives=true" +
                "&mode=driving" +
                "&key=" + key;

        String json = NetworkUtils.download(url);
        return json;
    }

    public static String linkGooglePlace(String input){
        String key = "AIzaSyDfRreMPCuDwSRaZdrnk64Ou_4YV-pTheQ";
        String text = null;
        try {
            text = URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?" +
                     "input=" + text +
                     "&types=establishment&components=country:vn&language=vi&sensor=true&key=" + key;
        return url;
    }


}
