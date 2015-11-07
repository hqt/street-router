package com.fpt.router.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fpt.router.R;
import com.fpt.router.activity.SearchRouteActivity;
import com.fpt.router.fragment.base.AbstractMapFragment;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.adapter.RouteItemAdapter;
import com.fpt.router.library.model.common.NotifyModel;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.model.motorbike.Step;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.library.utils.MotorMapUtils;
import com.fpt.router.service.GPSServiceOld;
import com.fpt.router.widget.LockableListView;
import com.fpt.router.library.utils.MapUtils;
import com.fpt.router.widget.SlidingUpPanelLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
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
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 10/12/2015.
 */
public class MotorDetailFragment extends AbstractMapFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
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

    private Marker now;
    private Marker markerStep;

    int position;
    List<LatLng> list;
    String encodedString;
    List<Leg> listLeg = SearchRouteActivity.listLeg;
    Leg leg;
    List<Step> listStep = new ArrayList<>();
    List<Leg> listFinalLeg = new ArrayList<>();
    private RouteItemAdapter adapterItem;

    public MotorDetailFragment() {
    }

    public static MotorDetailFragment newInstance(int position) {
        MotorDetailFragment fragment = new MotorDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("position", position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bus_detail_twopoint, container, false);

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
        if(SearchRouteActivity.mapLocation.size() == 2) {
            listFinalLeg.add(listLeg.get(position));
        } else if (SearchRouteActivity.mapLocation.size() == 3) {
            for(int n = position*2; n < position*2+2; n++) {
                listFinalLeg.add(listLeg.get(n));
            }
        } else {
            for(int n = position*3; n < position*3+3; n++) {
                listFinalLeg.add(listLeg.get(n));
            }
        }

        for(int n = 0; n < listFinalLeg.size(); n ++) {
            listStep.addAll(listFinalLeg.get(n).getSteps());
        }
        GPSServiceOld.setListNotify(getNotifyList());
        adapterItem = new RouteItemAdapter(getContext(), R.layout.activity_list_row_gmap, listStep);

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
                .addApi(Wearable.API)
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

                mMap.getUiSettings().setCompassEnabled(false);
                mMap.getUiSettings().setZoomControlsEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                if(SearchRouteActivity.mapLocation.size() == 2) {
                    MotorMapUtils.drawMapWithTwoPoint(mMap, listFinalLeg);

                } else {
                    MotorMapUtils.drawMapWithFourPoint(mMap, listFinalLeg);
                    List<LatLng> step = new ArrayList<>();
                }
                for(int n = 0; n < listStep.size(); n++) {
                    MapUtils.drawPointIcon(mMap,
                            listStep.get(n).getDetailLocation().getStartLocation().getLatitude(),
                            listStep.get(n).getDetailLocation().getStartLocation().getLongitude(),
                            "", R.drawable.info);
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
        // send location request
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setNumUpdates(1);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        // send data to wear
        // Create a DataMap object and send it to the data layer
        DataMap dataMap = new DataMap();
        //Requires a new thread to avoid blocking the UI
        new SendToDataLayerThread(AppConstants.PATH.MESSAGE_PATH_MOTOR, dataMap).start();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void drawCurrentLocation(Double lat, Double lng) {
        if(now != null){
            now.remove();
        }
        now = MapUtils.drawPointColor(mMap, lat, lng, "", BitmapDescriptorFactory.HUE_RED);
    }

    @Override
    public List<LatLng> getFakeGPSList() {
        List<LatLng> listFakeGPS = DecodeUtils.getListLocationToFakeGPS(listFinalLeg, SearchRouteActivity.optimize);
        return listFakeGPS;
    }

    @Override
    public List<NotifyModel> getNotifyList() {
        List<NotifyModel>  listNotifies = new ArrayList<>();
        for(int i = 0; i < listStep.size(); i++) {
            com.fpt.router.library.model.common.Location location = listStep.get(i).
                    getDetailLocation().getStartLocation();
            String smallTittle = listStep.get(i).getManeuver();
            String longTittle = "Thông tin chi tiết";
            Pair<String, String> detailInstruction = DecodeUtils.getDetailInstruction(
                    listStep.get(i).getInstruction());
            String smallMessage = detailInstruction.first;
            String longMessage = detailInstruction.second;
            NotifyModel notifyModel = new NotifyModel(location, smallTittle, longTittle, smallMessage, longMessage);
            listNotifies.add(notifyModel);
        }
        return listNotifies;
    }

    class SendToDataLayerThread extends Thread {
        String path;
        DataMap dataMap;

        // Constructor for sending data objects to the data layer
        SendToDataLayerThread(String p, DataMap data) {
            path = p;
            dataMap = data;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();

            for (Node node : nodes.getNodes()) {

                // method 1. send over the data layer. This is reliability way for sending data.
                // If a connection is unavailable when data is posted to the Data API,
                // it will automatically synchronize with the other device once the connection is reestablished.
                // this method shares and synchronizes data both devices
                // Use when:
                //  . synchronized data that might be modified on both side
                //  . system will manage and cache data
                //  . one-way or two-way communication.

                Leg leg = new Leg();
                ArrayList<DataMap> dataMaps = leg.listModelToDataMap(listFinalLeg);

                PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(path);
                putDataMapRequest.getDataMap().putDataMapArrayList("list_leg", dataMaps);
                putDataMapRequest.getDataMap().putLong("time", new Date().getTime());

                PutDataRequest request = putDataMapRequest.asPutDataRequest();

                // DataItems share among devices and contain small amounts of data. A DataItem has 2 parts:
                //  1. Path: Like Message API, unique string such as com/fpt/hqt
                //  2. Payload: a byte array limited to 100KB
                PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient, request);

                /*// asynchronous call
                pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(DataApi.DataItemResult dataItemResult) {

                    }
                });*/

                // synchronous call
                DataApi.DataItemResult result = pendingResult.await();
                if (result.getStatus().isSuccess()) {
                } else {
                    // Log an error
                }

                // method 2. send message. One-way message communication
                // once the message is sent. there is no confirmation that they were received
                // Use when:
                //  . immediately invoke action on other device, such as start an activity, start/stop music
                //  . one-way communication
                //  . don't want system to manage and cache messages
                //Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), MessagePath.MESSAGE_PATH, null);

                // method 3. send asset. When data is larger than 100KB
                // Asset asset;
                DataMap dataMap;

                // method 4. ChanelApi.


            }
        }
    }

}