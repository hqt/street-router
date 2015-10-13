package com.fpt.router.framework;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import net.danlew.android.joda.JodaTimeAndroid;

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
        JodaTimeAndroid.init(this);
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
