package com.fpt.router.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fpt.router.config.RouteApplication;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.util.Map;

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
        Context ctx = RouteApplication.getAppContext();
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
        Context ctx = RouteApplication.getAppContext();
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
