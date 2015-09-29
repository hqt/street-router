package com.fpt.router.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.activity.MainActivity;
import com.fpt.router.model.motorbike.detailLocation;
import com.fpt.router.model.motorbike.leg;
import com.fpt.router.model.motorbike.location;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.LogUtils;
import com.fpt.router.utils.NetworkUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MotorbikeFragment extends Fragment {


    private static String TAG = LogUtils.makeLogTag(MotorbikeFragment.class);

    private GoogleMap mMap;
    MapView mapView;
    private static Double latitude, longitude;

    /** Main Activity for reference */
    private MainActivity activity;

    /** empty constructor
     * must have for fragment
     */
    public MotorbikeFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
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

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONParse jsonParse = new JSONParse();
        jsonParse.execute();
        return rootView;
    }

    @Override
     public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    private class JSONParse extends AsyncTask<String, String, String> {
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
            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=congvienphanmemquangtrung,hochiminh&destination=chocau,hochiminh&mode=driving&key=AIzaSyCRGhL_5rWzeGXUbSpz0Urw8_8LCTmYrj4";
            json = NetworkUtils.download(url);
            return json;
        }
        @Override
        protected void onPostExecute(String json) {
            pDialog.dismiss();
            leg l;
            l = JSONParseUtils.getLeg(json);
            detailLocation detalL = l.getlDetailLocation();
            location start_location = detalL.getStart_location();
            // latitude and longitude
            mMap= mapView.getMap();
            latitude = start_location.getLatitude();
            longitude = start_location.getLongitude();

            // create marker
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(latitude, longitude)).title(l.getlStartAddress());

            // Changing marker icon
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            // adding marker
            mMap.addMarker(marker);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latitude, longitude)).zoom(12).build();
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            // Perform any camera updates here
        }
    }
}
