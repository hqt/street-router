package com.fpt.router.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.activity.SearchDetailActivity;
import com.fpt.router.adapter.BusDetailOneResultFourPointAdapter;
import com.fpt.router.adapter.BusFourPointViewPagerAdapter;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.widget.CustomLinearLayoutManager;

import java.util.List;

/**
 * Created by ngoan on 11/3/2015.
 */
public class BusFourPointDesignFragment extends Fragment {
    Journey journey;
    public BusFourPointDesignFragment(Journey journey) {
        this.journey = journey;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bus_four_point_design, parent, false);
        TextView txtDuration;
        TextView txtDistance;
        TextView txtTitle;
        RecyclerView recycler;
        Button btn_view_map;
        txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);
        txtDuration = (TextView) itemView.findViewById(R.id.txtDurationTime);
        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        recycler = (RecyclerView) itemView.findViewById(R.id.recycler);
        btn_view_map = (Button) itemView.findViewById(R.id.btn_view_maps);
        if (BusFourPointViewPagerAdapter.tab_position == 0) {
            txtTitle.setText("Tuyến đường được đề nghị ");
        } else {
            txtTitle.setText("Thêm kết quả cho đi xe bus");
        }
        txtDuration.setText(journey.minutes + " phút");
        double totalDistance = journey.totalDistance / 1000;
        totalDistance = Math.floor(totalDistance * 100) / 100;
        txtDistance.setText(String.valueOf(totalDistance) + " km");
        List<Result> results = journey.results;
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