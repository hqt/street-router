package com.fpt.router.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.activity.base.VectorMapBaseActivity;
import com.fpt.router.framework.OrientationManager;
import com.fpt.router.library.model.message.LocationMessage;
import com.fpt.router.utils.NutiteqMapUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;
import com.nutiteq.core.MapPos;
import com.nutiteq.datasources.LocalVectorDataSource;
import com.nutiteq.layers.VectorLayer;
import com.nutiteq.vectorelements.Marker;

import de.greenrobot.event.EventBus;

import static com.fpt.router.framework.OrientationManager.OnChangedListener;

/**
 * Created by asus on 10/6/2015.
 */
public class MainActivity extends VectorMapBaseActivity implements LocationListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, OnChangedListener{
    DrawerLayout mDrawerLayout;
    private FloatingActionButton fabMap;
    private FloatingActionButton fab;
    private FloatingActionButton fab_compass;
    private Marker now;
    private GoogleApiClient mGoogleApiClient;
    LocalVectorDataSource vectorDataSource;
    boolean isTracking = false;
    VectorLayer vectorLayer;
    MapPos markerPos;

    private EventBus bus = EventBus.getDefault();

    Marker marker;
    //Test sensor

    private OrientationManager mOrientationManager;

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


        final MapPos markerPos = mapView.getOptions().getBaseProjection().fromWgs84(new MapPos(106.628394, 10.855090));
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

        fab_compass = (FloatingActionButton) findViewById(R.id.fab_compass);
        fab_compass.setVisibility(View.GONE);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    // initializeMap();
                    mapView.setFocusPos(markerPos, 0);
                    fab.setVisibility(View.GONE);
                    fab_compass.setVisibility(View.VISIBLE);
                    return true;
                }
                return true; // consume the event
            }
        });


        fab_compass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fab_compass.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                return false;
            }
        });



        SensorManager sensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mOrientationManager = new OrientationManager(sensorManager);

        mOrientationManager.addOnChangedListener(this);
        mOrientationManager.start();

    }





    @Override
    protected void onStart() {
        super.onStart();
        /*Intent intent = new Intent(MainActivity.this, GPSServiceOld.class);
        startService(intent);*/
    }

    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        mGoogleApiClient.disconnect();
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
        if(marker == null){
           marker = NutiteqMapUtil.drawCurrentMarkerNutiteq(mapView, vectorDataSource, getResources(),
                   latitude, longitude, R.drawable.marker_cua_nam_burned);

        } else {
            markerPos = mapView.getOptions().getBaseProjection().fromWgs84(
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

    @Override
    public void onOrientationChanged(OrientationManager orientationManager) {
        float azimut = orientationManager.getHeading(); // orientation contains: azimut, pitch and roll
        //System.out.println(azimut);
        if(marker != null) {
            marker.setRotation(360 - azimut);
        }
    }

    @Override
    public void onAccuracyChanged(OrientationManager orientationManager) {

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
