package com.fpt.router.wear.config;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Huynh Quang Thao on 10/12/15.
 */
public class WearableApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}