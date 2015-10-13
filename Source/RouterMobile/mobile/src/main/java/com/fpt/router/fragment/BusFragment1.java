package com.fpt.router.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpt.router.R;
import com.fpt.router.activity.MainSecond;
import com.fpt.router.library.model.motorbike.Leg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 10/8/2015.
 */
public class BusFragment1 extends Fragment {

    /** Main Activity for reference */
    private MainSecond activity;
    private List<String> listLocation = MainSecond.listLocation;
    private Boolean optimize = MainSecond.optimize;
    /** Main Activity for reference */
    private List<Leg> listLeg;
    private RecyclerView recyclerView;
    private static final String TAB_POSITION = "tab_position";
    /** empty constructor
     * must have for fragment
     */

    public BusFragment1(){

    }

    public BusFragment1 newInstance(int tabPosition){

        BusFragment1 fragment = new BusFragment1();
        Bundle args = new Bundle();
        args.putInt(TAB_POSITION,tabPosition);
        fragment.setArguments(args);
        return fragment;
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

        listLeg = new ArrayList<Leg>();
        View v =  inflater.inflate(R.layout.fragment_list_view, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*recyclerView.setAdapter(new MotorbikeRecyclerAdapter(listLeg,listLocation.size()));*/
        return v;
    }
}
