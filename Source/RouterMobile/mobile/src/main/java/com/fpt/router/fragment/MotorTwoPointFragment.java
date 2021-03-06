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
import com.fpt.router.adapter.MotorAdapter;
import com.fpt.router.adapter.ErrorMessageAdapter;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.config.AppConstants.SearchField;
import com.fpt.router.library.model.bus.BusLocation;
import com.fpt.router.library.model.common.AutocompleteObject;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.model.motorbike.RouterDetailTwoPoint;
import com.fpt.router.library.model.motorbike.Step;
import com.fpt.router.service.GPSServiceOld;
import com.fpt.router.utils.GoogleAPIUtils;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 10/11/2015.
 */
public class MotorTwoPointFragment extends Fragment {
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

    public static List<RouterDetailTwoPoint> routerDetailTwoPoints = new ArrayList<RouterDetailTwoPoint>();

    public MotorTwoPointFragment() {

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


        if (mapLocation.size() > 1) {

            View v = inflater.inflate(R.layout.fragment_bus_twopoint, container, false);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            if (activity.needToSearch && activity.searchType == SearchRouteActivity.SearchType.MOTOR_TWO_POINT) {
                JSONParseTask jsonParseTask = new JSONParseTask();
                jsonParseTask.execute();
            } else if ((SearchRouteActivity.listLeg != null)
                    /*&& (activity.searchType == SearchRouteActivity.SearchType.MOTOR_TWO_POINT)*/) {
                recyclerView.setAdapter(new MotorAdapter());
            }

            return v;

        } else {
            TextView textView = new TextView(getActivity());
            return textView;
        }
    }

    class JSONParseTask extends AsyncTask<String, String, List<Leg>> {
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
            List<Leg> listLeg = new ArrayList<>();
            String json;
            String url;
            List<AutocompleteObject> locationAutoCompletes = new ArrayList<>();
            if (SearchRouteActivity.mapLocation.get(AppConstants.SearchField.FROM_LOCATION) != null) {
                locationAutoCompletes.add(mapLocation.get(AppConstants.SearchField.FROM_LOCATION));
            } else {
                String latlng = GPSServiceOld.gpsServiceInstance.getLatitude() + "," +
                        GPSServiceOld.gpsServiceInstance.getLongitude();
                locationAutoCompletes.add(new AutocompleteObject(latlng, ""));
            }
            if (SearchRouteActivity.mapLocation.get(AppConstants.SearchField.TO_LOCATION) != null) {
                locationAutoCompletes.add(mapLocation.get(AppConstants.SearchField.TO_LOCATION));
            }
            url = GoogleAPIUtils.getTwoPointDirection(locationAutoCompletes.get(0), locationAutoCompletes.get(1));
            json = NetworkUtils.download(url);
            try {
                jsonObject = new JSONObject(json);
                status = jsonObject.getString("status");
                if ((status.equals("NOT_FOUND")) || status.equals("ZERO_RESULTS") || status.equals("OVER_QUERY_LIMIT")) {
                    return null;
                } else {
                    int duration = 0;
                    double distance = 0;
                    List<Step> steps = new ArrayList<Step>();
                    listLeg = JSONParseUtils.getListLegWithTwoPoint(json);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return listLeg;
        }

        @Override
        protected void onPostExecute(List<Leg> listLeg) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            activity.searchType = null;
            activity.needToSearch = false;

            if((listLeg == null) || (listLeg.size() == 0)){
                listError = new ArrayList<String>();
                listError.add("Internet có vấn đề, xin thử lại....");
                recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                return;
            }
            if (status.equals("NOT_FOUND")) {
                listError = new ArrayList<String>();
                listError.add("Vị trí bạn nhập không được tìm thấy");
                recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                return;
            }

            if (status.equals("ZERO_RESULTS")) {
                listError = new ArrayList<String>();
                listError.add("Vị trí bạn nhập không có kết quả");
                recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                return;
            }

            if (status.equals("OVER_QUERY_LIMIT")) {
                listError = new ArrayList<String>();
                listError.add("Hết quota cmnr");
                recyclerView.setAdapter(new ErrorMessageAdapter((listError)));
                return;
            }
            if(listLeg.size() > 1) {
                for (int x = 0; x < listLeg.size() - 1; x++) {
                    for (int y = x+1; y < listLeg.size(); y++) {
                        if (listLeg.get(y).getDetailLocation().getDuration() < listLeg.get(x).getDetailLocation().getDuration()) {
                            Leg leg = listLeg.get(x);
                            listLeg.set(x, listLeg.get(y));
                            listLeg.set(y, leg);
                        }
                    }
                }
            }
            SearchRouteActivity.listLeg = listLeg;
            recyclerView.setAdapter(new MotorAdapter());
        }
    }
}
