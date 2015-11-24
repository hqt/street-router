package com.fpt.router.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.activity.base.VectorMapBaseActivity;
import com.fpt.router.dal.SearchLocationDAL;
import com.fpt.router.framework.OrientationManager;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.bus.BusLocation;
import com.fpt.router.library.model.common.AutocompleteObject;
import com.fpt.router.library.model.message.LocationMessage;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.library.utils.SoundUtils;
import com.fpt.router.utils.GoogleAPIUtils;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.MathUtils;
import com.fpt.router.utils.NetworkUtils;
import com.fpt.router.utils.NutiteqMapUtil;
import com.fpt.router.utils.PolyLineUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wearable.Wearable;
import com.nutiteq.core.MapPos;
import com.nutiteq.datasources.LocalVectorDataSource;
import com.nutiteq.layers.VectorLayer;
import com.nutiteq.vectorelements.Marker;

import org.json.JSONException;

import de.greenrobot.event.EventBus;

import static com.fpt.router.activity.SearchRouteActivity.mapLocation;
import static com.fpt.router.framework.OrientationManager.OnChangedListener;

/**
 * Created by asus on 10/6/2015.
 */
public class MainActivity extends VectorMapBaseActivity implements LocationListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, OnChangedListener {
    DrawerLayout mDrawerLayout;
    private FloatingActionButton fabMap;
    private FloatingActionButton fab;
    private FloatingActionButton fab_compass;
    private FloatingActionButton fab_gps;
    private Marker now;
    private GoogleApiClient mGoogleApiClient;
    LocalVectorDataSource vectorDataSource;
    boolean isTracking = false;
    VectorLayer vectorLayer;
    MapPos markerPos;
    private TextView txtSearchLocation;
    Marker ng_marker;
    MapPos ng_markerPos;
    private EventBus bus = EventBus.getDefault();
    public static BusLocation ng_bus_location;
    Marker marker;
    ImageButton ng_btn_close;
    public static boolean flatGPS = false;
    public static boolean isTrackingSearchLocation = false; // field location is not search
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
        LatLng tail = new LatLng(10.855090, 106.628394);
        LatLng head = new LatLng(10.852888, 106.629283);
        LatLng check = new LatLng(10.853508, 106.629125);
        double distance = PolyLineUtils.distanceToLine(check, tail, head);
        Log.e("Distance", "" + distance);
        boolean isOnRoute = PolyLineUtils.isOnRoute(tail, head, check, 20);
        if(isOnRoute) {
            Log.e("NAM", "On Route");
        }

        Integer a = 14, b = 14;
        System.out.println(a == b);
        Integer c = 256, d = 256;
        System.out.println(c == d);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        txtSearchLocation = (TextView) findViewById(R.id.txtSearchLocation);
        ng_btn_close = (ImageButton) findViewById(R.id.btn_close);
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

        ng_btn_close.setVisibility(View.GONE);
        //call back
        if ((SearchRouteActivity.mapLocation.get(AppConstants.SearchField.TO_LOCATION) != null) && (isTrackingSearchLocation)) {
            txtSearchLocation.setText(SearchRouteActivity.mapLocation.get(AppConstants.SearchField.TO_LOCATION).getName());
            ng_btn_close.setVisibility(View.VISIBLE);
            SearchLocationTask locationTask = new SearchLocationTask();
            locationTask.execute();
        }

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
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_help:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation_item_reference:
                        Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.navigation_item_setting:
                        intent = new Intent(MainActivity.this, SettingActivity.class);
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

