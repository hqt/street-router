package com.fpt.router.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.library.model.bus.BusImage;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.bus.Segment;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngoan on 10/21/2015.
 */
public class BusListItemResultFourPoint extends RecyclerView.Adapter<BusListItemResultFourPoint.BusItemViewHolder> {

    List<Result> results;
    Result result;

    public BusListItemResultFourPoint(List<Result> results){
        this.results = results;
    }

    @Override
    public BusItemViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_four_bus,parent,false);
        BusItemViewHolder viewHolder = new BusItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BusItemViewHolder holder, int position) {
        String viewDetail = "";
        String from = "";
        String to = "";
        List<BusImage> images = new ArrayList<BusImage>();
        result = results.get(position);
        List<INode> nodeList = result.nodeList;
        // set from and to
        if(nodeList.get(0) instanceof Path){
            from = ((Path)nodeList.get(0)).stationFromName;
        }
        if(nodeList.get(0) instanceof Segment){
            List<Path> paths = new ArrayList<Path>();
            paths = ((Segment)nodeList.get(0)).paths;
            for (int n = 0; n< paths.size();n++){
                from = paths.get(0).stationFromName;
            }
        }

        if(nodeList.get(nodeList.size() - 1) instanceof Path){
            to = ((Path)nodeList.get(nodeList.size() - 1)).stationToName;
        }
        if(nodeList.get(nodeList.size() - 1) instanceof Segment){
            List<Path> paths = new ArrayList<Path>();
            paths = ((Segment)nodeList.get(nodeList.size() - 1)).paths;
            for (int n = 0; n< paths.size();n++){
                to = paths.get(paths.size() - 1).stationToName;
            }
        }
        //set list image
        for (int i= 0 ;i<nodeList.size() -1;i++){

            if(nodeList.get(i) instanceof Path){
                images.add(new BusImage(R.drawable.ic_directions_walk_black_24dp,""));
                images.add(new BusImage(R.drawable.ic_chevron_right_black_24dp,""));
            }
            if(nodeList.get(i) instanceof Segment){
                Segment segment = (Segment) nodeList.get(i);
                images.add(new BusImage(R.drawable.ic_directions_bus_black_24dp,String.valueOf(segment.routeNo)));
                images.add(new BusImage(R.drawable.ic_chevron_right_black_24dp,""));

            }
        }
        if(nodeList.get(nodeList.size() - 1) instanceof Path){
            images.add(new BusImage(R.drawable.ic_directions_walk_black_24dp,""));
        }

        if(nodeList.get(nodeList.size() - 1) instanceof Segment){
            Segment segment = (Segment) nodeList.get(nodeList.size() - 1);
            images.add(new BusImage(R.drawable.ic_directions_bus_black_24dp,String.valueOf(segment.routeNo)));
        }

        //set total segment
        List<Segment> segments = new ArrayList<Segment>();
        for(int j=0;j<nodeList.size();j++){
            if(nodeList.get(j) instanceof Segment){
                segments.add((Segment) nodeList.get(j));
            }
        }
        viewDetail = "Tổng số tuyến : "+result.totalTransfer +"\n";
        for (int m=0;m<segments.size();m++){
            List<Path> paths = new ArrayList<Path>();
            paths = segments.get(m).paths;
            viewDetail +="Tuyến số: "+segments.get(m).routeNo+"\n   Lên trạm : "+paths.get(0).stationFromName+"\n   Xuống trạm : "+paths.get(paths.size()-1).stationToName+"\n";
        }

        BusImageAdapter showImage = new BusImageAdapter(holder.context,images);
        holder.tvList.setAdapter(showImage);

        holder.txtDuration.setText(result.minutes+" phút");
        double totalDistance = result.totalDistance/1000;
        totalDistance = Math.floor(totalDistance*100)/100;
        holder.txtDistance.setText(String.valueOf(totalDistance)+" km");
        holder.txtContent.setText(viewDetail);
        holder.txtFrom.setText(from);
        holder.txtTo.setText(to);
        /*holder.viewDetail.setVisibility(View.GONE);
        holder.btnShow.setVisibility(View.VISIBLE);
        holder.btnHide.setVisibility(View.GONE);*/
        /*holder.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.viewDetail.setVisibility(View.VISIBLE);
                holder.btnShow.setVisibility(View.GONE);
                holder.btnHide.setVisibility(View.VISIBLE);
            }
        });
        holder.btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.viewDetail.setVisibility(View.GONE);
                holder.btnShow.setVisibility(View.VISIBLE);
                holder.btnHide.setVisibility(View.GONE);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class BusItemViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TwoWayView tvList;
        TextView txtDuration;
        TextView txtDistance;
        TextView txtContent;
        TextView txtFrom;
        TextView txtTo;
        LinearLayout viewDetail;
        ImageButton btnShow;
        ImageButton btnHide;

        public BusItemViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);
            txtDuration = (TextView) itemView.findViewById(R.id.txtDurationTime);
            txtContent = (TextView) itemView.findViewById(R.id.txtContent);
            tvList = (TwoWayView) itemView.findViewById(R.id.lvItems);
            viewDetail = (LinearLayout) itemView.findViewById(R.id.viewDetail);
            btnShow = (ImageButton) itemView.findViewById(R.id.btnShowDetail);
            btnHide = (ImageButton) itemView.findViewById(R.id.btnHide);
            txtFrom = (TextView) itemView.findViewById(R.id.txtFrom);
            txtTo = (TextView) itemView.findViewById(R.id.txtTo);
        }

    }

}
