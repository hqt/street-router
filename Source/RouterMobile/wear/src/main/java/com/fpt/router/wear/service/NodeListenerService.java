package com.fpt.router.wear.service;

import android.content.pm.PackageManager;
import android.util.Log;

import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by Nguyen Trung Nam on 10/15/2015.
 */
public class NodeListenerService extends WearableListenerService {

    private static final String TAG = "NodeListenerService";

    @Override
    public void onPeerDisconnected(Node peer) {
        Log.d(TAG, "You have been disconnected.");
        if(!hasGPS()) {
            // Notify user to bring tethered handset
            // Fall back to functionality that does not use location
        }
    }

    private boolean hasGPS() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }
}