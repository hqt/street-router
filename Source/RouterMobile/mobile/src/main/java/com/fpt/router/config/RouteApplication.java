package com.fpt.router.config;

import android.app.Application;
import android.content.Context;

/**
 * Created by huynhthao on 9/19/15.
 */
public class RouteApplication extends Application {
    public static Context mContext;

    /** this is just application context. Use this function carefully to avoid error */
    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
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
