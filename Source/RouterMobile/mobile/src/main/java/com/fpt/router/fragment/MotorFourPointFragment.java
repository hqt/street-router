package com.fpt.router.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.fpt.router.R;
import com.fpt.router.activity.SearchRouteActivity;
import com.fpt.router.adapter.MotorAdapter;
import com.fpt.router.adapter.ErrorMessageAdapter;
import com.fpt.router.library.config.AppConstants.GoogleApiCode;
import com.fpt.router.library.config.AppConstants.SearchField;
import com.fpt.router.library.model.common.AutocompleteObject;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.service.GPSServiceOld;
import com.fpt.router.utils.GoogleAPIUtils;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Nguyen Trung Nam on 10/12/2015.
 */
public class MotorFourPointFragment extends Fragment{
    /**
     * Main Activity for reference
     */
    private SearchRouteActivity activity;
    private Map<Integer, AutocompleteObject> mapLocation = SearchRouteActivity.mapLocation;
    private Boolean optimize = SearchRouteActivity.optimize;
    private RecyclerView recyclerView;
    private JSONObject jsonObject;
    private String status;
    private List<String> listError;

    public MotorFourPointFragment() {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mapLocation.size() > 1){
            View v = inflater.inflate(R.layout.fragment_bus_twopoint, container, false);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            if (activity.needToSearch && activity.searchType == SearchRouteActivity.SearchType.MOTOR_FOUR_POINT) {
                JSONParseTask jsonParseTask = new JSONParseTask();
                jsonParseTask.execute();
            } else if ((SearchRouteActivity.listLeg != null)
                    /*&& (activity.searchType == SearchRouteActivity.SearchType.MOTOR_FOUR_POINT)*/) {
                recyclerView.setAdapter(new MotorAdapter());
            }

            return v;
        }else {
            TextView textView = new TextView(getActivity());
            return textView;
        }

    }


    private class JSONParseTask extends AsyncTask<String, String, List<Leg>> {
        private ProgressDialog pDialog;

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
        protected List<Leg> doInBackground(String... args) {
            List<Leg> listLegFinal = new ArrayList<>();
            List<String> listUrl;
            String json;
            List<String> listJson = new ArrayList<>();
            List<AutocompleteObject> locationAutoCompletes = new ArrayList<>();

            // add to list by ordinary
            if (SearchRouteActivity.mapLocation.get(SearchField.FROM_LOCATION) != null) {
                locationAutoCompletes.add(mapLocation.get(SearchField.FROM_LOCATION));
            }else {
                String latlng = GPSServiceOld.gpsServiceInstance.getLatitude() + "," +
                        GPSServiceOld.gpsServiceInstance.getLongitude();
                        locationAutoCompletes.add(new AutocompleteObject(latlng, ""));
            }
            if (SearchRouteActivity.mapLocation.get(SearchField.TO_LOCATION) != null) {
                locationAutoCompletes.add(mapLocation.get(SearchField.TO_LOCATION));
            }
            if (SearchRouteActivity.mapLocation.get(SearchField.WAY_POINT_1) != null) {
                locationAutoCompletes.add(mapLocation.get(SearchField.WAY_POINT_1));
            }
            if (SearchRouteActivity.mapLocation.get(SearchField.WAY_POINT_2) != null) {
                locationAutoCompletes.add(mapLocation.get(SearchField.WAY_POINT_2));
            }

            if(!optimize) {
                if(mapLocation.size() == 3) {
                    listUrl = GoogleAPIUtils.getThreePointWithoutOptimizeDirection(locationAutoCompletes);
                    listLegFinal.addAll(JSONParseUtils.sortLegForThreePointWithoutOptimize(listUrl));
                } else if (mapLocation.size() == 4) {
                    listUrl = GoogleAPIUtils.getFourPointWithoutOptimizeDirection(locationAutoCompletes);
                    listLegFinal.addAll(JSONParseUtils.sortLegForFourPointWithoutOptimize(listUrl));
                }
                try {
                    listUrl = GoogleAPIUtils.getFourPointDirection(locationAutoCompletes, optimize);
                    json = NetworkUtils.download(listUrl.get(0));
                    jsonObject = new JSONObject(json);
                    status = jsonObject.getString("status");
                    if ((status.equals(GoogleApiCode.NO_RESULT)) || status.equals(GoogleApiCode.NOT_FOUND)
                            || status.equals(GoogleApiCode.OVER_QUERY_LIMIT)) {
                        return null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                listUrl = GoogleAPIUtils.getFourPointDirection(locationAutoCompletes, optimize);
                for (int n = 0; n < listUrl.size(); n++) {
                    json = NetworkUtils.download(listUrl.get(n));
                    listJson.add(json);
                }

                try {
                    jsonObject = new JSONObject(listJson.get(0));
                    status = jsonObject.getString("status");
                    if ((status.equals(GoogleApiCode.NOT_FOUND)) || status.equals(GoogleApiCode.NO_RESULT)
                            || status.equals(GoogleApiCode.OVER_QUERY_LIMIT)) {
                        return null;
                    } else {
                        for (int i = 0; i < listJson.size(); i++) {
                            listLegFinal.addAll(JSONParseUtils.getListLegWithFourPoint(listJson.get(i)));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return listLegFinal;
        }

        @Override
        protected void onPostExecute(List<Leg> listLegFinal) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            activity.searchType = null;
            activity.needToSearch = false;

            if((listLegFinal == null) || (listLegFinal.size() == 0)){
                listError = new ArrayList<String>();
                listError.add("Internet có vấn đề, xin thử lại....");
                recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                return;
            }
            if (status.equals(GoogleApiCode.NOT_FOUND)) {
                listError = new ArrayList<String>();
                listError.add("Vị trí bạn nhập không được tìm thấy");
                recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                return;
            }

            if (status.equals(GoogleApiCode.NO_RESULT)) {
                listError = new ArrayList<String>();
                listError.add("Vị trí bạn nhập không có kết quả");
                recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                return;
            }

            if (status.equals(GoogleApiCode.OVER_QUERY_LIMIT)) {
                listError = new ArrayList<String>();
                listError.add("Hết quota cmnr");
                recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                return;
            }
            if(mapLocation.size() == 3) {
                listLegFinal = JSONParseUtils.sortThreePoint(listLegFinal);
            } else if (mapLocation.size() == 4) {
                listLegFinal = JSONParseUtils.sortFourPoint(listLegFinal);
            }
            SearchRouteActivity.listLeg = listLegFinal;
            recyclerView.setAdapter(new MotorAdapter());
        }
    }
}
