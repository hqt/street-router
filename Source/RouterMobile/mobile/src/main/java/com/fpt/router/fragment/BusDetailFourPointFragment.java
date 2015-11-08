package com.fpt.router.fragment;

import android.graphics.Color;
import android.os.Bundle;
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
import com.fpt.router.adapter.BusDetailFourAdapter;
import com.fpt.router.fragment.base.AbstractMapFragment;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.bus.Segment;
import com.fpt.router.library.model.common.NotifyModel;
import com.fpt.router.library.model.common.Location;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.library.utils.JSONUtils;
import com.fpt.router.library.utils.MapUtils;
import com.fpt.router.library.utils.StringUtils;
import com.fpt.router.service.GPSServiceOld;
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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ngoan on 10/23/2015.
 */
public class BusDetailFourPointFragment extends AbstractMapFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
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
    private Marker now;
    private boolean mIsNeedLocationUpdate = true;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Toolbar toolbar;

    Result result;

    List<INode> iNodeList;

    private List<Path> paths;
    private BusDetailFourAdapter detailFourAdapter;
    private List<Location> points = new ArrayList<>();
    Journey journey;
    List<Result> results = new ArrayList<Result>();
    List<Path> pathFinal = new ArrayList<>();

    public BusDetailFourPointFragment() {
    }


    public static BusDetailFourPointFragment newInstance(Journey journey) {
        BusDetailFourPointFragment f = new BusDetailFourPointFragment();
        Bundle args = new Bundle();
        args.putSerializable("journey", journey);
        f.setArguments(args);
        return f;
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

        journey = (Journey) getArguments().getSerializable("journey");

        mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mapContainer, mMapFragment, "map");
        fragmentTransaction.commit();

        /** start get list step and show  */

        results = journey.results;

        /*adapterItem = new BusDetailAdapter(getContext(),R.layout.adapter_show_detail_bus_steps,iNodeListFourPoint);*/
        detailFourAdapter = new BusDetailFourAdapter(getContext(), R.layout.detail_four_point_view, results);
        mListView.addHeaderView(mTransparentHeaderView);
      /* mListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, steps));*/
        mListView.setAdapter(detailFourAdapter);
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


                for (int k = 0; k < results.size(); k++) {
                    List<LatLng> listFinal = new ArrayList<LatLng>();
                    result = results.get(k);
                    iNodeList = result.nodeList;

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
                        for (int j = 0; j < paths.size(); j++) {
                            points = paths.get(j).points;
                            for (int n = 0; n < points.size(); n++) {
                                LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                                listFinal.add(latLng);
                            }
                        }
                    }

                    /**
                     * start location
                     */
                    if (iNodeList.get(0) instanceof Path) {
                        Path path = (Path) iNodeList.get(0);
                        Location startLocation = path.stationFromLocation;
                        latitude = startLocation.getLatitude();
                        longitude = startLocation.getLongitude();
                        MapUtils.drawStartPoint(mMap, latitude, longitude, path.stationFromName);
                        LatLng startLatLng = new LatLng(path.stationFromLocation.getLatitude(), path.stationFromLocation.getLongitude());
                        moveToLocation(startLatLng, true);
                        LatLng endLatLng = new LatLng(listFinal.get(0).latitude, listFinal.get(0).longitude);
                        List<LatLng> startList = new ArrayList<>();
                        startList.add(startLatLng);
                        startList.add(endLatLng);
                        pathFinal.add(path);
                        points = path.points;
                        if (points == null) {
                            MapUtils.drawDashedPolyLine(mMap, startList, Color.parseColor("#FF5722"));
                        } else {
                            List<LatLng> list = new ArrayList<>();


                            for (int n = 0; n < points.size(); n++) {
                                LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                                list.add(latLng);
                            }
                            MapUtils.drawDashedPolyLine(mMap, list, Color.parseColor("#FF5722"));
                        }
                    }


                    /**
                     * end location
                     */
                    if (iNodeList.get(iNodeList.size() - 1) instanceof Path) {
                        Path path = (Path) iNodeList.get(iNodeList.size() - 1);
                        Location endLocation = path.stationToLocation;
                        latitude = endLocation.getLatitude();
                        longitude = endLocation.getLongitude();
                        MapUtils.drawEndPoint(mMap, latitude, longitude, path.stationToName);
                        LatLng startLatLng = new LatLng(listFinal.get(listFinal.size() - 1).latitude, listFinal.get(listFinal.size() - 1).longitude);
                        LatLng endLatLng = new LatLng(path.stationToLocation.getLatitude(), path.stationToLocation.getLongitude());
                        List<LatLng> endList = new ArrayList<>();
                        endList.add(startLatLng);
                        endList.add(endLatLng);
                        pathFinal.add(path);
                        points = path.points;
                        if (points == null) {
                            MapUtils.drawDashedPolyLine(mMap, endList, Color.parseColor("#FF5722"));
                        } else {
                            List<LatLng> list = new ArrayList<>();
                            for (int n = 0; n < points.size(); n++) {
                                LatLng latLng = new LatLng(points.get(n).getLatitude(), points.get(n).getLongitude());
                                list.add(latLng);
                            }
                            MapUtils.drawDashedPolyLine(mMap, list, Color.parseColor("#FF5722"));
                        }


                    }
                    //add polyline
                    MapUtils.drawLine(mMap, listFinal, Color.BLUE);
                    MapUtils.moveCamera(mMap, latitude, longitude, 12);

                    for (int n = 1; n < pathFinal.size(); n++) {
                        Path path = pathFinal.get(n);
                        MapUtils.drawPointIcon(mMap,
                                path.stationFromLocation.getLatitude(),
                                path.stationFromLocation.getLongitude(),
                                "", R.drawable.info);
                    }
                    GPSServiceOld.setListNotify(getNotifyList());
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

    static int fuck = 0;

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
        new SendToDataLayerThread(AppConstants.PATH.MESSAGE_PATH_BUS_FOUR_POINT, dataMap).start();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void drawCurrentLocation(Double lat, Double lng) {
        if (now != null) {
            now.remove();
        }
        now = MapUtils.drawPointColor(mMap, lat, lng, "", BitmapDescriptorFactory.HUE_RED);
    }

    @Override
    public List<LatLng> getFakeGPSList() {
        List<Result> results = journey.results;
        List<LatLng> latLngs = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            Result result = results.get(i);
            List<INode> iNodeList = result.nodeList;
            List<LatLng> latLngList = DecodeUtils.getListLocationFromNodeList(iNodeList);
            for (LatLng latLng : latLngList) {
                latLngs.add(latLng);
            }
        }
        return latLngs;
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

    static int count = 0;

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
                String json = gson.toJson(journey);

                // b. convert string to asset
                Asset asset = StringUtils.convertStringToAsset(json);

                // b. put to data map.

                PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(path);
                putDataMapRequest.getDataMap().putAsset("journey_json", asset);
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