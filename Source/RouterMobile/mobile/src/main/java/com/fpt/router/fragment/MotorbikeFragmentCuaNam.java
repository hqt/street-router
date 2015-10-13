package com.fpt.router.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.fpt.router.R;
import com.fpt.router.activity.MainSecond;
import com.fpt.router.adapter.MotorbikeAdapterCuaNam;
import com.fpt.router.adapter.RecyclerAdapterShowError;
import com.fpt.router.model.motorbike.Leg;
import com.fpt.router.utils.JSONParseUtils;
import com.fpt.router.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nguyen Trung Nam on 10/12/2015.
 */
public class MotorbikeFragmentCuaNam extends Fragment{
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

    public static List<Leg> listLeg = new ArrayList<>();

    public MotorbikeFragmentCuaNam() {

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
            listLeg = new ArrayList<>();
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
            List<Leg> listLegFinal = new ArrayList<>();
            List<String> listUrl;
            String json;
            List<String> listPlaceID = JSONParseUtils.listPlaceID(listLocation);
            List<String> listJson = new ArrayList<>();
            if(!optimize && listLocation.size() == 4) {
                listUrl = NetworkUtils.linkFourPointWithoutOptimize(listPlaceID);
                listLegFinal.addAll(JSONParseUtils.sortLegForFourPointWithoutOptimize(listUrl));
                try {
                    listUrl = NetworkUtils.linkCuaNam(listPlaceID, optimize);
                    json = NetworkUtils.download(listUrl.get(0));
                    jsonObject = new JSONObject(json);
                    status = jsonObject.getString("status");
                    if ((status.equals("NOT_FOUND")) || status.equals("ZERO_RESULTS")) {
                        return null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                listUrl = NetworkUtils.linkCuaNam(listPlaceID, optimize);
                for (int n = 0; n < listUrl.size(); n++) {
                    json = NetworkUtils.download(listUrl.get(n));
                    listJson.add(json);
                }

                try {
                    jsonObject = new JSONObject(listJson.get(0));
                    status = jsonObject.getString("status");
                    if ((status.equals("NOT_FOUND")) || status.equals("ZERO_RESULTS")) {
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

            listLeg = listLegFinal;
            recyclerView.setAdapter(new MotorbikeAdapterCuaNam());
        }
    }
}
