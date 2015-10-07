package com.fpt.router.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.model.bus.ArrayAdapterItem;
import com.fpt.router.model.bus.DetailRoute;
import com.fpt.router.model.bus.ListRoute;
import com.fpt.router.model.bus.OnItemClickListenerListViewItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 10/5/2015.
 */
public class DetailGMActivity extends AppCompatActivity {
    private static final String TAG = "DetailGMActivity";
    public static final String SAVED_STATE_ACTION_BAR_HIDDEN = "saved_state_action_bar_hidden";
    private SlidingUpPanelLayout mLayout;


    // Google Map
    private GoogleMap googleMap;
    MarkerOptions marker;
    // latitude and longitude
    double latitude =10.853207 ;
    double longitude =106.629097 ;
    private Marker mLocationMarker;
    private LatLng mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_googlemap);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);



        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

        ListView lv = (ListView) findViewById(R.id.list_view);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DetailGMActivity.this, "onItemClick", Toast.LENGTH_SHORT).show();
            }
        });

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);

            }

            @Override
            public void onPanelCollapsed(View view) {
                Log.i(TAG, "onPanelExpanded");
                expandMap();
            }

            @Override
            public void onPanelExpanded(View view) {
                collapseMap();

            }

            @Override
            public void onPanelAnchored(View view) {

            }

            @Override
            public void onPanelHidden(View view) {

            }
        });

        ListRoute listRoute = new ListRoute();
        List<DetailRoute> routes = new ArrayList<DetailRoute>();
        routes = listRoute.getRoutes();

        ArrayAdapterItem adapterItem = new ArrayAdapterItem(this,R.layout.activity_list_row_gmap,routes);
        ListView listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(adapterItem);
        listview.setOnItemClickListener(new OnItemClickListenerListViewItem());
    }



    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(10.853207, 106.629097)).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            // create marker
            marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps ");


            // adding marker
            googleMap.addMarker(marker);

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }


    private LatLng getLastKnownLocation() {
        return getLastKnownLocation(true);
    }

    private LatLng getLastKnownLocation(boolean isMoveMarker) {
        LatLng latLng = new LatLng(latitude,longitude);
        if(isMoveMarker){
            moveMarker(latLng);
        }
        return latLng;
    }

    private void moveMarker(LatLng latLng) {
        if (mLocationMarker != null) {
            mLocationMarker.remove();
        }
        mLocationMarker = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker())
                .position(latLng).anchor(0.5f, 0.5f));
    }

    private void moveToLocation(Location location) {
        if (location == null) {
            return;
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        moveToLocation(latLng);
    }

    private void moveToLocation(LatLng latLng) {
        moveToLocation(latLng, true);
    }

    private void moveToLocation(LatLng latLng, final boolean moveCamera) {
        if (latLng == null) {
            return;
        }
        moveMarker(latLng);
        mLocation = latLng;

    }

    private void collapseMap() {

        if (googleMap != null && mLocation != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 11f), 1000, null);
        }

    }

    private void expandMap() {

        if (googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(14f), 1000, null);
        }
    }


}
