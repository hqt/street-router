package com.fpt.router.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.activity.SearchRouteActivity;
import com.fpt.router.adapter.BusTwoPointAdapter;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.bus.BusLocation;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.motorbike.Location;
import com.fpt.router.library.utils.JSONUtils;
import com.fpt.router.utils.APIUtils;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by asus on 10/12/2015.
 */
public class BusTwoPointFragment extends Fragment {
    /**
     * Main Activity for reference
     */
    private SearchRouteActivity activity;
    private List<String> listLocation = SearchRouteActivity.listLocation;
    private RecyclerView recyclerView;

    public BusTwoPointFragment() {

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
            InputStream is = getActivity().getAssets().open("test.json");
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
            View v = inflater.inflate(R.layout.fragment_list_view, container, false);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            if(SearchRouteActivity.results.size() > 0){
                recyclerView.setAdapter(new BusTwoPointAdapter(SearchRouteActivity.results));
            }else{
                JSONParseTask jsonParseTask = new JSONParseTask();
                jsonParseTask.execute();
            }

            return v;

        } else {
            TextView textView = new TextView(getActivity());
            return textView;
        }
    }

    private class JSONParseTask extends AsyncTask<String, String, List<Result>> {
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
        protected List<Result> doInBackground(String... args) {

           // List<Result> resultList = new ArrayList<Result>();


            //test with file in assets
            /*Gson gson1 = JSONUtils.buildGson();
            try {
                resultList = gson1.fromJson(loadJSONFromAsset(), new TypeToken<List<Result>>() {
                }.getType());
           } catch (Exception e) {
                e.printStackTrace();
            }*/
            //test test with service real
            String jsonFromServer = "";
            JSONObject object;
            JSONArray jsonArray;
            List<BusLocation> busLocations = new ArrayList<BusLocation>();
            List<Result> resultList = new ArrayList<Result>();
            try {
                for (int i = 0; i < listLocation.size(); i++) {
                    String address_1 = listLocation.get(i);

                    String json = APIUtils.getLocationGoogleAPI(address_1);
                    JSONObject jsonObject = new JSONObject(json);
                    Location location = JSONParseUtils.getmCurrentLocation(jsonObject);
                    BusLocation busLocation = new BusLocation();
                    busLocation.setAddress(address_1);
                    busLocation.setLatitude(location.getLatitude());
                    busLocation.setLongitude(location.getLongitude());
                    busLocations.add(busLocation);
                }
                jsonFromServer = APIUtils.getJsonFromServer(busLocations);
                Log.e("Thao", jsonFromServer);
                Gson gson1 = JSONUtils.buildGson();
                resultList = gson1.fromJson(jsonFromServer, new TypeToken<List<Result>>(){}.getType());


            //test server

            /*String url = "http://192.168.1.241:8080/api/twopoint?latA=10.8372022&latB=10.7808334&longA=106.6554907&longB=106.702825&hour=15&minute=16&addressA=Galaxy+Quang+Trung%2C+H%E1%BB%93+Ch%C3%AD+Minh%2C+Vi%E1%BB%87t+Nam&addressB=Diamond+Plaza%2C+B%E1%BA%BFn+Ngh%C3%A9%2C+H%E1%BB%93+Ch%C3%AD+Minh%2C+Vi%E1%BB%87t+Nam";
            String json = NetworkUtils.download(url);
            Gson gson1 = JSONUtils.buildGson();
            resultList = gson1.fromJson(json, new TypeToken<List<Result>>() {
            }.getType());*/

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resultList;
        }

        @Override
        protected void onPostExecute(List<Result> resultList) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            SearchRouteActivity.results = resultList;
            recyclerView.setAdapter(new BusTwoPointAdapter(SearchRouteActivity.results));
        }
    }
}
