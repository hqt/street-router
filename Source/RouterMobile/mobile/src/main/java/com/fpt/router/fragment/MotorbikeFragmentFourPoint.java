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
import com.fpt.router.adapter.RecyclerAdapterShowError;
import com.fpt.router.model.motorbike.Leg;
import com.fpt.router.model.motorbike.RouterDetailFourPoint;
import com.fpt.router.model.motorbike.Step;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NgoanTT on 10/11/2015.
 */
public class MotorbikeFragmentFourPoint extends Fragment {

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

    private List<RouterDetailFourPoint> routerDetailFourPoints;

    public MotorbikeFragmentFourPoint() {

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
            routerDetailFourPoints = new ArrayList<RouterDetailFourPoint>();
        }

        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }


    private class JSONParseTask extends AsyncTask<String, String, List<RouterDetailFourPoint>> {
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
        protected List<RouterDetailFourPoint> doInBackground(String... args) {
            List<RouterDetailFourPoint> routerDetailFourPointList = new ArrayList<RouterDetailFourPoint>();
            String json;
            String url;

            url = null;
            json = NetworkUtils.download(url);
            try {
                jsonObject = new JSONObject(json);
                status = jsonObject.getString("status");
                if ((status.equals("NOT_FOUND")) || status.equals("ZERO_RESULTS")) {
                    return null;
                } else {
                    /** get Short Path 1*/
                    int duration_1 = 0;
                    double distance_1 = 0;
                    List<Step> steps_1;
                    List<Step> steps_path_1 = new ArrayList<Step>();
                    String json_1 = NetworkUtils.getShortePath(listLocation.get(0), listLocation.get(1), listLocation.get(2), listLocation.get(3), optimize);
                    List<Leg> list_legs_1 = JSONParseUtils.getListLegWithFourPoint(json_1);
                    RouterDetailFourPoint routerDetail_FourPoint_1 = new RouterDetailFourPoint();
                    routerDetail_FourPoint_1.setStartLocation(listLocation.get(0));
                    routerDetail_FourPoint_1.setEndLocation(listLocation.get(1));
                    routerDetail_FourPoint_1.setWay_point_1(listLocation.get(2));
                    routerDetail_FourPoint_1.setWay_point_2(listLocation.get(3));

                    for (Leg leg_1 : list_legs_1) {
                        duration_1 += leg_1.getDetailLocation().getDuration();
                        distance_1 += leg_1.getDetailLocation().getDistance();
                        steps_1 = leg_1.getStep();
                        for (Step step_1 : steps_1) {
                            steps_path_1.add(step_1);
                        }
                    }
                    duration_1 = duration_1/60;
                    distance_1 = distance_1 / 1000 ;
                    distance_1 = Math.floor(distance_1 * 100) / 100;
                    routerDetail_FourPoint_1.setDuration(duration_1);
                    routerDetail_FourPoint_1.setDistance(distance_1);
                    routerDetail_FourPoint_1.setSteps(steps_path_1);
                    routerDetail_FourPoint_1.setLegs(list_legs_1);

                    /** get Short Path 2*/

                    int duration_2 = 0;
                    double distance_2 = 0;
                    List<Step> steps_2;
                    List<Step> steps_path_2 = new ArrayList<Step>();
                    String json_2 = NetworkUtils.getShortePath(listLocation.get(0), listLocation.get(2), listLocation.get(1), listLocation.get(3), optimize);
                    List<Leg> list_legs_2 = JSONParseUtils.getListLegWithFourPoint(json_2);
                    RouterDetailFourPoint routerDetail_FourPoint_2 = new RouterDetailFourPoint();
                    routerDetail_FourPoint_2.setStartLocation(listLocation.get(0));
                    routerDetail_FourPoint_2.setEndLocation(listLocation.get(2));
                    routerDetail_FourPoint_2.setWay_point_1(listLocation.get(1));
                    routerDetail_FourPoint_2.setWay_point_2(listLocation.get(3));

                    for (Leg leg_2 : list_legs_2) {
                        duration_2 += leg_2.getDetailLocation().getDuration();
                        distance_2 += leg_2.getDetailLocation().getDistance();
                        steps_2 = leg_2.getStep();
                        for (Step step_2 : steps_2) {
                            steps_path_2.add(step_2);
                        }
                    }
                    duration_2 = duration_2/60;
                    distance_2 = distance_2 / 1000 ;
                    distance_2 = Math.floor(distance_2 * 100)/100;
                    routerDetail_FourPoint_2.setDuration(duration_2);
                    routerDetail_FourPoint_2.setDistance(distance_2);
                    routerDetail_FourPoint_2.setSteps(steps_path_2);
                    routerDetail_FourPoint_2.setLegs(list_legs_2);

                    /** get Short Path 3*/

                    int duration_3 = 0;
                    double distance_3 = 0;
                    List<Step> steps_3;
                    List<Step> steps_path_3 = new ArrayList<Step>();
                    String json_3 = NetworkUtils.getShortePath(listLocation.get(0), listLocation.get(3), listLocation.get(1), listLocation.get(2), optimize);
                    List<Leg> list_legs_3 = JSONParseUtils.getListLegWithFourPoint(json_3);
                    RouterDetailFourPoint routerDetail_FourPoint_3 = new RouterDetailFourPoint();
                    routerDetail_FourPoint_3.setStartLocation(listLocation.get(0));
                    routerDetail_FourPoint_3.setEndLocation(listLocation.get(3));
                    routerDetail_FourPoint_3.setWay_point_1(listLocation.get(1));
                    routerDetail_FourPoint_3.setWay_point_2(listLocation.get(2));

                    for (Leg leg_3 : list_legs_3) {
                        duration_3 += leg_3.getDetailLocation().getDuration();
                        distance_3 += leg_3.getDetailLocation().getDistance();
                        steps_3 = leg_3.getStep();
                        for (Step step_3 : steps_3) {
                            steps_path_3.add(step_3);
                        }
                    }
                    duration_3 =duration_3/60;
                    distance_3 = distance_3 / 1000 ;
                    distance_3 = Math.floor(distance_3 * 100)/100;
                    routerDetail_FourPoint_3.setDuration(duration_3);
                    routerDetail_FourPoint_3.setDistance(distance_3);
                    routerDetail_FourPoint_3.setSteps(steps_path_3);
                    routerDetail_FourPoint_3.setLegs(list_legs_3);


                    /**check optimize with duration*/
                    if (duration_1 > duration_2) {
                        if (duration_2 > duration_3) {
                            routerDetailFourPointList.add(routerDetail_FourPoint_3);
                            routerDetailFourPointList.add(routerDetail_FourPoint_2);
                            routerDetailFourPointList.add(routerDetail_FourPoint_1);
                        } else {
                            if (duration_1 > duration_3) {
                                routerDetailFourPointList.add(routerDetail_FourPoint_2);
                                routerDetailFourPointList.add(routerDetail_FourPoint_3);
                                routerDetailFourPointList.add(routerDetail_FourPoint_1);
                            } else {
                                routerDetailFourPointList.add(routerDetail_FourPoint_2);
                                routerDetailFourPointList.add(routerDetail_FourPoint_1);
                                routerDetailFourPointList.add(routerDetail_FourPoint_3);
                            }
                        }
                    }else{
                        if(duration_1>duration_3){
                            routerDetailFourPointList.add(routerDetail_FourPoint_3);
                            routerDetailFourPointList.add(routerDetail_FourPoint_1);
                            routerDetailFourPointList.add(routerDetail_FourPoint_2);
                        }else{
                            routerDetailFourPointList.add(routerDetail_FourPoint_1);
                            routerDetailFourPointList.add(routerDetail_FourPoint_3);
                            routerDetailFourPointList.add(routerDetail_FourPoint_2);
                        }
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return routerDetailFourPointList;
        }

        @Override
        protected void onPostExecute(List<RouterDetailFourPoint> routerDetailFourPointList) {
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

            routerDetailFourPoints = routerDetailFourPointList;
            recyclerView.setAdapter(new MotorbikeAdapterFourPoint(routerDetailFourPoints));
        }
    }

}
