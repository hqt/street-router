package com.fpt.router.fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fpt.router.R;
import com.fpt.router.activity.SearchRouteActivity;
import com.fpt.router.adapter.RouteItemAdapter;
import com.fpt.router.fragment.base.AbstractNutiteqMapFragment;
import com.fpt.router.framework.OrientationManager;
import com.fpt.router.framework.OrientationManager.OnChangedListener;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.common.NotifyModel;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.model.motorbike.Step;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.service.GPSServiceOld;
import com.fpt.router.utils.NutiteqMapUtil;
import com.fpt.router.widget.LockableListView;
import com.fpt.router.widget.SlidingUpPanelLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.nutiteq.core.MapPos;
import com.nutiteq.core.MapVec;
import com.nutiteq.datasources.LocalVectorDataSource;
import com.nutiteq.layers.VectorLayer;
import com.nutiteq.utils.AssetUtils;
import com.nutiteq.vectorelements.Marker;
import com.nutiteq.vectorelements.NMLModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 10/12/2015.
 */
public class MotorNutiteqDetailFragment extends AbstractNutiteqMapFragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        SlidingUpPanelLayout.PanelSlideListener, LocationListener, OnChangedListener {

    private static final String ARG_LOCATION = "arg.location";
    // latitude and longitude
    double latitude = 10.853207;
    double longitude = 106.629097;

    private LockableListView mListView;
    private SlidingUpPanelLayout mSlidingUpPanelLayout;

    private OrientationManager mOrientationManager;

    private View mTransparentHeaderView;
    private View mTransparentView;
    private View mSpaceView;

    private LatLng mLocation;

    private SupportMapFragment mMapFragment;

    private boolean mIsNeedLocationUpdate = true;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Toolbar toolbar;

    private Marker now;

    int position;
    List<LatLng> list;
    String encodedString;
    List<Leg> listLeg = SearchRouteActivity.listLeg;
    Leg leg;
    List<Step> listStep = new ArrayList<>();
    List<Leg> listFinalLeg = new ArrayList<>();
    private RouteItemAdapter adapterItem;
    LocalVectorDataSource vectorDataSource;
    NMLModel modelCar;
    VectorLayer vectorLayer;

    public MotorNutiteqDetailFragment() {
    }

    public static MotorNutiteqDetailFragment newInstance(int position) {
        MotorNutiteqDetailFragment fragment = new MotorNutiteqDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("position", position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

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

        position = (int) getArguments().getSerializable("position");


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
        vectorDataSource = new LocalVectorDataSource(baseProjection);
        // Initialize a vector layer with the previous data source
        vectorLayer = new VectorLayer(vectorDataSource);
        // Add the previous vector layer to the map
        mapView.getLayers().add(vectorLayer);
        drawMap();
    }

    private void drawMap() {
        if(SearchRouteActivity.mapLocation.size() == 2) {
            NutiteqMapUtil.drawMapWithTwoPoint(mapView, vectorDataSource, getResources(), baseProjection, listFinalLeg);

        } else {
            NutiteqMapUtil.drawMapWithFourPoint(mapView, vectorDataSource, getResources(), baseProjection, listFinalLeg);
            List<LatLng> step = new ArrayList<>();
        }
        for(int n = 0; n < listStep.size(); n++) {
            NutiteqMapUtil.drawMarkerNutiteq(mapView, vectorDataSource, getResources(),
                    listStep.get(n).getDetailLocation().getStartLocation().getLatitude(),
                    listStep.get(n).getDetailLocation().getStartLocation().getLongitude(),
                    R.drawable.orange_small);
        }

        SensorManager sensorManager =
                (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        mOrientationManager = new OrientationManager(sensorManager);

        mOrientationManager.addOnChangedListener(this);
        mOrientationManager.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        // In case Google Play services has since become available.
        drawMap();
    }

    @Override
    public void onPause() {
        mGoogleApiClient.disconnect();
        super.onPause();
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

        if (isMoveMarker) {
        }
        return latLng;
    }

    private LatLng getLastKnownLocation(boolean isMoveMarker, LatLng latLng) {

        if (isMoveMarker) {
        }
        return latLng;
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
        }

        mLocation = latLng;
        mListView.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private void collapseMap() {
        mSpaceView.setVisibility(View.VISIBLE);
        mTransparentView.setVisibility(View.GONE);

        mListView.setScrollingEnabled(true);
    }

    private void expandMap() {
        mSpaceView.setVisibility(View.GONE);
        mTransparentView.setVisibility(View.INVISIBLE);

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
        new SendToDataLayerThread(AppConstants.PATH.MESSAGE_PATH_FOUR_POINT, dataMap).start();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void drawCurrentLocation(Double lat, Double lng) {
        MapPos markerPos = mapView.getOptions().getBaseProjection().fromWgs84(new MapPos(lng, lat));
        if(modelCar == null){
            modelCar = new NMLModel(markerPos, AssetUtils.loadBytes("ferrari360.nml"));
            modelCar.setScale(400);
            vectorDataSource.add(modelCar);
        } else {
            modelCar.setPos(markerPos);
            mapView.setFocusPos(markerPos, 0f);
        }

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

    @Override
    public void onOrientationChanged(OrientationManager orientationManager) {
        float azimut = orientationManager.getHeading(); // orientation contains: azimut, pitch and roll
        //System.out.println(azimut);
        mapView.setMapRotation(360 - azimut, 0f);
        if(modelCar != null) {
            modelCar.setRotation(new MapVec(0, 0, 1), 360-azimut);
        }
    }

    @Override
    public void onAccuracyChanged(OrientationManager orientationManager) {

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