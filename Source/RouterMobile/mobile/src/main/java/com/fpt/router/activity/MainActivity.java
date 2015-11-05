package com.fpt.router.activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.activity.base.VectorMapBaseActivity;
import com.fpt.router.library.model.message.LocationMessage;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.service.GPSServiceOld;
import com.fpt.router.utils.NutiteqMapUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;
import com.nutiteq.core.MapPos;
import com.nutiteq.core.MapRange;
import com.nutiteq.datasources.LocalVectorDataSource;
import com.nutiteq.layers.VectorLayer;
import com.nutiteq.vectorelements.Marker;

import de.greenrobot.event.EventBus;

/**
 * Created by asus on 10/6/2015.
 */
public class MainActivity extends VectorMapBaseActivity implements LocationListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    DrawerLayout mDrawerLayout;
    private FloatingActionButton fabMap;
    private FloatingActionButton fab;
    private Marker now;
    private GoogleApiClient mGoogleApiClient;
    LocalVectorDataSource vectorDataSource;
    boolean isTracking = false;
    VectorLayer vectorLayer;

    private EventBus bus = EventBus.getDefault();

    Marker marker;
    //Test sensor
    SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorMagneticField;

    private float[] valuesAccelerometer;
    private float[] valuesMagneticField;

    private float[] matrixR;
    private float[] matrixI;
    private float[] matrixValues;
    private float test;
    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        //Make DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        // Nutiteq
        vectorDataSource = new LocalVectorDataSource(baseProjection);
        // Initialize a vector layer with the previous data source
        vectorLayer = new VectorLayer(vectorDataSource);
        // Add the previous vector layer to the map
        mapView.getLayers().add(vectorLayer);


        MapPos markerPos = mapView.getOptions().getBaseProjection().fromWgs84(new MapPos(106.628394, 10.855090));
        mapView.setFocusPos(markerPos, 1);
        mapView.setZoom(10, 1);

        //marker = NutiteqMapUtil.drawCurrentMarkerNutiteq(mapView, vectorDataSource, getResources(),
        //        10.852954, 106.629268, R.drawable.pink);


        /**
         * Click event in menu item
         */
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Intent intent;
                switch (menuItem.getItemId()){
                    case R.id.navigation_item_help:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation_item_reference:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation_item_setting:
                        intent = new Intent(MainActivity.this,SettingActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_item_history:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation_item_close_service:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        //float button
        fabMap = (FloatingActionButton) findViewById(R.id.fabMap);
        fabMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(MainActivity.this, SearchRouteActivity.class);
                    startActivity(intent);
                    return true;
                }
                return true; // consume the event
            }
        });



        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    // initializeMap();
                    isTracking = !isTracking;
                    return true;
                }
                return true; // consume the event
            }
        });

        /*// notification testing
        int notificationId = (int) new Date().getTime();

        // Build intent for mobile: open SearchRouteActivity when click to intent
        Intent viewIntent = new Intent(this, SearchRouteActivity.class);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);

        // run notification for wearable side

        // WearableExtender. Using this for add functionality for wear. (more advanced)
        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setHintHideIcon(false)                 // show app icon
                ;

        // Create second page notification. for longer message. only show on wear. mobile will not see those pages
        NotificationCompat.BigTextStyle wearSecondPageNotif = new NotificationCompat.BigTextStyle();
        wearSecondPageNotif.setBigContentTitle("Page 2")
                .bigText("Nam Đẹp Trai");

        // create notification from builder
        Notification secondPageNotification =
                new NotificationCompat.Builder(this)
                        .setStyle(wearSecondPageNotif)
                        .run();

        wearableExtender.addPage(secondPageNotification);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_done)
                        .setContentTitle("Nguyễn Trung Nam")
                        .setContentText("Nam Dễ Thương")
                        .setVibrate(new long[]{DELAY_VIBRATE, ON_VIBRATE, OFF_VIBRATE, ON_VIBRATE, OFF_VIBRATE, ON_VIBRATE})
                        .setColor(Color.BLUE)
                        .extend(wearableExtender)
                        .setContentIntent(viewPendingIntent);


        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.run());*/


        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        valuesAccelerometer = new float[3];
        valuesMagneticField = new float[3];

        matrixR = new float[9];
        matrixI = new float[9];
        matrixValues = new float[3];



        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                // TODO Auto-generated method stub

                switch(event.sensor.getType()){
                    case Sensor.TYPE_ACCELEROMETER:
                        valuesAccelerometer = DecodeUtils.lowPass(event.values, valuesAccelerometer);
                        /*for(int i =0; i < 3; i++){
                            valuesAccelerometer[i] = event.values[i];
                        }*/
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        valuesMagneticField = DecodeUtils.lowPass(event.values, valuesMagneticField);
                        /*for(int i =0; i < 3; i++){
                            valuesMagneticField[i] = event.values[i];
                        }*/
                        break;
                }

                boolean success = SensorManager.getRotationMatrix(
                        matrixR,
                        matrixI,
                        valuesAccelerometer,
                        valuesMagneticField);

                if(success){
                    SensorManager.getOrientation(matrixR, matrixValues);

                    float azimuth = (float)Math.toDegrees(matrixValues[0]);
                    DecodeUtils decodeUtils = new DecodeUtils();
                    azimuth = decodeUtils.getHeading(azimuth);

                    if(marker != null && Math.abs(azimuth-test) > 1) {
                        marker.setRotation(azimuth-45);
                        mapView.setMapRotation((360.0f-azimuth), 0);
                        Log.e("NAM:", "xoay deu, xoay deu: " + "Double: " +azimuth + " !!!! Float: " + matrixValues[0] );
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

    }





    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(sensorEventListener,
                sensorAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener,
                sensorMagneticField,
                SensorManager.SENSOR_DELAY_NORMAL);
        /*Intent intent = new Intent(MainActivity.this, GPSServiceOld.class);
        startService(intent);*/
    }

    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();

        sensorManager.registerListener(sensorEventListener,
                sensorAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener,
                sensorMagneticField,
                SensorManager.SENSOR_DELAY_NORMAL);
        bus.register(this);
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        mGoogleApiClient.disconnect();
        sensorManager.unregisterListener(sensorEventListener, sensorAccelerometer);
        sensorManager.unregisterListener(sensorEventListener, sensorMagneticField);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            /*case R.id.action_settings:
                return true;*/

        }
        return super.onOptionsItemSelected(item);
    }

    public void onEventMainThread(LocationMessage event) {
        onLocationChanged(event.location);
    }


    /**
     * open google map
     */

    @Override
    public void onLocationChanged(Location location) {
        if (now != null) {
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        com.fpt.router.library.model.common.Location local = new com.fpt.router.library.model.common.Location();
        local.setLatitude(location.getLatitude());
        local.setLongitude(location.getLongitude());


        if (isTracking) {

        }
        if(marker == null){
           marker = NutiteqMapUtil.drawCurrentMarkerNutiteq(mapView, vectorDataSource, getResources(),
                    10.852954, 106.629268, R.drawable.pink);

        } else {
            MapPos markerPos = mapView.getOptions().getBaseProjection().fromWgs84(
                    new MapPos(location.getLongitude(), location.getLatitude()));
            marker.setPos(markerPos);
        }

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    /*class SendToDataLayerThread extends Thread {
        String path;
        DataMap dataMap;
        com.fpt.router.library.model.common.Location location;
        // Constructor for sending data objects to the data layer
        SendToDataLayerThread(String p, DataMap data, com.fpt.router.library.model.common.Location local) {
            path = p;
            dataMap = data;
            location = local;
        }

        public void run() {
            Log.e("Nam", "aaaa");
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
            Log.e("Nam", "bbbb");
            for (Node node : nodes.getNodes()) {
                Leg leg = new Leg();
                DataMap dataMaps = location.putToDataMap();
                Log.e("hqthao", "DataMap: " + dataMaps + " sent to: " + node.getDisplayName());

                PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(path);
                putDataMapRequest.getDataMap().putDataMap("location", dataMaps);

                PutDataRequest request = putDataMapRequest.asPutDataRequest();

                PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient, request);

                DataApi.DataItemResult result = pendingResult.await();
                if (result.getStatus().isSuccess()) {
                    Log.e("hqthao", "DataMap: " + dataMaps + " sent to: " + node.getDisplayName());
                } else {
                    // Log an error
                    Log.v("myTag", "ERROR: failed to send DataMap");
                }

            }
        }
    }*/
}
