package com.fpt.router.adapter.test;

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
import com.fpt.router.activity.test.GoogleMapMotorbikeFourPoint;
import com.fpt.router.library.model.motorbike.RouterDetailFourPoint;

import java.util.List;

/**
 * Created by asus on 10/11/2015.
 */
public class MotorFourPointAdapter extends RecyclerView.Adapter<MotorFourPointAdapter.RouterViewHolder> {

    List<RouterDetailFourPoint> routerDetailFourPoints;

    public MotorFourPointAdapter(List<RouterDetailFourPoint> routerDetailFourPoints){
        this.routerDetailFourPoints = routerDetailFourPoints;
    }

    @Override
    public RouterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_list_motorbike_four_point,parent,false);
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
        holder.duration.setText(routerDetailFourPoints.get(position).getDuration() + "mins");
        holder.distance.setText(routerDetailFourPoints.get(position).getDistance()+"km");
        holder.startLocation.setText(routerDetailFourPoints.get(position).getStartLocation());
        holder.endLocation.setText(routerDetailFourPoints.get(position).getEndLocation());
        holder.way_point_1.setText(routerDetailFourPoints.get(position).getWay_point_1());
        holder.way_point_2.setText(routerDetailFourPoints.get(position).getWay_point_2());
    }

    @Override
    public int getItemCount() {
        return routerDetailFourPoints.size();
    }

    public class RouterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Context context;
        CardView cv;
        TextView duration;
        TextView distance;
        TextView startLocation;
        TextView endLocation;
        TextView way_point_1;
        TextView way_point_2;
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
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, GoogleMapMotorbikeFourPoint.class);
            Bundle bundle = new Bundle();
            RouterDetailFourPoint routerDetailFourPoint = getRouterDetailFourPoint(getPosition());
            bundle.putSerializable("routerDetailFourPoint", routerDetailFourPoint);
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);
        }
    }
    public RouterDetailFourPoint getRouterDetailFourPoint(int position){
        return routerDetailFourPoints.get(position);
    }
}
