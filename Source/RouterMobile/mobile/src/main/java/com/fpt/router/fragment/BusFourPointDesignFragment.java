package com.fpt.router.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.activity.SearchDetailActivity;
import com.fpt.router.adapter.BusDetailOneResultFourPointAdapter;
import com.fpt.router.adapter.BusFourPointViewPagerAdapter;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.bus.Segment;
import com.fpt.router.widget.CustomLinearLayoutManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ngoan on 11/3/2015.
 */
public class BusFourPointDesignFragment extends Fragment {
    Journey journey;

    public BusFourPointDesignFragment() {

    }

    public BusFourPointDesignFragment(Journey journey) {
        this.journey = journey;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bus_four_point_design, parent, false);

        Toolbar toolbar = (Toolbar) itemView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        List<Result> results = journey.results;
        List<String> numberBusFinal = new ArrayList<>();
        for(int i=0;i<results.size();i++){
            Result result = results.get(i);
            List<INode> iNodeList = result.nodeList;
            for(int j=0;j<iNodeList.size();j++){
                if(iNodeList.get(j) instanceof Segment){
                    Segment segment = (Segment) iNodeList.get(j);
                    numberBusFinal.add(String.valueOf(segment.routeNo));
                }
            }
        }

        TextView txtDuration;
        TextView txtDistance;
        TextView txtTitle;
        RecyclerView recycler;
        ImageView btn_view_map;
        TextView txtBusNumber;
        txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);
        txtDuration = (TextView) itemView.findViewById(R.id.txtDurationTime);
        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        recycler = (RecyclerView) itemView.findViewById(R.id.recycler);
        btn_view_map = (ImageView) itemView.findViewById(R.id.btn_view_maps);
        txtBusNumber = (TextView) itemView.findViewById(R.id.txtBusNumberInToolbar);
        String[]  nBusFinal = numberBusFinal.toArray(new String[numberBusFinal.size()]);
        txtBusNumber.setText(Arrays.toString(nBusFinal));
        if (BusFourPointViewPagerAdapter.tab_position == 0) {
            txtTitle.setText("Tuyến đường được đề nghị ");
        } else {
            txtTitle.setText("Thêm kết quả cho đi xe bus");
        }
        txtDuration.setText(journey.minutes + " phút");
        double totalDistance = journey.totalDistance / 1000;
        totalDistance = Math.floor(totalDistance * 100) / 100;
        txtDistance.setText(String.valueOf(totalDistance) + " km");
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        BusDetailOneResultFourPointAdapter busDetailOneResultFourPointAdapter = new BusDetailOneResultFourPointAdapter(results);
        recycler.setAdapter(busDetailOneResultFourPointAdapter);

        btn_view_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("journey",journey);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return itemView;
    }
}