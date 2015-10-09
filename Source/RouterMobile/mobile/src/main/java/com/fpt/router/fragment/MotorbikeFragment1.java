package com.fpt.router.fragment;

import android.app.ProgressDialog;
import android.app.job.JobParameters;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fpt.router.R;
import com.fpt.router.activity.MainActivity;
import com.fpt.router.adapter.DesignDemoRecyclerAdapter;
import com.fpt.router.adapter.DesignRecyclerAdapter1;
import com.fpt.router.model.bus.ArrayAdapterItem;
import com.fpt.router.model.bus.DetailRoute;
import com.fpt.router.model.bus.ListRoute;
import com.fpt.router.model.bus.OnItemClickListenerListViewItem;
import com.fpt.router.model.motorbike.DetailLocation;
import com.fpt.router.model.motorbike.Leg;
import com.fpt.router.model.motorbike.Location;
import com.fpt.router.utils.DecodeUtils;
import com.fpt.router.utils.GPSUtils;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.MapUtils;
import com.fpt.router.utils.NetworkUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by asus on 10/8/2015.
 */
public class MotorbikeFragment1 extends Fragment {

    /** Main Activity for reference */
    private MainActivity activity;
    private List<String> listLocation = MainActivity.listLocation;
    private Boolean optimize = MainActivity.optimize;
    /** Main Activity for reference */
    private List<Leg> listLeg;
    private RecyclerView recyclerView;
    /** empty constructor
     * must have for fragment
     */
    public MotorbikeFragment1() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (MainActivity) context;
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

        if(listLocation.size()>1) {
            JSONParseTask jsonParseTask = new JSONParseTask();
            jsonParseTask.execute();

        }else{
            listLeg = new ArrayList<Leg>();
        }


        View v =  inflater.inflate(R.layout.fragment_list_view, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*recyclerView.setAdapter(new DesignRecyclerAdapter1(listLeg));*/
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
            ArrayList<Leg> listLeg;
            String url = NetworkUtils.linkGoogleDrirection(listLocation, optimize);
            json = NetworkUtils.download(url);
            if(listLocation.size() == 2){
                listLeg = JSONParseUtils.getListLegWithTwoPoint(json);
            }else {
                listLeg = JSONParseUtils.getListLegWithFourPoint(json);
            }

            return listLeg;
        }

        @Override
        protected void onPostExecute(List<Leg> legs) {
            if(pDialog.isShowing()) {
                pDialog.dismiss();
            }
            listLeg =  legs;

            recyclerView.setAdapter(new DesignRecyclerAdapter1(listLeg,listLocation.size()));

            /*super.onPostExecute(legs);*/

        }
    }


}
