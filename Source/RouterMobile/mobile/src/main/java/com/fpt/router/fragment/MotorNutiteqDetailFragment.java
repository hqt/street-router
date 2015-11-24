package com.fpt.router.fragment;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.fpt.router.R;
import com.fpt.router.activity.SearchDetailActivity;
import com.fpt.router.activity.SearchRouteActivity;
import com.fpt.router.adapter.ErrorMessageAdapter;
import com.fpt.router.adapter.MotorAdapter;
import com.fpt.router.adapter.RouteItemAdapter;
import com.fpt.router.fragment.base.AbstractNutiteqMapFragment;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.common.AutocompleteObject;
import com.fpt.router.library.model.common.NotifyModel;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.model.motorbike.Step;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.service.GPSServiceOld;
import com.fpt.router.utils.GoogleAPIUtils;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;
import com.fpt.router.utils.NutiteqMapUtil;
import com.fpt.router.widget.SlidingUpPanelLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.nutiteq.core.MapPos;
import com.nutiteq.datasources.LocalVectorDataSource;
import com.nutiteq.layers.VectorLayer;
import com.nutiteq.utils.AssetUtils;
import com.nutiteq.vectorelements.Marker;
import com.nutiteq.vectorelements.NMLModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fpt.router.library.utils.DecodeUtils.*;

/**
 * Created by asus on 10/12/2015.
 */
public class MotorNutiteqDetailFragment extends AbstractNutiteqMapFragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        SlidingUpPanelLayout.PanelSlideListener {

