package com.fpt.router.wear.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.message.LocationGPSMessage;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.model.common.Location;
import com.fpt.router.library.utils.JSONUtils;
import com.fpt.router.library.utils.StringUtils;
import com.fpt.router.wear.activity.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

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
    private EventBus bus = EventBus.getDefault();

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

    }
    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        for (DataEvent event : dataEvents) {
            // Check the data type
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                DataItem dataItem = event.getDataItem();
                // Check the data path
                String path = dataItem.getUri().getPath();
                Intent intent = new Intent( this, MainActivity.class );
                Bundle bundle = new Bundle();
                Log.e("hqthao", "Sending path: " + path);
                if (path.equals(AppConstants.PATH.MESSAGE_PATH_MOTOR)) {
                    DataMap dataMap = DataMapItem.fromDataItem(dataItem).getDataMap();
                    Log.e("Nam", dataMap+ "");
                    long time = dataMap.getLong("time");
                    Leg leg = new Leg();
                    ArrayList<Leg> listLeg = leg.dataMapToListModel(dataMap);

                    // set global variable for activity :)
                    MainActivity.listLeg = listLeg;
                    MainActivity.DATA_OLD_TIME_GET = MainActivity.DATA_NEW_TIME_GET;
                    MainActivity.DATA_NEW_TIME_GET = time;

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    //intent.putParcelableArrayListExtra("list_leg", listLeg);
                    // send to activity
                    startActivity(intent);
                } else if (path.equals(AppConstants.PATH.MESSAGE_PATH_GPS)) {
                    DataMap dataMap = DataMapItem.fromDataItem(dataItem).getDataMap();
                    dataMap = dataMap.getDataMap("location");
                    Location location = new Location();
                    location.dataMapToModel(dataMap);
                    LocationGPSMessage locationGPSMessage = new LocationGPSMessage(location);
                    bus.post(locationGPSMessage);
                    Log.e("Nam", dataMap +"");
                } else if (path.equals(AppConstants.PATH.MESSAGE_PATH_BUS_TWO_POINT)) {
                    Log.e("Ngoan","Bus Two Point in wear");
                    // get asset
                    Asset asset = DataMapItem.fromDataItem(dataItem).getDataMap().getAsset("result_json");

                    // convert asset to string
                    String json = StringUtils.convertAssetToString(mGoogleApiClient, asset);

                    // convert json to object
                    Gson gson = JSONUtils.buildGson();
                    Result result = gson.fromJson(json, Result.class);
                    Log.e("hqthao", "result code: " + result.code);

                    // set global variable for activity
                    MainActivity.result = result;

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (path.equals(AppConstants.PATH.MESSAGE_PATH_BUS_FOUR_POINT)) {
                    // get asset
                    Asset asset = DataMapItem.fromDataItem(dataItem).getDataMap().getAsset("journey_json");

                    // convert asset to string
                    String json = StringUtils.convertAssetToString(mGoogleApiClient, asset);

                    // convert json to object
                    Gson gson = JSONUtils.buildGson();
                    Journey journey = gson.fromJson(json, Journey.class);

                    Log.e("hqthao", "journey code: " + journey.code);

                    // set global variable for activity
                    MainActivity.journey = journey;

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
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
