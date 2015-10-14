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
import com.fpt.router.activity.TwoPointMotorbikeActivity;
import com.fpt.router.library.model.motorbike.RouterDetailTwoPoint;

import java.util.List;

/**
 * Created by asus on 10/12/2015.
 */
public class MotorTwoPointAdapter extends RecyclerView.Adapter<MotorTwoPointAdapter.RouterViewHolder> {
    List<RouterDetailTwoPoint> routerDetailTwoPoints;

    public MotorTwoPointAdapter(List<RouterDetailTwoPoint> routerDetailTwoPoints) {
        this.routerDetailTwoPoints = routerDetailTwoPoints;
    }

    @Override
    public RouterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_list_motorbike_two_point,parent,false);
        RouterViewHolder routerViewHolder = new RouterViewHolder(v);
        return routerViewHolder;
    }

    @Override
    public void onBindViewHolder(RouterViewHolder holder, int position) {
        if(position == 0){
            holder.title.setText("Tuyến đường được đề nghị ");
        }else{
            holder.title.setText("Thêm kết quả cho đi motorbike ");
        }
        holder.duration.setText(routerDetailTwoPoints.get(position).getDuration() + "mins");
        holder.distance.setText(routerDetailTwoPoints.get(position).getDistance()+"km");
        holder.startLocation.setText(routerDetailTwoPoints.get(position).getStartLocation());
        holder.endLocation.setText(routerDetailTwoPoints.get(position).getEndLocation());
    }

    @Override
    public int getItemCount() {
        return routerDetailTwoPoints.size();
    }

    public class RouterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final Context context;
        CardView cv;
        TextView duration;
        TextView distance;
        TextView startLocation;
        TextView endLocation;
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
            title = (TextView) itemView.findViewById(R.id.txtTitle);
        }


        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, TwoPointMotorbikeActivity.class);
            Bundle bundle = new Bundle();
            RouterDetailTwoPoint routerDetailTwoPoint = getRouterDetailTowDetailTwoPoint(getPosition());
            bundle.putSerializable("routerDetailTwoPoint", routerDetailTwoPoint);
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);
        }
    }

    public  RouterDetailTwoPoint getRouterDetailTowDetailTwoPoint(int position){
        return routerDetailTwoPoints.get(position);
    }
}
