package com.fpt.router.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fpt.router.R;
import com.fpt.router.activity.MainActivity;
import com.fpt.router.model.motorbike.DetailLocation;
import com.fpt.router.model.motorbike.Leg;
import com.fpt.router.model.motorbike.Location;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.LogUtils;
import com.fpt.router.utils.DecodeUtils;
import com.fpt.router.utils.MapUtils;
import com.fpt.router.utils.NetworkUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.Map;

public class MotorbikeFragment extends Fragment {

    private GoogleMap mMap;
    MapView mapView;
    private Double latitude, longitude;

    /** Main Activity for reference */
    private MainActivity activity;

    /** empty constructor
     * must have for fragment
     */
    public MotorbikeFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (MainActivity) context;
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
        if(savedInstanceState.getString("from") != null) {
            String from = savedInstanceState.getString("from");
            Log.e("Test from: ", from);
        }
        if(savedInstanceState.getString("to") != null) {
            String to = savedInstanceState.getString("to");
            Log.e("Test to: ", to);
        }

        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONParseTask jsonParseTask = new JSONParseTask();
        jsonParseTask.execute();
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
            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=congvienphanmemquangtrung,hochiminh&destination=damsen,hochiminh&waypoints=congvienphulam&mode=driving&key=AIzaSyBkY1ok25IxoD6nRl_hunFAtTbh1EOss5A";
            json = NetworkUtils.download(url);
            return json;
        }
        @Override
        protected void onPostExecute(String json) {
            pDialog.dismiss();
            mMap= mapView.getMap();
            Leg l;
            l = JSONParseUtils.getLeg(json);
            DetailLocation detalL = l.getlDetailLocation();
            Location start_location = detalL.getStart_location();
            Location end_location = detalL.getEnd_location();
            // latitude and longitude

            latitude = start_location.getLatitude();
            longitude = start_location.getLongitude();

            MapUtils.drawPoint(mMap, latitude, longitude, l.getlStartAddress());

            //add polyline
            String encodedString = l.getlOverview_polyline();
            List<LatLng> list = DecodeUtils.decodePoly(encodedString);
            MapUtils.drawLine(mMap, list, Color.BLUE);
            MapUtils.moveCamera(mMap, latitude, longitude, 12);
        }
    }


}