    private SearchDetailActivity activity;
    private GoogleApiClient mGoogleApiClient;
    private Marker now;
    int position;
    List<Leg> listLeg = SearchRouteActivity.listLeg;
    List<Leg> listLegFake = new ArrayList<>();
    List<Step> listStep = new ArrayList<>();
    List<Leg> listFinalLeg = new ArrayList<>();
    private RouteItemAdapter adapterItem;
    private String status;
    LocalVectorDataSource vectorDataSource;
    VectorLayer vectorLayer;
    Resources rs;
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
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        position = (int) getArguments().getSerializable("position");
        rs = getResources();

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
        //Get list legs fake to fake line
        if(SearchRouteActivity.mapLocation.size() == 2) {
            listLegFake.add(listLeg.get(position+1));
        } else if (SearchRouteActivity.mapLocation.size() == 3) {
            for(int n = (position+1)*2; n < (position+1)*2+2; n++) {
                listLegFake.add(listLeg.get(n));
            }
        } else {
            for(int n = (position+1)*3; n < (position+1)*3+3; n++) {
                listLegFake.add(listLeg.get(n));
            }
        }
        for(int n = 0; n < listFinalLeg.size(); n ++) {
            listStep.addAll(listFinalLeg.get(n).getSteps());
        }



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
        drawFakeLine(listLegFake);
        drawMap();
    }

    private void drawMap() {
        GPSServiceOld.setListLegToCheck(listFinalLeg);
        GPSServiceOld.setListFakeGPSOfFake(getListLocationToFakeGPS(listLegFake, SearchRouteActivity.optimize));
        GPSServiceOld.setListNotify(getNotifyList());
        adapterItem = new RouteItemAdapter(getContext(), R.layout.activity_list_row_gmap, listStep);
        if(SearchRouteActivity.mapLocation.size() == 2) {
            NutiteqMapUtil.drawMapWithTwoPoint(mapView, vectorDataSource, rs, baseProjection, listFinalLeg);

        } else {
            NutiteqMapUtil.drawMapWithFourPoint(mapView, vectorDataSource, rs, baseProjection, listFinalLeg);
        }
        for(int n = 0; n < listStep.size(); n++) {
            NutiteqMapUtil.drawMarkerNutiteqAllOption(mapView, vectorDataSource, rs,
                    listStep.get(n).getDetailLocation().getStartLocation().getLatitude(),
                    listStep.get(n).getDetailLocation().getStartLocation().getLongitude(),
                    R.drawable.orange_small, 20);
        }
        
    }

    private void drawFakeLine(List<Leg> input) {
        for(int n = 0; n < input.size(); n++) {
            List<LatLng> listLL = DecodeUtils.decodePoly(input.get(n).getOverview_polyline());
            NutiteqMapUtil.drawLineNutite(vectorDataSource, 0xFFFF0000, listLL, baseProjection, 6);

        }
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

    @Override
    public void onConnected(Bundle bundle) {
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

    int icon = R.drawable.marker_cua_nam_burned;
    @Override
    public void drawCurrentLocation(Double lat, Double lng) {
        /*MapPos markerPos = mapView.getOptions().getBaseProjection().fromWgs84(new MapPos(lng, lat));
        if(marker == null){
            marker = NutiteqMapUtil.drawCurrentMarkerNutiteq(mapView, rs, lat, lng, icon);
            vectorDataSource.add(marker);
        } else {
            marker.setPos(markerPos);
        }
        if(GPS_ON_FLAG) {
            mapView.setFocusPos(markerPos, 0f);
        }*/
        MapPos markerPos = mapView.getOptions().getBaseProjection().fromWgs84(new MapPos(lng, lat));
        if (model == null) {
            model = new NMLModel(markerPos, AssetUtils.loadBytes("ferrari360.nml"));
            model.setScale(400);
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
        List<LatLng> listFakeGPS = getListLocationToFakeGPS(listFinalLeg, SearchRouteActivity.optimize);
        return listFakeGPS;
    }

    @Override
    public List<NotifyModel> getNotifyList() {
        List<NotifyModel>  listNotifies = new ArrayList<>();
        for(int i = 0; i < listStep.size(); i++) {
            com.fpt.router.library.model.common.Location location = listStep.get(i).
                    getDetailLocation().getStartLocation();
            String smallTittle = JSONParseUtils.replayManeuver((listStep.get(i).getManeuver()));
            String longTittle = "Thông tin chi tiết";
            String[] detailInstruction = getDetailInstruction(
                    listStep.get(i).getInstruction());
            String smallMessage = detailInstruction[0];
            String longMessage = "";
            if(detailInstruction.length >1) {
                longMessage = detailInstruction[1];
            }
            if(detailInstruction[detailInstruction.length - 1].startsWith("Điểm đến")) {
                smallMessage = smallMessage + ", "+ detailInstruction[detailInstruction.length - 1];
            }
            NotifyModel notifyModel = new NotifyModel(location, smallTittle, longTittle, smallMessage, longMessage);
            listNotifies.add(notifyModel);
        }
        //modifyNotifyList(listNotifies);
        return listNotifies;
    }


    private List<NotifyModel> modifyNotifyList(List<NotifyModel> notifyModelList) {
        List<LatLng> listFakeGPS = getFakeGPSList();
        int notifyIndex = 0;
        for(int n = 0; n < listFakeGPS.size(); n++) {
            if (notifyIndex < notifyModelList.size() - 1) {
                LatLng startLatLng = new LatLng(notifyModelList.get(notifyIndex).location.getLatitude(),
                        notifyModelList.get(notifyIndex).location.getLongitude());
                LatLng endLatLng = new LatLng(notifyModelList.get(notifyIndex + 1).location.getLatitude(),
                        notifyModelList.get(notifyIndex + 1).location.getLongitude());
                if (calculateDistance(listFakeGPS.get(n), endLatLng) < 50) {
                    notifyIndex++;
                } else if ((calculateDistance(listFakeGPS.get(n), startLatLng) >= 1000) &&
                        (calculateDistance(listFakeGPS.get(n), endLatLng) >= 1000)) {
                    com.fpt.router.library.model.common.Location location =
                            new com.fpt.router.library.model.common.Location(
                                    listFakeGPS.get(n).latitude,
                                    listFakeGPS.get(n).longitude);
                    String smallTittle = "Tiếp tục đi thẳng";
                    String longTittle = "Thông tin chi tiết";
                    String smallMessage = "Xin tiếp tục đi thẳng trên con đường hiện tại";
                    String longMessage = "";
                    NotifyModel notifyModel = new NotifyModel(location, smallTittle, longTittle, smallMessage, longMessage);
                    notifyModelList.add(notifyIndex +1, notifyModel);
                    notifyIndex++;
                }
            }
        }
        return notifyModelList;
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

    class JSONParseTask extends AsyncTask<String, String, List<Leg>> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();

        }

        @Override
        protected List<Leg> doInBackground(String... args) {
            List<Leg> listLeg = new ArrayList<>();
            String json;
            String url;
            List<AutocompleteObject> locationAutoCompletes = new ArrayList<>();
            locationAutoCompletes.add(new AutocompleteObject(args[0], ""));
            locationAutoCompletes.add(new AutocompleteObject(args[1], ""));
            url = GoogleAPIUtils.getTwoPointDirection(locationAutoCompletes.get(0), locationAutoCompletes.get(1));
            json = NetworkUtils.download(url);
            try {
                JSONObject jsonObject = new JSONObject(json);
                status = jsonObject.getString("status");
                if ((status.equals("NOT_FOUND")) || status.equals("ZERO_RESULTS") || status.equals("OVER_QUERY_LIMIT")) {
                    return null;
                } else {
                    int duration = 0;
                    double distance = 0;
                    List<Step> steps = new ArrayList<Step>();
                    listLeg = JSONParseUtils.getListLegWithTwoPoint(json);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return listLeg;
        }

        @Override
        protected void onPostExecute(List<Leg> listLeg) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (status.equals("NOT_FOUND")) {
                return;
            }

            if (status.equals("ZERO_RESULTS")) {
                return;
            }

            if (status.equals("OVER_QUERY_LIMIT")) {
                return;
            }
            if(listLeg.size() > 1) {
                for (int x = 0; x < listLeg.size() - 1; x++) {
                    for (int y = x+1; y < listLeg.size(); y++) {
                        if (listLeg.get(y).getDetailLocation().getDuration() < listLeg.get(x).getDetailLocation().getDuration()) {
                            Leg leg = listLeg.get(x);
                            listLeg.set(x, listLeg.get(y));
                            listLeg.set(y, leg);
                        }
                    }
                }
            }
        }
    }
}