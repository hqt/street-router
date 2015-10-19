package com.fpt.router.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.activity.SearchDetailActivity;
import com.fpt.router.activity.SearchRouteActivity;
import com.fpt.router.library.model.motorbike.Leg;

import java.util.List;

/**
 * Created by asus on 10/11/2015.
 */
public class MotorFourPointAdapter extends RecyclerView.Adapter<MotorFourPointAdapter.RouterViewHolder> {

    List<String> listLocation = SearchRouteActivity.listLocation;
    List<Leg> listLeg = SearchRouteActivity.listLeg;
    int countPoint = listLocation.size() - 1;
    View v;
    RouterViewHolder routerViewHolder;

    @Override
    public RouterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (listLocation.size() == 2 ){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_list_motorbike_two_point,parent,false);
        }else if (listLocation.size() == 3) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_list_motorbike_three_point,parent,false);
        } else if (listLocation.size() == 4) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_list_motorbike_four_point,parent,false);
        }
        routerViewHolder = new RouterViewHolder(v);
        return routerViewHolder;
    }

    @Override
    public void onBindViewHolder(RouterViewHolder holder, int position) {

        if(position == 0){
            holder.title.setText("Tuyến đường được đề nghị ");
        }else{
            holder.title.setText("Thêm kết quả cho đi motorbike ");
        }
        int duration = 0;
        int distance = 0;
        for(int n = position*countPoint; n < (position+1)*countPoint; n++) {
            duration = duration + listLeg.get(n).getDetailLocation().getDuration();
            distance = distance + listLeg.get(n).getDetailLocation().getDistance();
        }
        Double totalDistance = (double)distance;
        holder.duration.setText(duration/60 + " phút");
        holder.distance.setText(totalDistance/1000+" Km");
        holder.startLocation.setText(listLeg.get(position*countPoint).getStartAddress());
        if (listLocation.size() > 2) {
            holder.way_point_1.setText(listLeg.get(position * countPoint).getEndAddress());
        }
        if (listLocation.size() > 3) {
            holder.way_point_2.setText(listLeg.get(position * countPoint + (countPoint - 1)).getStartAddress());
        }
        holder.endLocation.setText(listLeg.get(position*countPoint+(countPoint-1)).getEndAddress());
    }

    @Override
    public int getItemCount() {
        if(listLeg.size() == 2){
            return 1;
        } else {
            return 3;
        }
    }

    public class RouterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Context context;
        CardView cv;
        TextView duration;
        TextView distance;
        TextView startLocation;
        TextView endLocation;
        TextView way_point_1;
        TextView middle_1;
        TextView way_point_2;
        TextView middle_2;
        TextView title;

        public RouterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
            cv = (CardView) itemView.findViewById(R.id.card_view);
            duration = (TextView) itemView.findViewById(R.id.txtDurationTime);
            distance = (TextView) itemView.findViewById(R.id.txtDistance);
            startLocation = (TextView) itemView.findViewById(R.id.startLocation);
            endLocation = (TextView) itemView.findViewById(R.id.endLocation);
            way_point_1 = (TextView) itemView.findViewById(R.id.way_point_1);
            way_point_2 = (TextView) itemView.findViewById(R.id.way_point_2);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
            middle_1 = (TextView) itemView.findViewById(R.id.middle_1);
            middle_2 = (TextView) itemView.findViewById(R.id.middle_2);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, SearchDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("position", getLayoutPosition());
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);
        }
    }
}
