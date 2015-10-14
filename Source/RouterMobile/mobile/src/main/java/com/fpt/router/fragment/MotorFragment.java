package com.fpt.router.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpt.router.R;
import com.fpt.router.activity.SearchRouteActivity;
import com.fpt.router.library.model.motorbike.DetailLocation;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.model.motorbike.Location;
import com.fpt.router.utils.DecodeUtils;
import com.fpt.router.utils.GPSUtils;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.MapUtils;
import com.fpt.router.utils.NetworkUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MotorFragment extends Fragment {

    private GoogleMap mMap;
    MapView mapView;
    private Double latitude, longitude;
    private List<String> listLocation = SearchRouteActivity.listLocation;
    private Boolean optimize = SearchRouteActivity.optimize;
    /** Main Activity for reference */
    private SearchRouteActivity activity;

    /** empty constructor
     * must have for fragment
     */
    public MotorFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (SearchRouteActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_motorbike, container, false);
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();// needed to get the map to display immediately
        mMap= mapView.getMap();

        // Get current location
        GPSUtils gpsUtils = new GPSUtils(activity);
        Double currentLatitude = gpsUtils.getLatitude();
        Double currentLongitude = gpsUtils.getLongitude();


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(listLocation.size()>1) {
            JSONParseTask jsonParseTask = new JSONParseTask();
            jsonParseTask.execute();
        } else {
            MapUtils.drawPoint(mMap, currentLatitude, currentLongitude, "Current Location");
            MapUtils.moveCamera(mMap, currentLatitude, currentLongitude, 15);
        }
        return rootView;
    }

    @Override
     public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    private class JSONParseTask extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String json;
            String url = NetworkUtils.linkGoogleDrirectionForTwoPoint(listLocation.get(0), listLocation.get(1));
            json = NetworkUtils.download(url);
            return json;
        }
        @Override
        protected void onPostExecute(String json) {
            if(pDialog.isShowing()) {
                pDialog.dismiss();
            }
            Leg leg;
            List<LatLng> list;
            String encodedString;
            if(listLocation.size() == 2) {
                List<Leg> listLeg = JSONParseUtils.getListLegWithTwoPoint(json);
                for (int n = 1; n < listLeg.size(); n++) {
                    leg = listLeg.get(n);
                    encodedString = leg.getOverview_polyline();
                    list = DecodeUtils.decodePoly(encodedString);
                    MapUtils.drawLine(mMap, list, Color.GRAY);
                }
                leg = listLeg.get(0);
                DetailLocation detalL = leg.getDetailLocation();
                Location start_location = detalL.getStart_location();
                Location end_location = detalL.getEnd_location();
                // latitude and longitude

                latitude = end_location.getLatitude();
                longitude = end_location.getLongitude();
                MapUtils.drawPoint(mMap, latitude, longitude, leg.getEndAddress());

                latitude = start_location.getLatitude();
                longitude = start_location.getLongitude();
                MapUtils.drawPoint(mMap, latitude, longitude, leg.getStartAddress());

                //add polyline
                encodedString = leg.getOverview_polyline();
                list = DecodeUtils.decodePoly(encodedString);
                MapUtils.drawLine(mMap, list, Color.BLUE);
                MapUtils.moveCamera(mMap, latitude, longitude, 12);
            } else {
                List<Leg> listLeg = JSONParseUtils.getListLegWithFourPoint(json);
                for(int n = 0; n < listLeg.size(); n++){
                    leg = listLeg.get(n);
                    DetailLocation detalL = leg.getDetailLocation();
                    Location start_location = detalL.getStart_location();
                    Location end_location = detalL.getEnd_location();
                    // latitude and longitude

                    latitude = end_location.getLatitude();
                    longitude = end_location.getLongitude();
                    MapUtils.drawPoint(mMap, latitude, longitude, leg.getEndAddress());

                    latitude = start_location.getLatitude();
                    longitude = start_location.getLongitude();
                    MapUtils.drawPoint(mMap, latitude, longitude, leg.getStartAddress());

                    //add polyline
                    encodedString = leg.getOverview_polyline();
                    list = DecodeUtils.decodePoly(encodedString);
                    MapUtils.drawLine(mMap, list, Color.BLUE);
                    MapUtils.moveCamera(mMap, latitude, longitude, 12);
                }
            }
        }
    }

}
