package com.fpt.router.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
 * Created by ngoan on 11/2/2015.
 */
public class BusItemFourPoint extends ArrayAdapter<Result> {
    Context context;
    List<Result> results;


    public BusItemFourPoint(Context context,List<Result> results){
        super(context, R.layout.activity_item_four_bus, results);
        this.context = context;
        this.results = results;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_item_four_bus, parent, false);

        String detail = "";
        String from = "";
        String to = "";
        List<BusImage> images = new ArrayList<BusImage>();

        /**
         * get parameter
         */

        TextView txtDistance = (TextView) rowView.findViewById(R.id.txtDistance);
        TextView txtDuration = (TextView) rowView.findViewById(R.id.txtDurationTime);
        TextView txtContent = (TextView) rowView.findViewById(R.id.txtContent);
        TwoWayView tvList = (TwoWayView) rowView.findViewById(R.id.lvItems);
        final LinearLayout viewDetail = (LinearLayout) rowView.findViewById(R.id.viewDetail);
        final ImageButton btnShow = (ImageButton) rowView.findViewById(R.id.btnShowDetail);
        final ImageButton btnHide = (ImageButton) rowView.findViewById(R.id.btnHide);
        TextView txtFrom = (TextView) rowView.findViewById(R.id.txtFrom);
        TextView txtTo = (TextView) rowView.findViewById(R.id.txtTo);

        Result result = results.get(position);

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
        detail = "Tổng số tuyến : "+result.totalTransfer +"\n";
        for (int m=0;m<segments.size();m++){
            List<Path> paths = new ArrayList<Path>();
            paths = segments.get(m).paths;
            detail +="Tuyến số: "+segments.get(m).routeNo+"\n   Lên trạm : "+paths.get(0).stationFromName+"\n   Xuống trạm : "+paths.get(paths.size()-1).stationToName+"\n";
        }

        BusImageAdapter showImage = new BusImageAdapter(context,images);
        tvList.setAdapter(showImage);
        txtDuration.setText(result.minutes+" phút");
        double totalDistance = result.totalDistance/1000;
        totalDistance = Math.floor(totalDistance*100)/100;
        txtDistance.setText(String.valueOf(totalDistance) + " km");
        txtContent.setText(detail);
        txtFrom.setText(from);
        txtTo.setText(to);
        viewDetail.setVisibility(View.GONE);
        btnShow.setVisibility(View.VISIBLE);
        btnHide.setVisibility(View.GONE);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDetail.setVisibility(View.VISIBLE);
                btnShow.setVisibility(View.GONE);
                btnHide.setVisibility(View.VISIBLE);
            }
        });
        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDetail.setVisibility(View.GONE);
                btnShow.setVisibility(View.VISIBLE);
                btnHide.setVisibility(View.GONE);
            }
        });

        return rowView;
    }
}
