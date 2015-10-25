package com.fpt.router.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.fpt.router.framework.RouterApplication;
import com.fpt.router.framework.PrefStore;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.bus.BusLocation;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean hostAvailabilityCheck(String address, int port) {
        try (Socket s = new Socket(address, port)) {
            return true;
        } catch (IOException ex) {
        /* ignore */
        }
        return false;
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






}
