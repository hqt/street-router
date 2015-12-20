package com.fpt.router.fragment;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.activity.MainActivity;
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
import com.fpt.router.service.GPSServiceOld;
import com.fpt.router.utils.APIUtils;
import com.fpt.router.utils.CheckDuplicateUtils;
import com.fpt.router.utils.GoogleAPIUtils;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;
import com.fpt.router.utils.SortUtils;
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
    public boolean flat_call_server_again = false;
    public boolean flat_check_search_second = false;
    String code = "";

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
            if (SearchRouteActivity.mapLocation.size() == 3) {
                is = getActivity().getAssets().open("testThree.json");
            }
            if (SearchRouteActivity.mapLocation.size() == 4) {
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
            } else if (SearchRouteActivity.journeys != null) {
                if (SearchRouteActivity.mapLocation.size() == 3) {
                    recyclerView.setAdapter(new BusThreePointAdapter(SearchRouteActivity.journeys));
                }
                if (SearchRouteActivity.mapLocation.size() == 4) {
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
        int totalProgress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            pDialog.setMessage(values[0]);
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
                    /*if (SearchRouteActivity.isTrackingGPS) {
                        busLocations.add(new BusLocation(GPSServiceOld.gpsServiceInstance.getLatitude(),
                                GPSServiceOld.gpsServiceInstance.getLongitude(), "Vị trí hiện tại."));
                    } else {
                        autocompleteObjects.add(mapLocation.get(AppConstants.SearchField.FROM_LOCATION));
                    }*/
                    autocompleteObjects.add(mapLocation.get(AppConstants.SearchField.FROM_LOCATION));
                } else {
                    busLocations.add(new BusLocation(GPSServiceOld.gpsServiceInstance.getLatitude(),
                            GPSServiceOld.gpsServiceInstance.getLongitude(), "Vị trí hiện tại."));
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
                        AutocompleteObject autoObject = autocompleteObjects.get(i);
                        if (!autoObject.getPlace_id().equals("")) {
                            String url = GoogleAPIUtils.getLocationByPlaceID(autocompleteObjects.get(i).getPlace_id());
                            String json = NetworkUtils.download(url);
                            BusLocation busLocation = JSONParseUtils.getBusLocation(json, autocompleteObjects.get(i).getName());
                            busLocations.add(busLocation);
                        } else {
                            String url = GoogleAPIUtils.getTwoPointDirection(autoObject, autoObject);
                            String json = NetworkUtils.download(url);
                            JSONObject jsonO = new JSONObject(json);
                            String status = jsonO.getString("status");
                            if (!status.equals("OK")) {
                                code = "Đia điểm bạn tìm kiếm không được tìm thấy, vui lòng nhập lại.";
                                return journeyList;
                            } else {
                                BusLocation busLocation = JSONParseUtils.getBusLocationWithNoPlaceId(json, autoObject.getName());
                                busLocations.add(busLocation);
                            }
                        }
                    }
                    jsonFromServer = APIUtils.getJsonFromServer(busLocations);

                    publishProgress("finish get data from server");

                    try {
                        journeyList = gson_parse.fromJson(jsonFromServer, new TypeToken<List<Journey>>() {
                        }.getType());
                    } catch (Exception e) {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            // must be sort before remove duplicate
            SortUtils.sortJourney(journeyList);
            // remove duplicate result
            journeyList = CheckDuplicateUtils.checkDuplicateJourney(journeyList);

            if (!SearchBus.IS_USED_REAL_WALKING) {
                return journeyList;
            }
            // Find how many request should be sent
            /* *
            * GET LIST RESULT AND SET AGAIN FOR WALKING PATH
            */
            int count = 0;
            for (int i = 0; i < journeyList.size(); i++) {
                Journey journey = journeyList.get(i);
                List<Result> results = journey.results;
                for (int k = 0; k < results.size(); k++) {
                    Result result = results.get(k);
                    List<INode> iNodeList = result.nodeList;
                    for (int j = 0; j < iNodeList.size(); j++) {
                        if (iNodeList.get(j) instanceof Path) {
                            Path path = (Path) iNodeList.get(j);
                            Path origin_path = (Path) iNodeList.get(j);
                            LatLng startLatLng = new LatLng(path.stationFromLocation.getLatitude(), path.stationFromLocation.getLongitude());
                            LatLng endLatLng = new LatLng(path.stationToLocation.getLatitude(), path.stationToLocation.getLongitude());
                            String url = GoogleAPIUtils.makeURL(startLatLng.latitude, startLatLng.longitude, endLatLng.latitude, endLatLng.longitude);
                            String json = NetworkUtils.download(url);
                            publishProgress("download " + count + " paths");
                            count++;
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                String status = jsonObject.getString("status");
                                if ((status.equals("NOT_FOUND")) || status.equals("ZERO_RESULTS") || status.equals("OVER_QUERY_LIMIT")) {
                                    iNodeList.set(j, path);
                                } else {
                                    List<Leg> listLeg = JSONParseUtils.getListLegWithTwoPoint(json);
                                    Leg leg = listLeg.get(0);
                                    path = DecodeUtils.covertLegToPath(leg);
                                    path.stationFromName = origin_path.stationFromName;
                                    path.stationToName = origin_path.stationToName;
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
        protected void onPostExecute(final List<Journey> journeyList) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if ((journeyList == null) || (journeyList.size() == 0)) {
                listError = new ArrayList<String>();
                listError.add(code);
                recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                return;
            }
            if (!journeyList.get(0).code.equals("success")) {

                code = journeyList.get(0).code;
                Log.i("BusTwoPointFragment -->", code);
                String re_code = code.trim().substring(0, 29);

                if (re_code.equals("không có trạm xe buýt nào gần")) {
                    if (!flat_call_server_again) {
                        flat_call_server_again = true;
                        flat_check_search_second = true;
                        Log.i("Ngoan Dep Trai Qua", "");
                        SearchRouteActivity.walkingDistance = 1000;
                        JSONParseTask jsonParseTask = new JSONParseTask();
                        jsonParseTask.execute();
                    } else {
                        listError = new ArrayList<String>();
                        listError.add(journeyList.get(0).code);
                        recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                        return;
                    }
                } else {
                    Log.i("Chan bo me", "");
                    listError = new ArrayList<String>();
                    listError.add(journeyList.get(0).code);
                    recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                    return;
                }
            } else {
                if (!flat_check_search_second) {
                    activity.searchType = null;
                    activity.needToSearch = false;

                    SearchRouteActivity.journeys = journeyList;
                    if (SearchRouteActivity.mapLocation.size() == 3) {
                        recyclerView.setAdapter(new BusThreePointAdapter(SearchRouteActivity.journeys));
                    }
                    if (SearchRouteActivity.mapLocation.size() == 4) {
                        recyclerView.setAdapter(new BusFourPointAdapter(SearchRouteActivity.journeys));
                    }
                } else {
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.activity_research_bus);
                    dialog.setTitle("Thông Báo....");
                    Button acceptButton, cancelButton;
                    acceptButton = (Button) dialog.findViewById(R.id.btn_yes);
                    cancelButton = (Button) dialog.findViewById(R.id.btn_no);
                    acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            SearchRouteActivity.walkingDistance = 300;
                            activity.searchType = null;
                            activity.needToSearch = false;
                            activity.searchType = null;
                            activity.needToSearch = false;

                            SearchRouteActivity.journeys = journeyList;
                            if (SearchRouteActivity.mapLocation.size() == 3) {
                                recyclerView.setAdapter(new BusThreePointAdapter(SearchRouteActivity.journeys));
                            }
                            if (SearchRouteActivity.mapLocation.size() == 4) {
                                recyclerView.setAdapter(new BusFourPointAdapter(SearchRouteActivity.journeys));
                            }
                        }
                    });

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            SearchRouteActivity.walkingDistance = 300;
                            listError = new ArrayList<String>();
                            listError.add(code);
                            recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                            return;
                        }
                    });
                    dialog.show();
                }
            }
        }
    }
}
