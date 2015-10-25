package com.fpt.router.framework;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fpt.router.library.config.AppConstants;
import com.fpt.router.service.GPSServiceOld;


/**
 * Created by Huynh Quang Thao on 10/13/15.
 */
public class RouterApplication extends Application {

    public static Context mContext;



    /** this is just application context. Use this function carefully to avoid error */
    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //JodaTimeAndroid.init(this);
        mContext = getApplicationContext();
        if (mContext != null) {
            Log.e("hqthao", "khac null");
        }

        // set some system variable
        AppConstants.SERVER_IP = PrefStore.getServerIp();
        AppConstants.SERVER_PORT = PrefStore.getServerPort();

        //GPSServiceOld gpsService = new GPSServiceOld(mContext);
        Intent intent = new Intent(mContext, GPSServiceOld.class);
        startService(intent);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
