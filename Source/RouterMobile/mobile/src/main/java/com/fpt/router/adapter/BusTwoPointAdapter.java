package com.fpt.router.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.activity.BusDetailTwoPointActivity;
import com.fpt.router.adapter.test.TestAdapter;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.bus.Segment;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 10/13/2015.
 */
public class BusTwoPointAdapter extends RecyclerView.Adapter<BusTwoPointAdapter.BusViewHoder> {

    List<Result> results;
    Result result;



    public BusTwoPointAdapter(List<Result> results){
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
        String viewDetail = null;
        List<Integer> images = new ArrayList<Integer>();
        result = results.get(position);
        List<INode> nodeList = result.nodeList;
        //set list image
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

        //set total segment
        for (int j=0;j<nodeList.size();j++){
            if((nodeList.get(j) instanceof Segment) && (nodeList.get(j+1) instanceof Segment)) {
                viewDetail = "Tổng số tuyến xe bus: "+result.totalTransfer+" (tuyến số: "+
                        ((Segment) nodeList.get(j)).routeNo+" - tuyến số: "+
                        ((Segment) nodeList.get(j=j+1)).routeNo+")";
            }
        }

        if(position == 0){
            holder.txtTitle.setText("Tuyến đường được đề nghị ");
        }else{
            holder.txtTitle.setText("Thêm kết quả cho đi motorbike ");
        }

        /*List<String> items = new ArrayList<String>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
*/
        /*TestAdapter showImage = new TestAdapter(holder.context,images);*/
        BusImageAdapter showImage = new BusImageAdapter(holder.context,images);
        /*ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(holder.context,R.layout.simple_list_item_1,images);*/
        holder.tvList.setAdapter(showImage);

        holder.txtDuration.setText(result.minutes+" mins");
        double totalDistance = result.totalDistance/1000;
        totalDistance = Math.floor(totalDistance*100)/100;
        holder.txtDistance.setText(String.valueOf(totalDistance)+" km");
        holder.txtContent.setText(viewDetail);
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
           /* Intent intent = new Intent(context, BusDetailTwoPointActivity.class);
            Bundle bundle = new Bundle();
            Result result = getResult(getPosition());
            bundle.putSerializable("result", result);
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);*/

        }
    }

    public Result getResult(int position){
        return results.get(position);
    }
}
