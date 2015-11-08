package com.fpt.router.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.activity.SearchDetailActivity;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.widget.CustomLinearLayoutManager;

import java.util.List;

/**
 * Created by ngoan on 10/21/2015.
 */
public class BusFourPointAdapterTest extends RecyclerView.Adapter<BusFourPointAdapterTest.BusViewHoder> {

    List<Journey> journeys;
    Journey journey;
    List<Result> results;




    public BusFourPointAdapterTest(List<Journey> journeys){
        this.journeys = journeys;
    }

    @Override
    public BusViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bus_four_point_design,parent,false);
        BusViewHoder busViewHoder = new BusViewHoder(v);
        return busViewHoder;
    }

    @Override
    public void onBindViewHolder(final BusViewHoder holder, final int position) {
        journey = journeys.get(position);
        results = journey.results;

        if(position == 0){
            holder.txtTitle.setText("Tuyến đường được đề nghị ");
        }else{
            holder.txtTitle.setText("Thêm kết quả cho đi xe bus ");
        }

        holder.txtDuration.setText(journey.minutes + " phút");
        double totalDistance = journey.totalDistance/1000;
        totalDistance = Math.floor(totalDistance*100)/100;
        holder.txtDistance.setText(String.valueOf(totalDistance) + " km");

        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(holder.context,LinearLayoutManager.HORIZONTAL,false);
        holder.recycler.setLayoutManager(linearLayoutManager);
        BusDetailOneResultFourPointAdapter busDetailOneResultFourPointAdapter = new BusDetailOneResultFourPointAdapter(results);
        holder.recycler.setAdapter(busDetailOneResultFourPointAdapter);
        holder.recycler.setHasFixedSize(true);
        holder.recycler.setItemAnimator(new DefaultItemAnimator());
        holder.recycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        rv.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                    case MotionEvent.ACTION_UP:
                        //Allow ScrollView to intercept touch events once again.
                        rv.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return journeys.size();
    }


    public class BusViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final Context context;
        TextView txtDuration;
        TextView txtDistance;
        TextView txtTitle;
        RecyclerView recycler;

        public BusViewHoder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();

            txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);
            txtDuration = (TextView) itemView.findViewById(R.id.txtDurationTime);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            recycler = (RecyclerView) itemView.findViewById(R.id.recycler);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, SearchDetailActivity.class);
            Bundle bundle = new Bundle();
            Journey journey = getJourney(getPosition());
            bundle.putSerializable("journey",journey);
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);
        }
    }

    public Journey getJourney(int position){
        return journeys.get(position);
    }
}
