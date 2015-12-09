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

import com.fpt.router.framework.PrefStore;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.common.NotifyModel;
import com.fpt.router.library.model.message.LocationMessage;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.model.motorbike.Step;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.library.utils.NotificationUtils;
import com.fpt.router.library.utils.SoundUtils;
import com.fpt.router.utils.PolyLineUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.fpt.router.library.config.AppConstants.*;

public class GPSServiceOld extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String LOCATION_CHANGE_SIGNAL = "location_change_signal";
    private static List<LatLng> fakeGPSList;

    private static int fakeGPSIndex = 0;
    private static int stepIndex = 0;
    private static int checkStepIndex = 0;
    private static int checkLegIndex = 0;

    private static int notifyIndex = 0;

    private EventBus bus;

    private Context mContext;
    long timeSaiduong = 0;
    long timeDithang = 0;
    LatLng checkDis;
    // flag for GPS status
    boolean isGPSEnabled = false;
    static boolean isTrueWay = true;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    public static boolean isFakeGPS = false;
    public static boolean isPlaySound = false;
    private static List<NotifyModel> listNotify;
    private static List<LatLng> listFakeGPSOfFake;
    private static List<Leg> listLegToCheck;
    private static int distance;


    public static void setListNotify(List<NotifyModel> listNotify) {
        GPSServiceOld.listNotify = listNotify;
        initializeState();
    }

    public static int getCheckLegIndex() {
        return checkLegIndex;
    }

    public static void setDistance(int input) {
        distance = input;
    }

    public static void setListFakeGPSOfFake(List<LatLng> input) {
        GPSServiceOld.listFakeGPSOfFake = input;
    }

    public static void setListLegToCheck(List<Leg> input) {
        GPSServiceOld.listLegToCheck = input;
    }

    public static List<NotifyModel> getNotifyModel() {
        return listNotify;
    }

    public static boolean getIsTrueWay() {
        return isTrueWay;
    }

    public static void initializeState() {
        // reset all state variables
        //GPSServiceOld.listStepToCheck = null;
        GPSServiceOld.isTrueWay = true;
        GPSServiceOld.checkLegIndex = 0;
        GPSServiceOld.checkStepIndex = 0;
        GPSServiceOld.fakeGPSIndex = 0;
        GPSServiceOld.stepIndex = 0;
        GPSServiceOld.notifyIndex = 0;
        GPSServiceOld.isFakeGPS = false;
        GPSServiceOld.isPlaySound = false;
        gpsServiceInstance.fakeGPSHandler.removeCallbacks(gpsServiceInstance.fakeGPSCallback);
    }

    boolean canGetLocation = false;

    private final Handler fakeGPSHandler = new Handler();

    Location location; // mCurrentLocation
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 10 meters

    // The minimum timeSaiduong between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 500 * 1 * 1; // 0.5s

    // Declaring a Location Manager
    protected LocationManager locationManager;

    GoogleApiClient mGoogleApiClient;
    boolean mConnected = false;

    // keep gps service instance
    public static GPSServiceOld gpsServiceInstance;

    public GPSServiceOld() {
        Log.e("hqthao", "empty constructor's GPS Service has been called");
        gpsServiceInstance = this;
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

        fakeGPSHandler.removeCallbacks(fakeGPSCallback);
        fakeGPSHandler.post(fakeGPSCallback);
    }

    private Runnable fakeGPSCallback = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Log.e("hqthao", "fuck fuck aaaa");
                    onLocationChanged(new Location("a"));
                }
            }).start();

            // counting speed
            int speed = PrefStore.getSimulationSpeed();
            speed = (20 * 1000) / (speed * 1000 / 3600);

            // should this if contains ? checking
            /*if (isFakeGPS) {
                fakeGPSHandler.postDelayed(this, speed);
            }*/

            if (isFakeGPS) {
                fakeGPSHandler.postDelayed(this, speed);
            }
        }
    };

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    public static void turnOnFakeGPS(List<LatLng> latLngs) {
        fakeGPSList = latLngs;
        isFakeGPS = true;
        gpsServiceInstance.fakeGPSHandler.removeCallbacks(gpsServiceInstance.fakeGPSCallback);
        gpsServiceInstance.fakeGPSHandler.post(gpsServiceInstance.fakeGPSCallback);
    }

    public static void turnOffFakeGPS() {
        isFakeGPS = false;
        gpsServiceInstance.fakeGPSHandler.removeCallbacks(gpsServiceInstance.fakeGPSCallback);
    }

    @Override
    public void onDestroy() {
        fakeGPSHandler.removeCallbacks(fakeGPSCallback);
        super.onDestroy();
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

        Calendar calendar = Calendar.getInstance();
        LatLng checkPoint = new LatLng(location.getLatitude(), location.getLongitude());

        if (isTrueWay) {
            if (listLegToCheck != null) {
               /* if(checkStepIndex < listLegToCheck.get(checkLegIndex).getSteps().size()) {
                    isTrueWay = PolyLineUtils.isOnAllRoute(DecodeUtils.decodePoly(
                            listLegToCheck.get(checkLegIndex).getSteps().
                                    get(checkStepIndex).getPolyline()), checkPoint, CHECK_DISTANCE);
                    if(!isTrueWay && checkStepIndex > 0) {
                        isTrueWay = PolyLineUtils.isOnAllRoute(DecodeUtils.decodePoly(
                                listLegToCheck.get(checkLegIndex).getSteps().
                                        get(checkStepIndex - 1).getPolyline()), checkPoint, CHECK_DISTANCE);
                    }
                } else {
                    isTrueWay = PolyLineUtils.isOnAllRoute(DecodeUtils.decodePoly(
                            listLegToCheck.get((checkLegIndex + 1) % listLegToCheck.size()).getSteps().
                                    get(0).getPolyline()), checkPoint, CHECK_DISTANCE);
                    if(!isTrueWay) {
                        isTrueWay = PolyLineUtils.isOnAllRoute(DecodeUtils.decodePoly(
                                listLegToCheck.get(checkLegIndex).getSteps().
                                        get(checkStepIndex-1).getPolyline()), checkPoint, CHECK_DISTANCE);
                    } else {
                        checkLegIndex = (checkLegIndex + 1) % listLegToCheck.size();
                        checkStepIndex = 0;
                    }
                }*/
                if(checkStepIndex < listLegToCheck.get(checkLegIndex).getSteps().size()) {
                    isTrueWay = PolyLineUtils.isOnAllRoute(DecodeUtils.decodePoly(
                            listLegToCheck.get(checkLegIndex).getSteps().
                                    get(checkStepIndex).getPolyline()), checkPoint, distance);
                } else {
                    checkLegIndex = (checkLegIndex + 1) % listLegToCheck.size();
                    checkStepIndex = 0;
                    isTrueWay = PolyLineUtils.isOnAllRoute(DecodeUtils.decodePoly(
                            listLegToCheck.get(checkLegIndex).getSteps().
                                    get(checkStepIndex).getPolyline()), checkPoint, distance);

                }
            }
        }
        if (isTrueWay) {
            bus.post(listLegToCheck.get(checkLegIndex).getEndAddress());
            LatLng latlngOfStep = new LatLng(listNotify.get(stepIndex).location.getLatitude(),
                    listNotify.get(stepIndex).location.getLongitude());
            if (DecodeUtils.calculateDistance(checkPoint, latlngOfStep) < distance) {
                notifyIndex = stepIndex;
                checkStepIndex ++;
                stepIndex = (stepIndex + 1) % listNotify.size();
                timeDithang = calendar.getTimeInMillis();
                return true;
            } else {
                if ((calendar.getTimeInMillis() - timeDithang) > 10000) {
                    timeDithang = calendar.getTimeInMillis();
                    bus.post(DI_THANG);
                }
            }
        } else {
            bus.post(checkPoint);
            if (timeSaiduong == 0) {
                timeSaiduong = calendar.getTimeInMillis();
                bus.post(SAI_DUONG);
                Log.e("Step", "Sai duong");
            } else {
                if ((calendar.getTimeInMillis() - timeSaiduong) > 10000) {
                    timeSaiduong = calendar.getTimeInMillis();
                    bus.post(SAI_DUONG);
                }
            }
            for (int n = 0; n < listLegToCheck.size(); n++) {
                List<Step> listStepToCheck = listLegToCheck.get(n).getSteps();
                for (int m = 0; m < listStepToCheck.size(); m++) {
                    List<LatLng> listLLOfStep = DecodeUtils.decodePoly(listStepToCheck.get(m).getPolyline());
                    if (PolyLineUtils.isOnAllRoute(listLLOfStep, checkPoint, 20)) {
                        checkLegIndex = n;
                        checkStepIndex = m;
                        int count = -1;
                        for(int i = 0; i < checkLegIndex; i++) {
                            count = count + listLegToCheck.get(i).getSteps().size();
                        }
                        if(count > -1) {
                            notifyIndex = count;
                            stepIndex = (count + 1) % listNotify.size();
                        } else {
                            notifyIndex = m;
                            stepIndex = (m + 1) % listNotify.size();
                        }
                        timeDithang = calendar.getTimeInMillis();
                        isTrueWay = true;
                        break;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location locationChanged) {
        // this is real gps from system
        location = locationChanged;

        // get gps. when come to end list. back to top list and re-run again :)
        if (isFakeGPS) {
            location.setLatitude(fakeGPSList.get(fakeGPSIndex).latitude);
            location.setLongitude(fakeGPSList.get(fakeGPSIndex).longitude);
            fakeGPSIndex = (fakeGPSIndex + 1) % fakeGPSList.size();

            /*location.setLatitude(listFakeGPSOfFake.get(fakeGPSIndex).latitude);
            location.setLongitude(listFakeGPSOfFake.get(fakeGPSIndex).longitude);
            fakeGPSIndex = (fakeGPSIndex + 1) % listFakeGPSOfFake.size();*/
        }

        // notify when near some location
        if (isNearLocation(location)) {
            //Log.e("NAM PLAY SOUND:", "" + listNotify.get(notifyIndex).smallMessage);
            // Play sound
            if (isPlaySound) {
                SoundUtils.playSound(mContext, listNotify.get(notifyIndex).smallMessage);
            }

            // Create notification
            NotifyModel notifyModel = listNotify.get(notifyIndex);
            NotificationUtils.run(mContext, notifyModel.smallTittle, notifyModel.smallMessage, notifyModel.longTittle, notifyModel.longMessage);

            // Send signal
            bus.post(notifyModel);
        }

        // send gps message changed to any client has subcribed
        LocationMessage message = new LocationMessage(location);
        bus.post(message);

        // send gps change to wear
        if (mConnected) {
            com.fpt.router.library.model.common.Location local = new com.fpt.router.library.model.common.Location();
            local.setLatitude(location.getLatitude());
            local.setLongitude(location.getLongitude());
            new SendToDataLayerThread(PATH.MESSAGE_PATH_GPS, local).start();
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