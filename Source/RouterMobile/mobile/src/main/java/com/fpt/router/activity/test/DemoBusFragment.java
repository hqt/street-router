package com.fpt.router.activity.test;

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
import com.fpt.router.activity.SearchRouteActivity;
import com.fpt.router.adapter.BusTwoPointAdapter;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.utils.JSONUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngoan on 10/15/2015.
 */
public class DemoBusFragment extends Fragment {
    /**
     * Main Activity for reference
     */
    private SearchRouteActivity activity;
    private RecyclerView recyclerView;
    private List<Result> results;


    public DemoBusFragment() {

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

        results = new ArrayList<Result>();

        JSONParseTask jsonParseTask = new JSONParseTask();
        jsonParseTask.execute();

        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(new BusTwoPointAdapter(results));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
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
            File jsonFromServer = new File("D:\\sample.json");
            BufferedReader br = null;
            try {
                br = new BufferedReader(
                        new FileReader(jsonFromServer));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Gson gson1 = JSONUtils.buildGson();
            resultList = gson1.fromJson(br, new TypeToken<List<Result>>(){}.getType());

            return resultList;
        }

        @Override
        protected void onPostExecute(List<Result> resultList) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            results = resultList;
            recyclerView.setAdapter(new BusTwoPointAdapter(results));
        }
    }
}