        //search location
        txtSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTrackingSearchLocation = true;
                Intent intent = new Intent(MainActivity.this, AutoCompleteSearchActivity.class);
                intent.putExtra("number", AppConstants.SearchField.TO_LOCATION);
                intent.putExtra("message", txtSearchLocation.getText());
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
            }
        });
        //float button
        fabMap = (FloatingActionButton) findViewById(R.id.fabMap);
        fabMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(MainActivity.this, SearchRouteActivity.class);

                    LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
                    boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if((statusOfGPS == true)&&(!SearchRouteActivity.flat_check_edittext_1)){
                        flatGPS = true;
                        SearchRouteActivity.mapLocation.put(AppConstants.SearchField.FROM_LOCATION,null);
                    }
                    startActivity(intent);
                    return true;
                }
                return true; // consume the event
            }
        });

        fab_gps = (FloatingActionButton) findViewById(R.id.fab_gps);
        fab_compass = (FloatingActionButton) findViewById(R.id.fab_compass);
        fab_compass.setVisibility(View.GONE);
        fab_gps.setVisibility(View.GONE);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    // initializeMap();
                    mapView.setFocusPos(markerPos, 0);
                    fab.setVisibility(View.GONE);
                    fab_compass.setVisibility(View.GONE);
                    fab_gps.setVisibility(View.VISIBLE);
                    return true;
                }
                return true; // consume the event
            }
        });

        fab_gps.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fab.setVisibility(View.GONE);
                fab_gps.setVisibility(View.GONE);
                fab_compass.setVisibility(View.VISIBLE);
                return true;
            }
        });

        fab_compass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fab_compass.setVisibility(View.GONE);
                fab_gps.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                return true;
            }
        });

        ng_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapLocation.get(AppConstants.SearchField.TO_LOCATION) != null) {
                    isTrackingSearchLocation = false;
                    mapLocation.remove(AppConstants.SearchField.TO_LOCATION);
                    txtSearchLocation.setText(null);
                    ng_btn_close.setVisibility(View.GONE);
                    MapPos ng_markerPos = mapView.getOptions().getBaseProjection().fromWgs84(new MapPos(106.628394, 10.855090));
                    mapView.setFocusPos(ng_markerPos, 1);
                    mapView.setZoom(10, 1);
                }
            }
        });


        SensorManager sensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mOrientationManager = new OrientationManager(sensorManager);

        mOrientationManager.addOnChangedListener(this);
        mOrientationManager.start();

        // SearchLocationDAL.deleteSearchLocation();

        /*handlerThread.removeCallbacks(whatMyName);
        handlerThread.post(whatMyName);*/
    }

    Handler handlerThread = new Handler();

    private Runnable whatMyName = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e("hqthao", "fuck fuck fuck");
                }
            }).start();

            // continue to run on next time
            handlerThread.postDelayed(this, 500);
        }
    };

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
        if (marker == null) {
            marker = NutiteqMapUtil.drawCurrentMarkerNutiteq(mapView, getResources(),
                    latitude, longitude, R.drawable.marker_cua_nam_burned);
            vectorDataSource.add(marker);
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
        if (marker != null) {
            marker.setRotation(360 - azimut);
        }
    }

    @Override
    public void onAccuracyChanged(OrientationManager orientationManager) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("NgoanTT-->", "MainActivity");
        AutocompleteObject autocompleteObject = SearchRouteActivity.mapLocation.get(AppConstants.SearchField.TO_LOCATION);
        if (autocompleteObject != null) {
            if((autocompleteObject.getName() != null) && (!autocompleteObject.getName().equals(""))){
                ng_btn_close.setVisibility(View.VISIBLE);
                txtSearchLocation.setText(SearchRouteActivity.mapLocation.get(AppConstants.SearchField.TO_LOCATION).getName());
                SearchLocationTask searchLocationTask = new SearchLocationTask();
                searchLocationTask.execute();
            }else {
                txtSearchLocation.setText(null);
            }
        } else {
            txtSearchLocation.setText(null);
        }

    }


    private class SearchLocationTask extends AsyncTask<Void, Void, BusLocation> {


        @Override
        protected BusLocation doInBackground(Void... params) {
            AutocompleteObject autocompleteObject = SearchRouteActivity.mapLocation.get(AppConstants.SearchField.TO_LOCATION);
            String url = GoogleAPIUtils.getLocationByPlaceID(autocompleteObject.getPlace_id());
            String json = NetworkUtils.download(url);
            BusLocation busLocation;
            try {
                busLocation = JSONParseUtils.getBusLocation(json, autocompleteObject.getName());
                if (busLocation != null) {
                    return busLocation;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(BusLocation location) {
            super.onPostExecute(location);
            if (location == null) {
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
                return;
            }
            ng_bus_location = location;
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            com.fpt.router.library.model.common.Location local = new com.fpt.router.library.model.common.Location();
            local.setLatitude(location.getLatitude());
            local.setLongitude(location.getLongitude());
            ng_markerPos = mapView.getOptions().getBaseProjection().fromWgs84(
                    new MapPos(location.getLongitude(), location.getLatitude()));
            if (ng_marker == null) {
                ng_marker = NutiteqMapUtil.drawCurrentMarkerNutiteq(mapView, getResources(),
                        latitude, longitude, R.drawable.ng_marker);
                vectorDataSource.add(ng_marker);
            } else {
                ng_marker.setPos(ng_markerPos);
            }
            mapView.setFocusPos(ng_markerPos, 0f);
            mapView.setZoom(24, 1);
        }
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
