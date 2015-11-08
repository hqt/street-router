package com.fpt.router.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.activity.SearchRouteActivity;
import com.fpt.router.adapter.BusFourPointViewPagerAdapter;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.common.AutocompleteObject;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.library.utils.JSONUtils;
import com.fpt.router.utils.GoogleAPIUtils;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ngoan on 11/3/2015.
 */
public class BusFourPointFragmentDemo extends Fragment {

    /**
     * Main Activity for reference
     */
    private SearchRouteActivity activity;
    private Map<Integer, AutocompleteObject> mapLocation = SearchRouteActivity.mapLocation;
    private ViewPager _view_pager;
    BusFourPointViewPagerAdapter adapter;
    CirclePageIndicator pageIndicator;

    List<Journey> journeys;
    ProgressDialog pDialog;

    private AtomicInteger count;

    private List<String> listError = new ArrayList<String>();

    public BusFourPointFragmentDemo() {

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

        if (mapLocation.size() > 1) {
            View v = inflater.inflate(R.layout.busviewpagerdemo, container, false);
            _view_pager = (ViewPager) v.findViewById(R.id.pager);
            pageIndicator = (CirclePageIndicator) v.findViewById(R.id.indicator);
            if (activity.needToSearch && activity.searchType == SearchRouteActivity.SearchType.BUS_FOUR_POINT) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    new JSONParseTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    new JSONParseTask().execute();
                }
                /*JSONParseTask jsonParseTask = new JSONParseTask();
                jsonParseTask.execute();*/
            } else if (SearchRouteActivity.journeys.size() > 0) {
                adapter = new BusFourPointViewPagerAdapter(getActivity().getSupportFragmentManager(), getContext(), SearchRouteActivity.journeys);
                _view_pager.setAdapter(adapter);

                pageIndicator.setViewPager(_view_pager);
            }

            return v;

        } else {
            TextView textView = new TextView(getActivity());
            return textView;
        }
    }

    private class JSONParseTask extends AsyncTask<String, String, List<Journey>> {
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

            Gson gson1 = JSONUtils.buildGson();
            try {
                journeyList = gson1.fromJson(loadJSONFromAsset(), new TypeToken<List<Journey>>() {
                }.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //test test with service real
            /*String jsonFromServer = "";
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
                    journeyList = gson1.fromJson(jsonFromServer, new TypeToken<List<Journey>>() {
                    }.getType());


                } catch (Exception e) {

                }


            } catch (JSONException e) {
                e.printStackTrace();

            }*/

            return journeyList;
        }

        @Override
        protected void onPostExecute(List<Journey> journeyList) {

            journeys = journeyList;

            count = new AtomicInteger();
             /* *
            * GET LIST RESULT AND SET AGAIN
            */
            if (journeyList.size() > 0) {
                for (int i = 0; i < journeyList.size(); i++) {
                    Journey journey = journeyList.get(i);
                    List<Result> results = journey.results;
                    for (int k = 0; k < results.size(); k++) {
                        Result result = results.get(k);
                        List<INode> iNodeList = result.nodeList;
                        for (int j = 0; j < iNodeList.size(); j++) {

                            if (iNodeList.get(j) instanceof Path) {
                                count.incrementAndGet();
                                Path path = (Path) iNodeList.get(j);
                                new DownloadWalkingTask(iNodeList, path, j).execute();
                            }
                        }
                    }
                }
            }
        }
    }

    private class DownloadWalkingTask extends AsyncTask<Void, Void, Void> {
        Path path;
        int index;
        List<INode> iNodes;


        public DownloadWalkingTask(List<INode> iNodes, Path path, int index) {
            this.path = path;
            this.index = index;
            this.iNodes = iNodes;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            LatLng startLatLng = new LatLng(path.stationFromLocation.getLatitude(), path.stationFromLocation.getLongitude());
            LatLng endLatLng = new LatLng(path.stationToLocation.getLatitude(), path.stationToLocation.getLongitude());
            String url = GoogleAPIUtils.makeURL(startLatLng.latitude, startLatLng.longitude, endLatLng.latitude, endLatLng.longitude);
            String json = NetworkUtils.download(url);
            try {
                JSONObject jsonObject = new JSONObject(json);
                String status = jsonObject.getString("status");
                if ((status.equals("NOT_FOUND")) || status.equals("ZERO_RESULTS") || status.equals("OVER_QUERY_LIMIT")) {
                    Log.i("Ngoan --- > ", status);
                    Toast.makeText(getContext(), "Ngoan" + status, Toast.LENGTH_SHORT).show();
                    //return null;
                    iNodes.set(index, path);
                } else {
                    List<Leg> listLeg = JSONParseUtils.getListLegWithTwoPoint(json);
                    Leg leg = listLeg.get(0);
                    path = DecodeUtils.covertLegToPath(leg);
                    iNodes.set(index, path);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            int remain = count.decrementAndGet();

            if (remain == 0) {
                if (pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                activity.searchType = null;
                activity.needToSearch = false;

                SearchRouteActivity.journeys = journeys;
                Log.i("Journeys ", journeys.size() + "");
                adapter = new BusFourPointViewPagerAdapter(getActivity().getSupportFragmentManager(), getContext(), SearchRouteActivity.journeys);
                _view_pager.setAdapter(adapter);
                pageIndicator.setViewPager(_view_pager);
            }
        }
    }

}