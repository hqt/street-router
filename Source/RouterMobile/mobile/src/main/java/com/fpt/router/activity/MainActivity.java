package com.fpt.router.activity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.message.LocationMessage;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.service.GPSFuseService;
import com.fpt.router.library.utils.MapUtils;
import com.fpt.router.service.GPSService;
import com.fpt.router.service.GPSServiceOld;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by asus on 10/6/2015.
 */
public class MainActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{
    DrawerLayout mDrawerLayout;
    private FloatingActionButton fabMap;
    private FloatingActionButton fab;
    private GoogleMap googleMap;
    private Marker now;
    private GoogleApiClient mGoogleApiClient;

    private EventBus bus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_first);

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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
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
                    initializeMap();
                    GPSServiceOld gpsService = new GPSServiceOld(MainActivity.this);
                    MapUtils.drawPointColor(googleMap, gpsService.getLatitude(), gpsService.getLongitude(), "Current", BitmapDescriptorFactory.HUE_RED);
                    return true;
                }
                return true; // consume the event
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent(MainActivity.this, GPSServiceOld.class);
        startService(intent);
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
        switch (id){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onEvent(LocationMessage event){
        Log.e("hqthao", event.location.getLatitude() + "");
        onLocationChanged(event.location);
    }


    /**
     * open google map
     */
    private void initializeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();


            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(now != null){
            now.remove();
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        com.fpt.router.library.model.motorbike.Location local = new com.fpt.router.library.model.motorbike.Location();
        local.setLatitude(location.getLatitude());
        local.setLongitude(location.getLongitude());
        Log.e("Nam:", local.getLatitude() + "@#@" + local.getLongitude());

        now = MapUtils.drawPointColor(googleMap, latitude, longitude, "", BitmapDescriptorFactory.HUE_RED);
        MapUtils.moveCamera(googleMap, latitude, longitude, 15);
        DataMap dataMap = new DataMap();

        new SendToDataLayerThread(AppConstants.PATH.MESSAGE_PATH_GPS, dataMap, local).start();
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

    class SendToDataLayerThread extends Thread {
        String path;
        DataMap dataMap;
        com.fpt.router.library.model.motorbike.Location location;
        // Constructor for sending data objects to the data layer
        SendToDataLayerThread(String p, DataMap data, com.fpt.router.library.model.motorbike.Location local) {
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
    }
}
