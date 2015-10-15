package com.fpt.router.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;


/**
 * Created by Nguyen Trung Nam on 10/15/2015.
 */
public class GPSBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Location location = intent.getParcelableExtra("location_changed");
    }
}
