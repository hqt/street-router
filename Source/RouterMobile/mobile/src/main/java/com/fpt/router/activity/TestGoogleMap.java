package com.fpt.router.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;


public class TestGoogleMap extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        JSONParse asd = new JSONParse();
        asd.execute();
    }

    private class JSONParse extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TestGoogleMap.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String test;
            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=congvienphanmemquangtrung,hochiminh&destination=chocau,hochiminh&mode=driving&key=AIzaSyCRGhL_5rWzeGXUbSpz0Urw8_8LCTmYrj4";
            test = NetworkUtils.download(url);
            return test;
        }
        @Override
        protected void onPostExecute(String json) {
            pDialog.dismiss();
            TextView textView = (TextView) findViewById(R.id.name);
        }
    }
}

