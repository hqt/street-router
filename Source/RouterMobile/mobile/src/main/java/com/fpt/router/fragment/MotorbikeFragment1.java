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
import com.fpt.router.adapter.MotorbikeRecyclerAdapter;
import com.fpt.router.adapter.RecyclerAdapterShowError;
import com.fpt.router.model.motorbike.Leg;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 10/8/2015.
 */
public class MotorbikeFragment1 extends Fragment {

    /**
     * Main Activity for reference
     */
    private MainSecond activity;
    private List<String> listLocation = MainSecond.listLocation;
    private Boolean optimize = MainSecond.optimize;
    /**
     * Main Activity for reference
     */
    public static List<Leg> listLeg;
    private RecyclerView recyclerView;
    private JSONObject jsonObject;
    private String status;
    private List<String> listError;

    /**
     * empty constructor
     * must have for fragment
     */
    public MotorbikeFragment1() {

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
            listLeg = new ArrayList<Leg>();
        }


        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
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
            pDialog.show();

        }

        @Override
        protected List<Leg> doInBackground(String... args) {

            String json;
            String url;
            List<Leg> listLeg = new ArrayList<Leg>();
            List<String> jsons = new ArrayList<String>();

            url = NetworkUtils.linkGoogleDrirection(listLocation, optimize);
            json = NetworkUtils.download(url);
            try {
                jsonObject = new JSONObject(json);
                status = jsonObject.getString("status");
                if ((status.equals("NOT_FOUND")) || status.equals("ZERO_RESULTS")) {
                    return null;
                } else {
                    if (listLocation.size() == 2) {
                        listLeg = JSONParseUtils.getListLegWithTwoPoint(json);
                    } else {
                        String json_1 = NetworkUtils.getShortePath(listLocation.get(0), listLocation.get(1), listLocation.get(2), listLocation.get(3), optimize);
                        String json_2 = NetworkUtils.getShortePath(listLocation.get(0), listLocation.get(2), listLocation.get(1), listLocation.get(3), optimize);
                        String json_3 = NetworkUtils.getShortePath(listLocation.get(0), listLocation.get(3), listLocation.get(1), listLocation.get(2), optimize);
                        jsons.add(json_1);
                        jsons.add(json_2);
                        jsons.add(json_3);
                        int  duration_listLeg_1 = 0;
                        int duration_listLeg_2 = 0;
                        int duration_listLeg_3 = 0;
                        List<Leg> listLeg_1 = JSONParseUtils.getListLegWithFourPoint(json_1);
                        List<Leg> listLeg_2 = JSONParseUtils.getListLegWithFourPoint(json_2);
                        List<Leg> listLeg_3 = JSONParseUtils.getListLegWithFourPoint(json_3);
                        for (Leg leg1 : listLeg_1){
                            duration_listLeg_1 += leg1.getDetailLocation().getDuration();
                        }
                        for (Leg leg2 : listLeg_2){
                            duration_listLeg_2 += leg2.getDetailLocation().getDuration();
                        }
                        for (Leg leg3 : listLeg_3){
                            duration_listLeg_3 += leg3.getDetailLocation().getDuration();
                        }

                        if(duration_listLeg_1 > duration_listLeg_2){
                            if(duration_listLeg_2 > duration_listLeg_3){
                                for (Leg leg3 : listLeg_3){
                                    listLeg.add(leg3);
                                }
                                for (Leg leg2 : listLeg_2){
                                    listLeg.add(leg2);
                                }
                                for (Leg leg1 : listLeg_1){
                                    listLeg.add(leg1);
                                }
                            }else {

                                if(duration_listLeg_1 > duration_listLeg_3){
                                    for (Leg leg2 : listLeg_2){
                                        listLeg.add(leg2);
                                    }
                                    for (Leg leg3 : listLeg_3){
                                        listLeg.add(leg3);
                                    }
                                    for (Leg leg1 : listLeg_1){
                                        listLeg.add(leg1);
                                    }
                                }else{
                                    for (Leg leg2 : listLeg_2){
                                        listLeg.add(leg2);
                                    }

                                    for (Leg leg1 : listLeg_1){
                                        listLeg.add(leg1);
                                    }
                                    for (Leg leg3 : listLeg_3){
                                        listLeg.add(leg3);
                                    }

                                }

                            }
                        }else {
                            if(duration_listLeg_1 > duration_listLeg_3){

                                for (Leg leg3 : listLeg_3){
                                    listLeg.add(leg3);
                                }

                                for (Leg leg1 : listLeg_1){
                                    listLeg.add(leg1);
                                }
                                for (Leg leg2 : listLeg_2){
                                    listLeg.add(leg2);
                                }

                            }else {
                                if(duration_listLeg_2 > duration_listLeg_3){
                                    for (Leg leg1 : listLeg_1){
                                        listLeg.add(leg1);
                                    }
                                    for (Leg leg3 : listLeg_3){
                                        listLeg.add(leg3);
                                    }

                                    for (Leg leg2 : listLeg_2){
                                        listLeg.add(leg2);
                                    }
                                }else{
                                    for (Leg leg1 : listLeg_1){
                                        listLeg.add(leg1);
                                    }
                                    for (Leg leg2 : listLeg_2){
                                        listLeg.add(leg2);
                                    }
                                    for (Leg leg3 : listLeg_3){
                                        listLeg.add(leg3);
                                    }


                                }


                            }
                        }

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return listLeg;
        }

        @Override
        protected void onPostExecute(List<Leg> legs) {
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

            listLeg = legs;
            recyclerView.setAdapter(new MotorbikeRecyclerAdapter(listLeg, listLocation.size()));
        }
    }
}
