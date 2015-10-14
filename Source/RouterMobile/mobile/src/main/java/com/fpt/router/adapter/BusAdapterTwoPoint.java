package com.fpt.router.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.activity.GoogleMapBusTwoPoint;
import com.fpt.router.activity.GoogleMapMotorbikeTwoPoint;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.bus.Segment;
import com.fpt.router.library.model.motorbike.RouterDetailTwoPoint;

import org.lucasr.twowayview.TwoWayView;

import java.util.List;

/**
 * Created by asus on 10/13/2015.
 */
public class BusAdapterTwoPoint extends RecyclerView.Adapter<BusAdapterTwoPoint.BusViewHoder> {

    List<Result> results;
    Result result;
    List<Integer> images;

    public BusAdapterTwoPoint(List<Result> results){
        this.results = results;
    }

    @Override
    public BusViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_list_bus,parent,false);
        BusViewHoder busViewHoder = new BusViewHoder(v);
        return busViewHoder;
    }

    @Override
    public void onBindViewHolder(BusViewHoder holder, int position) {

        result = results.get(position);
        List<INode> nodeList = result.nodeList;
        for (int i= 0 ;i<nodeList.size() -1;i++){

            if(nodeList.get(i) instanceof Path){
                images.add(R.drawable.ic_directions_walk_black_24dp);
                images.add(R.drawable.ic_chevron_right_black_24dp);
            }
            if(nodeList.get(i) instanceof Segment){
                images.add(R.drawable.ic_directions_bus_black_24dp);
                images.add(R.drawable.ic_chevron_right_black_24dp);
            }
        }
        if(nodeList.get(nodeList.size() - 1) instanceof Path){
            images.add(R.drawable.ic_directions_walk_black_24dp);
        }

        if(nodeList.get(nodeList.size() - 1) instanceof Segment){
            images.add(R.drawable.ic_directions_bus_black_24dp);
        }

        if(position == 0){
            holder.txtTitle.setText("Tuyến đường được đề nghị ");
        }else{
            holder.txtTitle.setText("Thêm kết quả cho đi motorbike ");
        }


        BusAdapterShowImage showImage = new BusAdapterShowImage(holder.context,R.layout.activity_show_image,images);
        holder.txtDuration.setText(result.minutes);
        holder.txtDistance.setText(String.valueOf(result.totalDistance));
        holder.txtContent.setText(result.code);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class BusViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final  Context context;
        TwoWayView tvList;
        TextView txtDuration;
        TextView txtDistance;
        TextView txtContent;
        TextView txtTitle;

        public BusViewHoder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();

            txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);
            txtDuration = (TextView) itemView.findViewById(R.id.txtDurationTime);
            txtContent = (TextView) itemView.findViewById(R.id.txtContent);
            tvList = (TwoWayView) itemView.findViewById(R.id.lvItems);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, GoogleMapBusTwoPoint.class);
            Bundle bundle = new Bundle();
            Result result = getResult(getPosition());
            bundle.putSerializable("result", result);
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);

        }
    }

    public Result getResult(int position){
        return results.get(position);
    }
}
