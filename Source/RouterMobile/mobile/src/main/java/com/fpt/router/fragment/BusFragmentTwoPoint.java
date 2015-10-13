package com.fpt.router.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpt.router.R;
import com.fpt.router.activity.MainSecond;
import com.fpt.router.model.bus.BusLocation;
import com.fpt.router.model.motorbike.Location;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by asus on 10/12/2015.
 */
public class BusFragmentTwoPoint extends Fragment {
    /**
     * Main Activity for reference
     */
    private MainSecond activity;
    private List<String> listLocation = MainSecond.listLocation;
    private RecyclerView recyclerView;



    public BusFragmentTwoPoint() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (MainSecond) context;
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

        if (listLocation.size() > 1) {
            JSONParseTask jsonParseTask = new JSONParseTask();
            jsonParseTask.execute();

        }

        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
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
            String jsonFromServer = "";
            List<BusLocation> busLocations = new ArrayList<BusLocation>();
            try {
                for (int i=0;i<listLocation.size();i++){
                    String address_1 = listLocation.get(i);

                    String json = NetworkUtils.getLocationGoogleAPI(address_1);
                    JSONObject jsonObject = new JSONObject(json);
                    Location location = JSONParseUtils.getLocation(jsonObject);
                    BusLocation busLocation = new BusLocation();
                    busLocation.setAddress(address_1);
                    busLocation.setLatitude(location.getLatitude());
                    busLocation.setLongitude(location.getLongitude());
                    busLocations.add(busLocation);
                }

                jsonFromServer = NetworkUtils.getJsonFromServer(busLocations);



            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonFromServer;
        }

        @Override
        protected void onPostExecute(String json) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }


        }
    }
}
