package com.fpt.router.framework;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fpt.router.utils.LogUtils;

/**
 * Created by huynhthao on 9/19/15.
 */
public class PrefStore {
    public static final String TAG = LogUtils.makeLogTag(PrefStore.class);

    ///////////////////////////////////////////////////
    ///////////////  PREFERENCE KEY   /////////////////
    /** preference key that mark this is first run or not */
    public static final String PREF_IS_FIRST_RUN = "is_first_run";

    /** Preference key containing currently latest version on system */
    public static final String PREF_LATEST_VERSION = "latest_database_version";

    /** Preference key containing currently latest date on system */
    public static final String PREF_LATEST_UPDATE_DATE = "latest_update_date";

    /** Preference key contain is 3g or not */
    public static final String PREF_IS_MOBILE_NETWORK = "is_mobile_network";

    /** Preference key for bus server ip */
    public static final String PREF_BUS_SERVER_IP = "bus_server_ip_address";

    /** Preference key for bus server port */
    public static final String PREF_BUS_SERVER_PORT = "bus_server_port";

    /** Preference key for simulation speed  */
    public static final String PREF_SIMULATION_SPEED = "simulation_speed";

    /** Preference key for map download option  */
    public static final String PREF_IS_MAP_DOWNLOADED = "is_map_downloaded";

    /** Preference key for bus notify distance  */
    public static final String PREF_BUS_NOTIFY_DISTANCE = "bus_notify_distance";

    /** Preference key for motorbike notify distance  */
    public static final String PREF_MOTOR_NOTIFY_DISTANCE = "motor_notify_distance";

    /** Preference key for bus sort type */
    public static final String PREF_BUS_SORT_TYPE = "bus_sort_type";

    /** Preference key for motorbike sort type  */
    public static final String PREF_MOTOR_SORT_TYPE = "motor_sort_type";

    /** Preference key for motorbike sort type  */
    public static final String PREF_STIMULATE_ROUTE_TYPE = "stimulate_route_type";

    ///////////////////////////////////////////////////////////////
    /////////////////   DEFAULT VALUE   ///////////////////////////
    /** Default value for {@link PrefStore#PREF_IS_FIRST_RUN} */
    public static final boolean DEFAULT_FIRST_RUN = true;

    /** Default value for {@link PrefStore#PREF_LATEST_VERSION} */
    public static final int DEFAULT_LATEST_VERSION = 1;

    /** Default value for {@link PrefStore#PREF_LATEST_UPDATE_DATE} (return empty string for multi-language concept) */
    public static final String DEFAULT_PREF_LATEST_UPDATE_DATE = "";

    /** Default value for {@link PrefStore#PREF_IS_MOBILE_NETWORK} */
    public static final boolean DEFAULT_AUTO_MOBILE_NETWORK = true;

    /** Default value for {@link PrefStore#PREF_BUS_SERVER_IP} */
    public static final String DEFAULT_BUS_SERVER_IP = "192.168.1.1";

    /** Default value for {@link PrefStore#PREF_BUS_SERVER_PORT} */
    public static final int DEFAULT_BUS_SERVER_PORT = 8080;

    /** Default value for {@link PrefStore#PREF_SIMULATION_SPEED} */
    public static final int DEFAULT_SIMULATION_SPEED = 120;

    /** Default value for {@link PrefStore#PREF_IS_MAP_DOWNLOADED} */
    public static final boolean DEFAULT_MAP_DOWNLOAD = false;

    /** Default value for {@link PrefStore#PREF_BUS_NOTIFY_DISTANCE} */
    public static final int DEFAULT_BUS_NOTIFY_DISTANCE = 150;

    /** Default value for {@link PrefStore#PREF_MOTOR_NOTIFY_DISTANCE} */
    public static final int DEFAULT_MOTOR_NOTIFY_DISTANCE = 150;

    /** Default value for {@link PrefStore#PREF_BUS_SORT_TYPE} */
    public static final int DEFAULT_BUS_SORT_TYPE = 0;

    /** Default value for {@link PrefStore#PREF_MOTOR_SORT_TYPE} */
    public static final int DEFAULT_MOTOR_SORT_TYPE = 0;

    /** Default value for {@link PrefStore#PREF_STIMULATE_ROUTE_TYPE} */
    public static final int DEFAULT_STIMULATE_ROUTE_TYPE = 0;

