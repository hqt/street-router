package com.fpt.router.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.activity.SearchRouteActivity;
import com.fpt.router.adapter.BusFourPointAdapter;
import com.fpt.router.adapter.BusTwoPointAdapter;
import com.fpt.router.adapter.ErrorMessageAdapter;
import com.fpt.router.library.model.bus.BusLocation;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.motorbike.Location;
import com.fpt.router.library.utils.JSONUtils;
import com.fpt.router.utils.APIUtils;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngoan on 10/21/2015.
 */
public class BusFourPointFragment extends Fragment {

    /**
     * Main Activity for reference
     */
    private SearchRouteActivity activity;
    private List<String> listLocation = SearchRouteActivity.listLocation;
    private RecyclerView recyclerView;
    private List<String> listError = new ArrayList<String>();

    public BusFourPointFragment() {

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

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("testFour.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (listLocation.size() > 1) {
            View v = inflater.inflate(R.layout.fragment_bus_twopoint, container, false);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            if (activity.needToSearch && activity.searchType == SearchRouteActivity.SearchType.BUS_FOUR_POINT) {
                JSONParseTask jsonParseTask = new JSONParseTask();
                jsonParseTask.execute();
            } else if (SearchRouteActivity.results.size() > 0) {
                recyclerView.setAdapter(new BusFourPointAdapter(SearchRouteActivity.journeys));
            }

            return v;

        } else {
            TextView textView = new TextView(getActivity());
            return textView;
        }
    }

    private class JSONParseTask extends AsyncTask<String, String, List<Journey>> {
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
        protected List<Journey> doInBackground(String... args) {
            List<Journey> journeyList = new ArrayList<Journey>();

           /* Gson gson1 = JSONUtils.buildGson();
            try {
                journeyList = gson1.fromJson(loadJSONFromAsset(), new TypeToken<List<Journey>>() {
                }.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            //test test with service real
            String jsonFromServer = "";
            JSONObject object;
            JSONArray jsonArray;
            List<BusLocation> busLocations = new ArrayList<BusLocation>();
            try {
                for (int i = 0; i < listLocation.size(); i++) {
                    String address_1 = listLocation.get(i);

                    String json = APIUtils.getLocationGoogleAPI(address_1);
                    JSONObject jsonObject = new JSONObject(json);
                    Location location = JSONParseUtils.getLocation(jsonObject);
                    BusLocation busLocation = new BusLocation();
                    busLocation.setAddress(address_1);
                    busLocation.setLatitude(location.getLatitude());
                    busLocation.setLongitude(location.getLongitude());
                    busLocations.add(busLocation);
                }
                jsonFromServer = APIUtils.getJsonFromServer(busLocations);
                Gson gson1 = JSONUtils.buildGson();

                try {
                    journeyList = gson1.fromJson(jsonFromServer, new TypeToken<List<Journey>>() {
                    }.getType());

                } catch (Exception e) {

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            //test server
           /* String url = "http://192.168.1.128:8080/search/multi?latA=10.855090&longA=106.628394&latB=10.801605&longB=106.698817&latC=10.800767&longC=106.659483&latD=10.779786&longD=106.698994&addressA=Qu%C3%A1n+Vitamin%2C+H%E1%BB%93+Ch%C3%AD+Minh%2C+Vi%E1%BB%87t+Nam&addressB=Qu%C3%A1n+Vitamin%2C+H%E1%BB%93+Ch%C3%AD+Minh%2C+Vi%E1%BB%87t+Nam&addressC=Qu%C3%A1n+Vitamin%2C+H%E1%BB%93+Ch%C3%AD+Minh%2C+Vi%E1%BB%87t+Nam&addressD=Qu%C3%A1n+Vitamin%2C+H%E1%BB%93+Ch%C3%AD+Minh%2C+Vi%E1%BB%87t+Nam&isOp=false&hour=1&minute=30";
            String json = NetworkUtils.download(url);
            Gson gson1 = JSONUtils.buildGson();
            journeyList = gson1.fromJson(json, new TypeToken<List<Journey>>() {
            }.getType());*/
            return journeyList;
        }

        @Override
        protected void onPostExecute(List<Journey> journeyList) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if(!journeyList.get(0).code.equals("success")){
                listError = new ArrayList<String>();
                listError.add(journeyList.get(0).code);
                recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                return;
            }

            activity.searchType = null;
            activity.needToSearch = false;

            SearchRouteActivity.journeys = journeyList;
            recyclerView.setAdapter(new BusFourPointAdapter(SearchRouteActivity.journeys));
        }
    }
}
