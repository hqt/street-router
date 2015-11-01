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
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.activity.SearchRouteActivity;
import com.fpt.router.adapter.BusTwoPointAdapter;
import com.fpt.router.adapter.ErrorMessageAdapter;
import com.fpt.router.framework.PrefStore;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.bus.BusLocation;
import com.fpt.router.library.model.bus.INode;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by asus on 10/12/2015.
 */
public class BusTwoPointFragment extends Fragment {
    /**
     * Main Activity for reference
     */
    private SearchRouteActivity activity;
    private Map<Integer, AutocompleteObject> mapLocation = SearchRouteActivity.mapLocation;
    private RecyclerView recyclerView;
    private List<String> listError = new ArrayList<String>();

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

        if (mapLocation.size() > 1) {
            View v = inflater.inflate(R.layout.fragment_bus_twopoint, container, false);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            if (activity.needToSearch && activity.searchType == SearchRouteActivity.SearchType.BUS_TWO_POINT) {

                JSONParseTask jsonParseTask = new JSONParseTask();
                jsonParseTask.execute();

            } else if (SearchRouteActivity.results.size() > 0) {
                recyclerView.setAdapter(new BusTwoPointAdapter(SearchRouteActivity.results));
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
            List<Result> resultList = new ArrayList<Result>();


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
                Gson gson1 = JSONUtils.buildGson();

                try {
                    resultList = gson1.fromJson(jsonFromServer, new TypeToken<List<Result>>() {
                    }.getType());
                } catch (Exception e) {

                }


            } catch (JSONException e) {
                e.printStackTrace();

            }

            /**
             * GET LIST RESULT AND SET AGAIN
             */
            for (int i=0;i<resultList.size();i++){
                Result result = resultList.get(i);
                List<INode> iNodeList = result.nodeList;
                for (int j = 0; j <iNodeList.size();j++){
                    if(iNodeList.get(j) instanceof Path){
                        Path path = (Path) iNodeList.get(j);
                        LatLng startLatLng = new LatLng(path.stationFromLocation.getLatitude(),path.stationFromLocation.getLongitude());
                        LatLng endLatLng = new LatLng(path.stationToLocation.getLatitude(),path.stationToLocation.getLongitude());
                        String   url = GoogleAPIUtils.makeURL(startLatLng.latitude, startLatLng.longitude, endLatLng.latitude, endLatLng.longitude);
                        String json = NetworkUtils.download(url);
                        try {
                           JSONObject jsonObject = new JSONObject(json);
                           String status = jsonObject.getString("status");
                            if ((status.equals("NOT_FOUND")) || status.equals("ZERO_RESULTS") || status.equals("OVER_QUERY_LIMIT")) {
                                return null;
                            } else {
                                List<Leg>  listLeg = JSONParseUtils.getListLegWithTwoPoint(json);
                                Leg leg = listLeg.get(0);
                                path = DecodeUtils.covertLegToPath(leg);
                                iNodeList.set(j,path);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            return resultList;
        }

        @Override
        protected void onPostExecute(List<Result> resultList) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (!resultList.get(0).code.equals("success")) {
                listError = new ArrayList<String>();
                listError.add(resultList.get(0).code);
                recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                return;
            }

            activity.searchType = null;
            activity.needToSearch = false;

            SearchRouteActivity.results = resultList;
            recyclerView.setAdapter(new BusTwoPointAdapter(SearchRouteActivity.results));
        }
    }
}
