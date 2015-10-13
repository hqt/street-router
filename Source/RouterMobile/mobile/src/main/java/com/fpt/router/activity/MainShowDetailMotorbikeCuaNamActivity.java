package com.fpt.router.activity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fpt.router.R;
import com.fpt.router.fragment.MotorbikeFragmentCuaNam;
import com.fpt.router.model.bus.ArrayAdapterItem;
import com.fpt.router.model.motorbike.DetailLocation;
import com.fpt.router.model.motorbike.Leg;
import com.fpt.router.model.motorbike.RouterDetailFourPoint;
import com.fpt.router.model.motorbike.RouterDetailTwoPoint;
import com.fpt.router.model.motorbike.Step;
import com.fpt.router.utils.DecodeUtils;
import com.fpt.router.utils.LockableListView;
import com.fpt.router.utils.MapUtils;
import com.fpt.router.utils.SlidingUpPanelLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 10/12/2015.
 */
public class MainShowDetailMotorbikeCuaNamActivity extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        SlidingUpPanelLayout.PanelSlideListener, LocationListener {

    private static final String ARG_LOCATION = "arg.location";
    // latitude and longitude
    double latitude = 10.853207;
    double longitude = 106.629097;

    private LockableListView mListView;
    private SlidingUpPanelLayout mSlidingUpPanelLayout;

    private View mTransparentHeaderView;
    private View mTransparentView;
    private View mSpaceView;

    private LatLng mLocation;
    private Marker mLocationMarker;

    private SupportMapFragment mMapFragment;

    private GoogleMap mMap;
    private boolean mIsNeedLocationUpdate = true;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Toolbar toolbar;

    int position;
    List<LatLng> list;
    String encodedString;
    List<Leg> listLeg = MotorbikeFragmentCuaNam.listLeg;
    Leg leg;
    List<Step> listStep = new ArrayList<>();
    private ArrayAdapterItem adapterItem;

    public MainShowDetailMotorbikeCuaNamActivity() {
    }

    public static MainShowDetailMotorbikeCuaNamActivity newInstance(int position) {
        MainShowDetailMotorbikeCuaNamActivity fragment = new MainShowDetailMotorbikeCuaNamActivity();
        Bundle args = new Bundle();
        args.putSerializable("position", position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        mListView = (LockableListView) rootView.findViewById(android.R.id.list);
        mListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);

        mSlidingUpPanelLayout = (SlidingUpPanelLayout) rootView.findViewById(R.id.slidingLayout);
        mSlidingUpPanelLayout.setEnableDragViewTouchEvents(true);

        int mapHeight = getResources().getDimensionPixelSize(R.dimen.map_height);
        int panelHeight = getResources().getDimensionPixelSize(R.dimen.panel_height);
        /*int panelHeight = 50;*/
        mSlidingUpPanelLayout.setPanelHeight(panelHeight); // you can use different height here
        mSlidingUpPanelLayout.setScrollableView(mListView, mapHeight);

        mSlidingUpPanelLayout.setPanelSlideListener(this);

        // transparent view at the top of ListView
        mTransparentView = rootView.findViewById(R.id.transparentView);

        // init header view for ListView
        mTransparentHeaderView = inflater.inflate(R.layout.transparent_header_view, mListView, false);
        mSpaceView = mTransparentHeaderView.findViewById(R.id.space);

        collapseMap();

        mSlidingUpPanelLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mSlidingUpPanelLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mSlidingUpPanelLayout.onPanelDragged(0);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMapFragment = SupportMapFragment.newInstance();

        position = (int) getArguments().getSerializable("position");

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mapContainer, mMapFragment, "map");
        fragmentTransaction.commit();

        /** start get list step and show  */
        Log.e("Nam", listLeg.get(0).getStep().get(0).getInstruction());

        for(int n = 0; n < listLeg.size(); n ++) {
            listStep.addAll(listLeg.get(n).getStep());
        }
        adapterItem = new ArrayAdapterItem(getContext(), R.layout.activity_list_row_gmap, listStep);

