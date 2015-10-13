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
import com.fpt.router.adapter.MotorbikeAdapterFourPoint;
import com.fpt.router.adapter.MotorbikeAdapterTwoPoint;
import com.fpt.router.adapter.RecyclerAdapterShowError;
import com.fpt.router.model.motorbike.Leg;
import com.fpt.router.model.motorbike.RouterDetailFourPoint;
import com.fpt.router.model.motorbike.RouterDetailTwoPoint;
import com.fpt.router.model.motorbike.Step;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 10/11/2015.
 */
public class MotorbikeFragmentTwoPoint extends Fragment {
    /**
     * Main Activity for reference
     */
    private MainSecond activity;
    private List<String> listLocation = MainSecond.listLocation;
    private Boolean optimize = MainSecond.optimize;
    private RecyclerView recyclerView;
    private JSONObject jsonObject;
    private String status;
    private List<String> listError;

    private List<RouterDetailTwoPoint> routerDetailTwoPoints;

    public MotorbikeFragmentTwoPoint() {

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
            routerDetailTwoPoints = new ArrayList<RouterDetailTwoPoint>();
        }

        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    private class JSONParseTask extends AsyncTask<String, String, List<RouterDetailTwoPoint>> {
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
        protected List<RouterDetailTwoPoint> doInBackground(String... args) {
            List<RouterDetailTwoPoint> routerDetailTwoPointList = new ArrayList<RouterDetailTwoPoint>();
            String json;
            String url;
            List<String> listPlaceID = JSONParseUtils.listPlaceID(listLocation);
            url = NetworkUtils.linkGoogleDrirectionForTwoPoint(listPlaceID.get(0), listPlaceID.get(1));
            json = NetworkUtils.download(url);
            try {
                jsonObject = new JSONObject(json);
                status = jsonObject.getString("status");
                if ((status.equals("NOT_FOUND")) || status.equals("ZERO_RESULTS")) {
                    return null;
                } else {
                    int duration = 0;
                    double distance = 0;
                    List<Step> steps = new ArrayList<Step>();
                    List<Leg> legList = JSONParseUtils.getListLegWithTwoPoint(json);
                    for (int i = 0 ;i<legList.size();i++){
                        Leg leg = legList.get(i);
                        duration = leg.getDetailLocation().getDuration();
                        duration = duration/60;
                        distance = leg.getDetailLocation().getDistance();
                        distance = distance / 1000 ;
                        distance = Math.floor(distance * 100) / 100;
                        steps = leg.getStep();
                        RouterDetailTwoPoint routerDetailTwoPoint = new RouterDetailTwoPoint();
                        routerDetailTwoPoint.setStartLocation(leg.getStartAddress());
                        routerDetailTwoPoint.setEndLocation(leg.getEndAddress());
                        routerDetailTwoPoint.setDuration(duration);
                        routerDetailTwoPoint.setDistance(distance);
                        routerDetailTwoPoint.setSteps(steps);
                        routerDetailTwoPoint.setOverview_polyline(leg.getOverview_polyline());
                        routerDetailTwoPoint.setDetailLocation(leg.getDetailLocation());
                        routerDetailTwoPointList.add(routerDetailTwoPoint);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return routerDetailTwoPointList;
        }

        @Override
        protected void onPostExecute(List<RouterDetailTwoPoint> routerDetailTwoPointList) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (status.equals("NOT_FOUND")) {
                listError = new ArrayList<String>();
                listError.add("Vị trí bạn nhập không được tìm thấy");
                recyclerView.setAdapter(new RecyclerAdapterShowError((listError)));
                return;
            }

            if (status.equals("ZERO_RESULTS")) {
                listError = new ArrayList<String>();
                listError.add("Vị trí bạn nhập không có kết quả");
                recyclerView.setAdapter(new RecyclerAdapterShowError((listError)));
                return;
            }

            routerDetailTwoPoints = routerDetailTwoPointList;
            recyclerView.setAdapter(new MotorbikeAdapterTwoPoint(routerDetailTwoPoints));
        }
    }
}
