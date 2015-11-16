package com.fpt.router.fragment;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fpt.router.R;
import com.fpt.router.adapter.BusDetailAdapter;
import com.fpt.router.adapter.BusDetailFourAdapter;
import com.fpt.router.fragment.base.AbstractNutiteqMapFragment;
import com.fpt.router.framework.OrientationManager;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Journey;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fpt.router.framework.OrientationManager.*;

/**
 * Created by ngoan on 10/23/2015.
 */
public class BusDetailFourPointFragment extends AbstractNutiteqMapFragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        OnChangedListener {

    private Marker now;
    private GoogleApiClient mGoogleApiClient;
    private BusDetailFourAdapter detailFourAdapter;
    Journey journey;
    List<Result> results = new ArrayList<Result>();
    List<Path> pathFinal = new ArrayList<>();
    LocalVectorDataSource vectorDataSource;
    VectorLayer vectorLayer;

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
        super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        journey = (Journey) getArguments().getSerializable("journey");
/** start get list step and show  */
        results = journey.results;
        List<Path> paths;
        List<INode> iNodeList;
        Result result;

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
        }


        GPSServiceOld.setListNotify(getNotifyList());
        detailFourAdapter = new BusDetailFourAdapter(getContext(), R.layout.detail_four_point_view, results);
        mListView.addHeaderView(mTransparentHeaderView);
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
        vectorDataSource = new LocalVectorDataSource(baseProjection);
        // Initialize a vector layer with the previous data source
        vectorLayer = new VectorLayer(vectorDataSource);
        // Add the previous vector layer to the map
        mapView.getLayers().add(vectorLayer);
        drawMap();

      //  setUpMapIfNeeded();
    }


    private void drawMap() {
        NutiteqMapUtil.drawMapWithBusFourPoint(mapView, vectorDataSource, getResources(), baseProjection, journey);
    }


    @Override
    public void onResume() {
        super.onResume();
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

    @Override
    public void onConnected(Bundle bundle) {
        // Create a DataMap object and send it to the data layer
        DataMap dataMap = new DataMap();
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