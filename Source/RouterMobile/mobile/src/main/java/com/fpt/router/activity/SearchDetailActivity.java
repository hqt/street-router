package com.fpt.router.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.fpt.router.R;
import com.fpt.router.fragment.AbstractMapFragment;
import com.fpt.router.fragment.BusDetailFourPointFragment;
import com.fpt.router.fragment.BusDetailTwoPointFragment;
import com.fpt.router.fragment.MotorDetailFourPointFragment;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.message.LocationMessage;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.library.utils.MapUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class SearchDetailActivity extends AppCompatActivity implements LocationListener,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{
    private EventBus bus = EventBus.getDefault();
    private GoogleApiClient mGoogleApiClient;
    AbstractMapFragment fragment;
    private int position;
    List<Leg> listLeg = SearchRouteActivity.listLeg;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        Result result = (Result)getIntent().getSerializableExtra("result");
        Journey journey = (Journey) getIntent().getSerializableExtra("journey");
        position = getIntent().getIntExtra("position", -1);
        if(result != null){
            if (savedInstanceState == null) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                trans.add(R.id.fragment, BusDetailTwoPointFragment.newInstance(result));
                trans.commit();
            }
        }

        if(position != -1){
            if (savedInstanceState == null) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                fragment = MotorDetailFourPointFragment.newInstance(position);
                trans.add(R.id.fragment, fragment);
                trans.commit();
            }
        }

        if(journey != null){
            if (savedInstanceState == null) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                trans.add(R.id.fragment, BusDetailFourPointFragment.newInstance(journey));
                trans.commit();
            }
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        // Intent intent = new Intent(MainActivity.this, GPSServiceOld.class);
        // startService(intent);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onEvent(LocationMessage event){
        Log.e("hqthao", event.location.getLatitude() + "");
        onLocationChanged(event.location);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("Nam", "Nam dep trai");
        List<Leg> listFinalLeg = new ArrayList<>();
        if(SearchRouteActivity.listLocation.size() == 2) {
            listFinalLeg.add(listLeg.get(position));
        } else if (SearchRouteActivity.listLocation.size() == 3) {
            listFinalLeg.addAll(listLeg);
        } else {
            for(int n = position*3; n < position*3+3; n++) {
                listFinalLeg.add(listLeg.get(n));
            }
        }
        List<LatLng> listLatLng = DecodeUtils.getListLocationToFakeGPS(listFinalLeg);
        double latitude = listLatLng.get(count).latitude;
        double longitude = listLatLng.get(count).longitude;
        if(count == listLatLng.size()) {
            count = 0 ;
        } else {
            count++;
        }
        fragment.drawCurrentLocation(latitude, longitude);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