        mListView.addHeaderView(mTransparentHeaderView);
       /* mListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, testData));*/
        mListView.setAdapter(adapterItem);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSlidingUpPanelLayout.collapsePane();
            }
        });
        /** end get list step and show */

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = mMapFragment.getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                List<Leg> listFinalLeg = new ArrayList<>();
                if(listLeg.size() == 2){
                    listFinalLeg.addAll(listLeg);
                } else {
                    for(int n = position*3; n < position*3+3; n++) {
                        listFinalLeg.add(listLeg.get(n));
                    }
                }
                mMap.getUiSettings().setCompassEnabled(false);
                mMap.getUiSettings().setZoomControlsEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                for (int i = 0; i < listFinalLeg.size(); i++) {
                    leg = listLeg.get(i);
                    DetailLocation detalL = leg.getDetailLocation();
                    com.fpt.router.model.motorbike.Location start_location = detalL.getStart_location();
                    com.fpt.router.model.motorbike.Location end_location = detalL.getEnd_location();
                    // latitude and longitude

                    if(i == 0) {
                        latitude = end_location.getLatitude();
                        longitude = end_location.getLongitude();
                        MapUtils.drawPointColor(mMap, latitude, longitude, leg.getEndAddress(), BitmapDescriptorFactory.HUE_YELLOW);

                        latitude = start_location.getLatitude();
                        longitude = start_location.getLongitude();
                        MapUtils.drawPointColor(mMap, latitude, longitude, leg.getStartAddress(), BitmapDescriptorFactory.HUE_GREEN);
                    }
                    if (i == listFinalLeg.size()-1) {
                        latitude = end_location.getLatitude();
                        longitude = end_location.getLongitude();
                        MapUtils.drawPointColor(mMap, latitude, longitude, leg.getEndAddress(), BitmapDescriptorFactory.HUE_RED);

                        latitude = start_location.getLatitude();
                        longitude = start_location.getLongitude();
                        MapUtils.drawPointColor(mMap, latitude, longitude, leg.getStartAddress(), BitmapDescriptorFactory.HUE_YELLOW);
                    }
                    LatLng latLng = new LatLng(latitude, longitude);
                    moveToLocation(latLng, true);

                    //add polyline
                    encodedString = leg.getOverview_polyline();
                    list = DecodeUtils.decodePoly(encodedString);
                    MapUtils.drawLine(mMap, list, Color.BLUE);
                    MapUtils.moveCamera(mMap, latitude, longitude, 12);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // In case Google Play services has since become available.
        setUpMapIfNeeded();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Connect the client.
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private LatLng getLastKnownLocation() {
        return getLastKnownLocation(true);
    }

    private LatLng getLastKnownLocation(boolean isMoveMarker) {
        LatLng latLng = new LatLng(latitude, longitude);
        /*boolean isGPSEnabled = false;
        boolean isNetworkEnabled = false;
        LocationManager lm = (LocationManager) TheApp.getAppContext().getSystemService(Context.LOCATION_SERVICE);

        // getting GPS status
       isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        String provider = lm.getBestProvider(criteria, true);
        if (provider == null) {
            return null;
        }
        Location loc = lm.getLastKnownLocation(provider);
        if (loc != null) {
            LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
            if (isMoveMarker) {
                moveMarker(latLng);
            }
            return latLng;
        }
        return null;*/
        if (isMoveMarker) {
            moveMarker(latLng);
        }
        return latLng;
    }

    private LatLng getLastKnownLocation(boolean isMoveMarker, LatLng latLng) {

        if (isMoveMarker) {
            moveMarker(latLng);
        }
        return latLng;
    }

    private void moveMarker(LatLng latLng) {
        if (mLocationMarker != null) {
            mLocationMarker.remove();
        }
        mLocationMarker = mMap.addMarker(new MarkerOptions()
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

        if (!moveCamera) {
            moveMarker(latLng);
        }

        mLocation = latLng;
        mListView.post(new Runnable() {
            @Override
            public void run() {
                if (mMap != null && moveCamera) {
                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(mLocation, 11.0f)));
                }
            }
        });
    }

    private void collapseMap() {
        mSpaceView.setVisibility(View.VISIBLE);
        mTransparentView.setVisibility(View.GONE);
        if (mMap != null && mLocation != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 11f), 1000, null);
        }
        mListView.setScrollingEnabled(true);
    }

    private void expandMap() {
        mSpaceView.setVisibility(View.GONE);
        mTransparentView.setVisibility(View.INVISIBLE);
        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14f), 1000, null);
        }
        mListView.setScrollingEnabled(false);
    }

    @Override
    public void onPanelSlide(View view, float v) {
    }

    @Override
    public void onPanelCollapsed(View view) {
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
    public void onLocationChanged(Location location) {
        if (mIsNeedLocationUpdate) {
            moveToLocation(location);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setNumUpdates(1);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}