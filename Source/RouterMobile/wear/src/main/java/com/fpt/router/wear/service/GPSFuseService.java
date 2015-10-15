package com.fpt.router.wear.service;

/**
 * Created by Nguyen Trung Nam on 10/5/2015.
 */

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.fpt.router.library.model.message.LocationMessage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import de.greenrobot.event.EventBus;

public class GPSFuseService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private EventBus bus = EventBus.getDefault();

    private GoogleApiClient mGoogleApiClient;

    private Context mContext = null;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location mCurrentLocation; // mCurrentLocation
    double latitude; // latitude
    double longitude; // longitude
    LocationRequest mLocationRequest;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 500 * 1 * 1; // 0.5s

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSFuseService() {

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(MIN_TIME_BW_UPDATES);
        mLocationRequest.setFastestInterval(MIN_TIME_BW_UPDATES);
        mLocationRequest.setSmallestDisplacement(0.01f);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onDestroy() {
        mGoogleApiClient.disconnect();
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("hqthao", "fuck 222");
        bus = EventBus.getDefault();
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        Log.e("hqthao", "try to connect");
        mGoogleApiClient.connect();
        return START_STICKY;
    }

    //region  GoogleApiClient
    @Override
    public void onConnected(Bundle bundle) {
        Log.e("Nam:", "Connection CC");
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (currentLocation != null)
            Log.e("hqthao", "Current location: " + currentLocation.getLongitude() + "###" + currentLocation.getLatitude());
        //LocationServices.FusedLocationApi.setMockMode(mGoogleApiClient, true);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this).setResultCallback(new ResultCallback() {

            @Override
            public void onResult(Result result) {

            }


        });;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("Nam:", "Connection failed: " + connectionResult.toString());

    }

    //endregion

    @Override
    public void onLocationChanged(Location locationChanged) {
        this.mCurrentLocation = locationChanged;
        Log.e("Nam", "Ket qua: " + mCurrentLocation.getLatitude() + " : " + mCurrentLocation.getLongitude());
        LocationMessage message = new LocationMessage(mCurrentLocation);
        bus.post(message);
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }




}
