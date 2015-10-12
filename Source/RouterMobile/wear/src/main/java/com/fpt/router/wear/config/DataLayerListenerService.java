package com.fpt.router.wear.config;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fpt.router.wear.activity.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * A {@link com.google.android.gms.wearable.WearableListenerService} service that is invoked upon
 * receiving a DataItem from the handset for getting map information.
 * Handset application creates a Data Item that will then trigger the invocation of
 * this service. That will result in creation of a wearable notification. Similarly, when a
 * notification is dismissed, this service will be invoked to delete the associated data item.
 *
 * Created by Huynh Quang Thao on 10/12/15.
 */
public class DataLayerListenerService extends WearableListenerService
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "Listener";

    private static final String WEARABLE_DATA_PATH = "/wearable_data";

    private GoogleApiClient mGoogleApiClient;

    private boolean mConnected = false;
    private final static long TIMEOUT_S = 10; // how long to wait for GoogleApi Client connection


    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);
        Log.e(TAG, "Connected: name=" + peer.getDisplayName() + ", id=" + peer.getId());
    }

    @Override
    public void onMessageReceived(MessageEvent m) {
        Log.d(TAG, "onMessageReceived: " + m.getPath());
        if(m.getPath().equals("start")) {
            Intent startIntent = new Intent(this, MainActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startIntent);
        }
    }
    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        DataMap dataMap;
        for (DataEvent event : dataEvents) {

            // Check the data type
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // Check the data path
                String path = event.getDataItem().getUri().getPath();
                if (path.equals(WEARABLE_DATA_PATH)) {}
                dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                Double lat = 0.0;
                Double lng = 0.0;
                if(dataMap != null) {
                    lat = dataMap.getDouble("lat");
                    lng = dataMap.getDouble("lng");
                }
                Log.e("hqthao", "Lat: " + lat);
                Log.e("hqthao", "Lat: " + lng);
                Log.e("hqthao", "DataMap received on watch: " + dataMap);

                Intent intent = new Intent( this, MainActivity.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

<<<<<<< HEAD:Source/RouterMobile/wear/src/main/java/com/fpt/router/DataLayerListenerService.java
=======
    @Override
    public void onConnected(Bundle bundle) {
        Log.e(TAG, "Connected to Google API Client");
        mConnected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        mConnected = false;

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "Failed to connect to the Google API client");
        mConnected = false;
    }
>>>>>>> 865294786edc5b13704a00f17f733d0aa8aba0aa:Source/RouterMobile/wear/src/main/java/com/fpt/router/wear/config/DataLayerListenerService.java
}
