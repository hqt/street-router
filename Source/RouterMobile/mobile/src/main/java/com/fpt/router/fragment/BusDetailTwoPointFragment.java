package com.fpt.router.fragment;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.adapter.BusDetailAdapter;
import com.fpt.router.fragment.base.AbstractNutiteqMapFragment;
import com.fpt.router.framework.OrientationManager;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.bus.Segment;
import com.fpt.router.library.model.common.Location;
import com.fpt.router.library.model.common.NotifyModel;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.library.utils.JSONUtils;
import com.fpt.router.library.utils.StringUtils;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.nutiteq.core.MapPos;
import com.nutiteq.core.MapVec;
import com.nutiteq.datasources.LocalVectorDataSource;
import com.nutiteq.layers.VectorLayer;
import com.nutiteq.utils.AssetUtils;
import com.nutiteq.vectorelements.NMLModel;
import com.fpt.router.framework.OrientationManager.OnChangedListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 10/13/2015.
 */
public class BusDetailTwoPointFragment extends AbstractNutiteqMapFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        SlidingUpPanelLayout.PanelSlideListener, LocationListener, OnChangedListener {

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
    private Marker now;

    private SupportMapFragment mMapFragment;

    private GoogleMap mMap;
    private boolean mIsNeedLocationUpdate = true;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Toolbar toolbar;
    private OrientationManager mOrientationManager;
    Result result;
    private List<INode> iNodeList;

    List<Path> pathFinal = new ArrayList<>();
    private BusDetailAdapter adapterItem;
    LocalVectorDataSource vectorDataSource;
    VectorLayer vectorLayer;

    public BusDetailTwoPointFragment() {
    }


    public static BusDetailTwoPointFragment newInstance(Result result) {
        BusDetailTwoPointFragment f = new BusDetailTwoPointFragment();
        Bundle args = new Bundle();
        args.putSerializable("result", result);
        f.setArguments(args);
        return f;
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

        result = (Result) getArguments().getSerializable("result");

        /** start get list step and show  */

        iNodeList = result.nodeList;

        List<Path> paths = new ArrayList<>();
        /**
         * Middle location
         */
        List<Segment> segments = new ArrayList<Segment>();
        for (int i = 0; i < iNodeList.size(); i++) {
            if (iNodeList.get(i) instanceof Segment) {
                Segment segment = (Segment) iNodeList.get(i);
                segments.add(segment);
            }
        }

        for (int m = 0; m < segments.size(); m++) {
            paths = segments.get(m).paths;
            pathFinal.add(paths.get(0));
        }

        /**
         * start location
         */
        if (iNodeList.get(0) instanceof Path) {
            Path path = (Path) iNodeList.get(0);
            pathFinal.add(path);
        }
        /**
         * end location
         */
        if (iNodeList.get(iNodeList.size() - 1) instanceof Path) {
            Path path = (Path) iNodeList.get(iNodeList.size() - 1);
            pathFinal.add(path);
        }

        GPSServiceOld.setListNotify(getNotifyList());
        adapterItem = new BusDetailAdapter(getContext(), R.layout.adapter_show_detail_bus_steps, iNodeList);

        mListView.addHeaderView(mTransparentHeaderView);
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
        NutiteqMapUtil.drawMapWithBusTwoPoint(mapView, vectorDataSource, getResources(), baseProjection, result);
        /*for (int n = 1; n < pathFinal.size(); n++) {
            Path path = pathFinal.get(n);
            NutiteqMapUtil.drawMarkerNutiteq(mapView, vectorDataSource, getResources(),
                    path.stationFromLocation.getLatitude(),
                    path.stationFromLocation.getLongitude(),
                    R.drawable.orange_small);
        }*/

    }


    @Override
    public void onResume() {
        super.onResume();
        // In case Google Play services has since become available.
        drawMap();
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

    private void moveToLocation(android.location.Location location) {
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
    public void onLocationChanged(android.location.Location location) {
        if (mIsNeedLocationUpdate) {
            moveToLocation(location);
        }
    }

    static int count = 0;

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

        //Requires a new thread to avoid blocking the UI
        new SendToDataLayerThread(AppConstants.PATH.MESSAGE_PATH_BUS_TWO_POINT, dataMap).start();
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
        if (model == null) {
            model = new NMLModel(markerPos, AssetUtils.loadBytes("bus32.nml"));
            model.setScale(5);
            vectorDataSource.add(model);
        } else {
            model.setPos(markerPos);
        }
        if(GPS_ON_FLAG) {
            mapView.setFocusPos(markerPos, 0f);
        }

    }

    @Override
    public List<LatLng> getFakeGPSList() {
        List<LatLng> latLngList = DecodeUtils.getListLocationFromNodeList(iNodeList);
        return latLngList;
    }

    @Override
    public List<NotifyModel> getNotifyList() {
        List<NotifyModel> listNotifies = new ArrayList<>();
        for (int i = 0; i < pathFinal.size(); i++) {
            Path path = pathFinal.get(i);
            Location location = new Location(path.stationFromLocation.getLatitude(), path.stationFromLocation.getLongitude());
            String smallTittle = "Gần đến trạm";
            String longTittle = "Thông tin chi tiết";
            String smallMessage = path.stationFromName;
            String longMessage = "Chi tiết này để sau";
            NotifyModel notifyModel = new NotifyModel(location, smallTittle, longTittle, smallMessage, longMessage);
            listNotifies.add(notifyModel);

        }
        Path path = pathFinal.get(pathFinal.size() - 1);
        Location location = new Location(path.stationToLocation.getLatitude(), path.stationToLocation.getLongitude());
        String smallTittle = "Gần đến trạm";
        String longTittle = "Thông tin chi tiết";
        String smallMessage = path.stationToName;
        String longMessage = "Chi tiết này để sau";
        NotifyModel notifyModel = new NotifyModel(location, smallTittle, longTittle, smallMessage, longMessage);
        listNotifies.add(notifyModel);
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

                // a. convert journey to json again
                Gson gson = JSONUtils.buildGson();
                String json = gson.toJson(result);

                // b. convert string to asset
                Asset asset = StringUtils.convertStringToAsset(json);

                // b. put to data map.

                PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(path);
                putDataMapRequest.getDataMap().putAsset("result_json", asset);
                putDataMapRequest.getDataMap().putLong("time_stamp", new Date().getTime());

                PutDataRequest request = putDataMapRequest.asPutDataRequest();

                // DataItems share among devices and contain small amounts of data. A DataItem has 2 parts:
                //  1. Path: Like Message API, unique string such as com/fpt/hqt
                //  2. Payload: a byte array limited to 100KB. if not. must use asset object
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
                    Log.e("hqthao", "Sending bus data successfully");
                } else {
                    Log.e("hqthao", "Sending bus data failed");
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

                // method 4. ChanelApi.


            }
        }
    }

}

