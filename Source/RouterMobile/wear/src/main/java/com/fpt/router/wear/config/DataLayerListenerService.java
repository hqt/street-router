package com.fpt.router.wear.config;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fpt.router.library.config.MessagePath;
import com.fpt.router.library.model.Model;
import com.fpt.router.library.model.motorbike.RouterDetailTwoPoint;
import com.fpt.router.wear.activity.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
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

    private static final String TAG = "DataLayerListenerService";

    private static final String WEARABLE_DATA_PATH = "/wearable_data";

    private GoogleApiClient mGoogleApiClient;

    private boolean mConnected = false;
    private final static long TIMEOUT_S = 10; // how long to wait for GoogleApi Client connection


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("hqthao", "DataLayerListenerService OnCreated");
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
        // this method rarely "see" when debugging
        Log.e("hqthao", "Connected: name=" + peer.getDisplayName() + ", id=" + peer.getId());
    }

    @Override
    public void onMessageReceived(MessageEvent m) {
        Log.d("hqthao", "onMessageReceived: " + m.getPath());
        if(m.getPath().equals(MessagePath.MESSAGE_PATH)) {
            Log.e("hqthao", "Message Path: " + "ABC");
            // Intent startIntent = new Intent(this, MainActivity.class);
            // startIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            // startActivity(startIntent);
        }
    }
    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.e("hqthao", "On Data Changed");

        for (DataEvent event : dataEvents) {

            // Check the data type
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                DataItem dataItem = event.getDataItem();
                // Check the data path
                String path = dataItem.getUri().getPath();
                Intent intent = new Intent( this, MainActivity.class );
                if (path.equals(MessagePath.MESSAGE_PATH)) {
                    DataMap dataMap = DataMapItem.fromDataItem(dataItem).getDataMap();
                    RouterDetailTwoPoint routerDetailTwoPoint = new RouterDetailTwoPoint();
                    routerDetailTwoPoint.dataMapToModel(dataMap);
                    Log.e("hqthao", "Start: " + routerDetailTwoPoint.getStartLocation());
                    Log.e("hqthao", "Latitude: " + routerDetailTwoPoint.getDetailLocation().getStart_location().getLatitude());
                    Log.e("hqthao", "Longiude: " + routerDetailTwoPoint.getDetailLocation().getStart_location().getLongitude());
                    Log.e("Name", "All: " + dataMap);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("test", routerDetailTwoPoint);
                    intent.putExtras(bundle);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.e("hqthao", "Connected to Google API Client");
        mConnected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("hqthao", "Suspended Google API Client");
        mConnected = false;

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("hqthao", "Failed to connect to the Google API client");
        mConnected = false;
    }
}
