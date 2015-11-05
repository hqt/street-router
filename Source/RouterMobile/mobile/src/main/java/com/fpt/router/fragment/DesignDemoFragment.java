package com.fpt.router.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.activity.SearchDetailActivity;
import com.fpt.router.adapter.BusListItemResultFourPoint;
import com.fpt.router.adapter.BusViewPagerAdapter;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.widget.MyLinearLayoutManager;

import java.util.List;

/**
 * Created by ngoan on 11/3/2015.
 */
public class DesignDemoFragment extends Fragment {
    private static final String TAB_POSITION = "tab_position";
    Journey journey;

    public DesignDemoFragment(Journey journey) {
        this.journey = journey;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_list_four_bus, parent, false);
        TextView txtDuration;
        TextView txtDistance;
        TextView txtTitle;
        RecyclerView recycler;
        ScrollView scrollView;
        //LinearLayout linearClick;
        txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);
        txtDuration = (TextView) itemView.findViewById(R.id.txtDurationTime);
        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        recycler = (RecyclerView) itemView.findViewById(R.id.recycler);
        scrollView = (ScrollView) itemView.findViewById(R.id.scrollView);
        //linearClick = (LinearLayout) itemView.findViewById(R.id.linearClickEvent);
        if (BusViewPagerAdapter.tab_position == 0) {
            txtTitle.setText("Tuyến đường được đề nghị ");
        } else {
            txtTitle.setText("Thêm kết quả cho đi xe bus");
        }
        txtDuration.setText(journey.minutes + " phút");
        double totalDistance = journey.totalDistance / 1000;
        totalDistance = Math.floor(totalDistance * 100) / 100;
        txtDistance.setText(String.valueOf(totalDistance) + " km");
        List<Result> results = journey.results;
        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        BusListItemResultFourPoint busListItemResultFourPoint = new BusListItemResultFourPoint(results);
        recycler.setAdapter(busListItemResultFourPoint);

        View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("journey",journey);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
        scrollView.getChildAt(0).setOnClickListener(mOnClickListener);

        /*linearClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), SearchDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("journey",journey);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/

        return itemView;
    }
}