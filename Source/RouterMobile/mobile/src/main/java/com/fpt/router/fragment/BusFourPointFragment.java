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
import com.fpt.router.adapter.BusThreePointAdapter;
import com.fpt.router.adapter.ErrorMessageAdapter;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.config.AppConstants.SearchBus;
import com.fpt.router.library.model.bus.BusLocation;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.common.AutocompleteObject;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.library.utils.JSONUtils;
import com.fpt.router.utils.APIUtils;
import com.fpt.router.utils.GoogleAPIUtils;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ngoan on 10/21/2015.
 */
public class BusFourPointFragment extends Fragment {

    /**
     * Main Activity for reference
     */
    private SearchRouteActivity activity;
    private Map<Integer, AutocompleteObject> mapLocation = SearchRouteActivity.mapLocation;
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
            InputStream is = null;
            if(SearchRouteActivity.mapLocation.size() == 3){
                is = getActivity().getAssets().open("testThree.json");
            }
            if(SearchRouteActivity.mapLocation.size() == 4){
                is = getActivity().getAssets().open("testFour.json");
            }

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

        if (mapLocation.size() > 1) {
            View v = inflater.inflate(R.layout.fragment_bus_twopoint, container, false);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
            LinearLayoutManager myLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(myLinearLayoutManager);
            if (activity.needToSearch && activity.searchType == SearchRouteActivity.SearchType.BUS_FOUR_POINT) {
                JSONParseTask jsonParseTask = new JSONParseTask();
                jsonParseTask.execute();
            } else if (SearchRouteActivity.journeys.size() > 0) {
                if(SearchRouteActivity.mapLocation.size() == 3){
                    recyclerView.setAdapter(new BusThreePointAdapter(SearchRouteActivity.journeys));
                }
                if(SearchRouteActivity.mapLocation.size() == 4){
                    recyclerView.setAdapter(new BusFourPointAdapter(SearchRouteActivity.journeys));
                }
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
            // parse gson
            Gson gson_parse = JSONUtils.buildGson();
            //test with file json in asset
            if (!SearchBus.IS_REAL_BUS_SERVER) {
                try {
                    journeyList = gson_parse.fromJson(loadJSONFromAsset(), new TypeToken<List<Journey>>() {
                    }.getType());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //test test with service real
                String jsonFromServer = "";
                List<BusLocation> busLocations = new ArrayList<BusLocation>();
                List<AutocompleteObject> autocompleteObjects = new ArrayList<>();
                // add to list by ordinary
                if (SearchRouteActivity.mapLocation.get(AppConstants.SearchField.FROM_LOCATION) != null) {
                    autocompleteObjects.add(mapLocation.get(AppConstants.SearchField.FROM_LOCATION));
                }
                if (SearchRouteActivity.mapLocation.get(AppConstants.SearchField.TO_LOCATION) != null) {
                    autocompleteObjects.add(mapLocation.get(AppConstants.SearchField.TO_LOCATION));
                }
                if (SearchRouteActivity.mapLocation.get(AppConstants.SearchField.WAY_POINT_1) != null) {
                    autocompleteObjects.add(mapLocation.get(AppConstants.SearchField.WAY_POINT_1));
                }
                if (SearchRouteActivity.mapLocation.get(AppConstants.SearchField.WAY_POINT_2) != null) {
                    autocompleteObjects.add(mapLocation.get(AppConstants.SearchField.WAY_POINT_2));
                }

                try {
                    for (int i = 0; i < autocompleteObjects.size(); i++) {
                        String url = GoogleAPIUtils.getLocationByPlaceID(autocompleteObjects.get(i).getPlace_id());
                        String json = NetworkUtils.download(url);
                        BusLocation busLocation = JSONParseUtils.getBusLocation(json, autocompleteObjects.get(i).getName());
                        busLocations.add(busLocation);
                    }
                    jsonFromServer = APIUtils.getJsonFromServer(busLocations);

                    try {
                        journeyList = gson_parse.fromJson(jsonFromServer, new TypeToken<List<Journey>>() {
                        }.getType());
                    } catch (Exception e) {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            /* *
            * GET LIST RESULT AND SET AGAIN FOR WALKING PATH
            */
            for (int i = 0; i < journeyList.size(); i++) {
                Journey journey = journeyList.get(i);
                List<Result> results = journey.results;
                for (int k = 0; k < results.size(); k++) {
                    Result result = results.get(k);
                    List<INode> iNodeList = result.nodeList;
                    for (int j = 0; j < iNodeList.size(); j++) {
                        if (iNodeList.get(j) instanceof Path) {
                            Path path = (Path) iNodeList.get(j);
                            LatLng startLatLng = new LatLng(path.stationFromLocation.getLatitude(), path.stationFromLocation.getLongitude());
                            LatLng endLatLng = new LatLng(path.stationToLocation.getLatitude(), path.stationToLocation.getLongitude());
                            String url = GoogleAPIUtils.makeURL(startLatLng.latitude, startLatLng.longitude, endLatLng.latitude, endLatLng.longitude);
                            String json = NetworkUtils.download(url);
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                String status = jsonObject.getString("status");
                                if ((status.equals("NOT_FOUND")) || status.equals("ZERO_RESULTS") || status.equals("OVER_QUERY_LIMIT")) {
                                    iNodeList.set(j, path);
                                } else {
                                    List<Leg> listLeg = JSONParseUtils.getListLegWithTwoPoint(json);
                                    Leg leg = listLeg.get(0);
                                    path = DecodeUtils.covertLegToPath(leg);
                                    iNodeList.set(j, path);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            return journeyList;
        }

        @Override
        protected void onPostExecute(List<Journey> journeyList) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (!journeyList.get(0).code.equals("success")) {
                listError = new ArrayList<String>();
                listError.add(journeyList.get(0).code);
                recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                return;
            }

            activity.searchType = null;
            activity.needToSearch = false;

            SearchRouteActivity.journeys = journeyList;
            if(SearchRouteActivity.mapLocation.size() == 3){
                recyclerView.setAdapter(new BusThreePointAdapter(SearchRouteActivity.journeys));
            }
            if(SearchRouteActivity.mapLocation.size() == 4){
                recyclerView.setAdapter(new BusFourPointAdapter(SearchRouteActivity.journeys));
            }

        }
    }
}
