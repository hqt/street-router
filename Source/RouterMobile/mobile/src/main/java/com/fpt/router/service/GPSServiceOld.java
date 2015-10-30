package com.fpt.router.service;

/**
 * Created by Nguyen Trung Nam on 10/5/2015.
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.common.NotifyModel;
import com.fpt.router.library.model.message.LocationMessage;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.library.utils.NotificationUtils;
import com.fpt.router.library.utils.SoundUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class GPSServiceOld extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String LOCATION_CHANGE_SIGNAL = "location_change_signal";
    private static List<LatLng> fakeGPSList;

    private static int fakeGPSIndex = 0;
    private static int stepIndex = 0;
    private static int notifyIndex = 0;

    private EventBus bus;

    private Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;


    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    public static boolean isFakeGPS = false;
    public static boolean isPlaySound = false;
    private static List<NotifyModel> listNotify;

    public static void setListNotify(List<NotifyModel> listNotify) {
        GPSServiceOld.listNotify = listNotify;
        initializeState();
     }

    public static void turnOnFakeGPS(List<LatLng> latLngs) {
        fakeGPSList = latLngs;
        isFakeGPS = true;
    }

    public static void turnOffFakeGPS() {
        isFakeGPS = false;
    }

    public static List<NotifyModel> getNotifyModel() {
        return listNotify;
    }

    private static void initializeState() {
        // reset all state variables
        GPSServiceOld.fakeGPSIndex = 0;
        GPSServiceOld.stepIndex = 0;
        GPSServiceOld.notifyIndex = 0;
        GPSServiceOld.isFakeGPS = false;
        GPSServiceOld.isPlaySound = false;


    }

    boolean canGetLocation = false;

    private final Handler handlerThread = new Handler();

    Location location; // mCurrentLocation
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 500 * 1 * 1; // 0.5s

    // Declaring a Location Manager
    protected LocationManager locationManager;

    GoogleApiClient mGoogleApiClient;
    boolean mConnected = false;

    public GPSServiceOld() {
        Log.e("hqthao", "empty constructor has been called");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeState();
        this.mContext = getApplicationContext();
        bus = EventBus.getDefault();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        getLocation();

        handlerThread.removeCallbacks(whatMyName);
        handlerThread.post(whatMyName);
    }

    private Runnable whatMyName = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (isFakeGPS) {
                        onLocationChanged(new Location("a"));
                    }
                }
            }).start();
            handlerThread.postDelayed(this, 600);
        }
    };

    public GPSServiceOld(Context context) {
        this.mContext = context;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        getLocation();
    }

    public Location getLocation() {
        try {

            // from android with sdk >= 23. User can deny some permission.
            // so we must check permission programmatically on code
            if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }

            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get mCurrentLocation from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public boolean isNearLocation(Location location) {
        if (listNotify == null) {
            return false;
        }

        LatLng checkPoint = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng latlngOfStep = new LatLng(listNotify.get(stepIndex).location.getLatitude(),
                listNotify.get(stepIndex).location.getLongitude());
        if (DecodeUtils.calculateDistance(checkPoint, latlngOfStep) < AppConstants.NEAR_CIRCULAR_RANGE) {
            notifyIndex = stepIndex;
            stepIndex = (stepIndex + 1) % listNotify.size();
            return true;
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location locationChanged) {
        // this is real gps from system
        location = locationChanged;

        if (isFakeGPS) {
            location.setLatitude(fakeGPSList.get(fakeGPSIndex).latitude);
            location.setLongitude(fakeGPSList.get(fakeGPSIndex).longitude);
            fakeGPSIndex = (fakeGPSIndex + 1) % fakeGPSList.size();
        }

        if (isNearLocation(location)) {
            // Play sound
            if (isPlaySound) {
                SoundUtils.playSound(listNotify.get(notifyIndex).smallMessage);
            }

            // Create nofitication
            NotifyModel notifyModel = listNotify.get(notifyIndex);
            NotificationUtils.run(mContext, notifyModel.smallTittle, notifyModel.smallMessage, notifyModel.longTittle, notifyModel.longMessage);

            // Send signal
            bus.post(notifyModel);
        }

        LocationMessage message = new LocationMessage(location);
        bus.post(message);

        if (mConnected) {
            com.fpt.router.library.model.common.Location local = new com.fpt.router.library.model.common.Location();
            local.setLatitude(location.getLatitude());
            local.setLongitude(location.getLongitude());
            new SendToDataLayerThread(AppConstants.PATH.MESSAGE_PATH_GPS, local).start();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onConnected(Bundle bundle) {
        mConnected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        mConnected = false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mConnected = false;
    }


    class SendToDataLayerThread extends Thread {
        String path;
        com.fpt.router.library.model.common.Location location;

        // Constructor for sending data objects to the data layer
        SendToDataLayerThread(String p, com.fpt.router.library.model.common.Location local) {
            path = p;
            location = local;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
            for (Node node : nodes.getNodes()) {
                Leg leg = new Leg();
                DataMap dataMaps = location.putToDataMap();
                //Log.e("hqthao", "DataMap: " + dataMaps + " sent to: " + node.getDisplayName());

                PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(path);
                putDataMapRequest.getDataMap().putDataMap("location", dataMaps);

                PutDataRequest request = putDataMapRequest.asPutDataRequest();

                PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient, request);

                DataApi.DataItemResult result = pendingResult.await();
                if (result.getStatus().isSuccess()) {
                    // Log.e("hqthao", "DataMap: " + dataMaps + " sent to: " + node.getDisplayName());
                } else {
                    // Log an error
                    Log.v("myTag", "ERROR: failed to send DataMap");
                }

            }
        }
    }


}
