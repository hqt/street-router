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

import com.fpt.router.R;
import com.fpt.router.activity.MainSecond;
import com.fpt.router.adapter.BusAdapterTwoPoint;
import com.fpt.router.library.model.bus.BusLocation;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.motorbike.Location;
import com.fpt.router.library.utils.JSONUtils;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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
    private List<Result> results;


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

        } else {
            results = new ArrayList<Result>();
        }

        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
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
            String jsonFromServer = "";
            JSONObject object;
            JSONArray jsonArray;
            List<BusLocation> busLocations = new ArrayList<BusLocation>();
            List<Result> resultList = new ArrayList<Result>();

            Gson gson = new Gson();
          /*  try {*/
                /*for (int i = 0; i < listLocation.size(); i++) {
                    String address_1 = listLocation.get(i);

                    String json = NetworkUtils.getLocationGoogleAPI(address_1);
                    JSONObject jsonObject = new JSONObject(json);
                    Location location = JSONParseUtils.getLocation(jsonObject);
                    BusLocation busLocation = new BusLocation();
                    busLocation.setAddress(address_1);
                    busLocation.setLatitude(location.getLatitude());
                    busLocation.setLongitude(location.getLongitude());
                    busLocations.add(busLocation);
                }*/

                String url = "http://192.168.43.227:8080/api/twopoint?latA=10.8372022&latB=10.7808334&longA=106.6554907&longB=106.702825&hour=15&minute=16&addressA=Galaxy+Quang+Trung%2C+H%E1%BB%93+Ch%C3%AD+Minh%2C+Vi%E1%BB%87t+Nam&addressB=Diamond+Plaza%2C+B%E1%BA%BFn+Ngh%C3%A9%2C+H%E1%BB%93+Ch%C3%AD+Minh%2C+Vi%E1%BB%87t+Nam";
                String json = NetworkUtils.download(url);
                /*jsonFromServer = NetworkUtils.getJsonFromServer(busLocations);*/
               /* Result result = JSONParseUtils.parseJsonResult(json);*/
               /*object = new JSONObject(jsonFromServer);
                jsonArray = object.getJSONArray("abc");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject resultObject = jsonArray.getJSONObject(i);
                    BufferedReader br = new BufferedReader(
                            new StringReader(jsonFromServer));
                    Result result = gson.fromJson(br, Result.class);
                    resultList.add(result);
                }
*/
               /* BufferedReader br = new BufferedReader(
                        new StringReader(jsonFromServer));*/
                Gson gson1 = JSONUtils.buildGson();
                resultList = gson1.fromJson(json, new TypeToken<List<Result>>(){}.getType());

               // Result result = gson.fromJson(jsonFromServer, Result.class);
               // resultList.add(result);

            /*} catch (JSONException e) {
                e.printStackTrace();
            }*/
            return resultList;
        }

        @Override
        protected void onPostExecute(List<Result> resultList) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            results = resultList;
            recyclerView.setAdapter(new BusAdapterTwoPoint(results));
        }
    }
}