    ////////////////////////////////////////////////////////////////////
    /////////////////////////////  GETTER //////////////////////////////
    public static SharedPreferences getSharedPreferencesWithContext(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(RouterApplication.getAppContext());
    }

    public static int getLatestVersion() {
        return getSharedPreferences().getInt(PREF_LATEST_VERSION, DEFAULT_LATEST_VERSION);
    }

    public static String getLastedUpdateDate() {
        return getSharedPreferences().getString(PREF_LATEST_UPDATE_DATE, DEFAULT_PREF_LATEST_UPDATE_DATE);
    }

    public static boolean isMobileNetwork() {
        return getSharedPreferences().getBoolean(PREF_IS_MOBILE_NETWORK, DEFAULT_AUTO_MOBILE_NETWORK);
    }

    public static String getServerIp() {
        return getSharedPreferences().getString(PREF_BUS_SERVER_IP, DEFAULT_BUS_SERVER_IP);
    }

    public static int getServerPort() {
        return getSharedPreferences().getInt(PREF_BUS_SERVER_PORT, DEFAULT_BUS_SERVER_PORT);
    }

    public static int getSimulationSpeed() {
        return getSharedPreferences().getInt(PREF_SIMULATION_SPEED, DEFAULT_SIMULATION_SPEED);
    }

    public static boolean getIsMapDownloaded() {
        return getSharedPreferences().getBoolean(PREF_IS_MAP_DOWNLOADED, DEFAULT_MAP_DOWNLOAD);
    }

    public static int getBusNotifyDistance() {
        return getSharedPreferences().getInt(PREF_BUS_NOTIFY_DISTANCE, DEFAULT_BUS_NOTIFY_DISTANCE);
    }

    public static int getMotorNotifyDistance() {
        return getSharedPreferences().getInt(PREF_MOTOR_NOTIFY_DISTANCE, DEFAULT_MOTOR_NOTIFY_DISTANCE);
    }


    public static int getBusSortType() {
        return getSharedPreferences().getInt(PREF_BUS_SORT_TYPE, DEFAULT_BUS_SORT_TYPE);
    }

    public static int getMotorSortType() {
        return getSharedPreferences().getInt(PREF_MOTOR_SORT_TYPE, DEFAULT_MOTOR_SORT_TYPE);
    }

    public static int getSimulateRouteType() {
        return getSharedPreferences().getInt(PREF_STIMULATE_ROUTE_TYPE, DEFAULT_STIMULATE_ROUTE_TYPE);
    }



    ////////////////////////////////////////////////////////////////////
    /////////////////////////////  SETTER //////////////////////////////

    public static void setLastestVersion(int version) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(PREF_LATEST_VERSION, version);
        editor.commit();
    }

    public static void setDeployedApp() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PREF_IS_FIRST_RUN, false);
        editor.commit();
    }

    public static void setCanMobileNetwork(boolean state) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PREF_IS_MOBILE_NETWORK, state);
        editor.commit();
    }

    public static void setBusServerIp(String ip) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(PREF_BUS_SERVER_IP, ip);
        editor.commit();
    }

    public static void setBusServerPort(int port) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(PREF_BUS_SERVER_PORT, port);
        editor.commit();
    }

    public static void setPrefSimulationSpeed(int speed) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(PREF_SIMULATION_SPEED, speed);
        editor.commit();
    }

    public static void setIsMapDownloaded(boolean state) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PREF_IS_MAP_DOWNLOADED, state);
        editor.commit();
    }

    public static void setBusNotifyDistance(int distance) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(PREF_BUS_NOTIFY_DISTANCE, distance);
        editor.commit();
    }

    public static void setMotorNotifyDistance(int distance) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(PREF_MOTOR_NOTIFY_DISTANCE, distance);
        editor.commit();
    }

    public static void setBusSortType(int type) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(PREF_BUS_SORT_TYPE, type);
        editor.commit();
    }

    public static void setMotorSortType(int type) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(PREF_MOTOR_SORT_TYPE, type);
        editor.commit();
    }

    public static void setStimulateRouteType(int type) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(PREF_STIMULATE_ROUTE_TYPE, type);
        editor.commit();
    }

    //////////////////////////////////////////////////////////////////////
    ///////////////////// QUERY DATA EXIST ///////////////////////////////
    public static boolean isFirstRun() {
        return getSharedPreferences().getBoolean(PREF_IS_FIRST_RUN, DEFAULT_FIRST_RUN);
    }
}
