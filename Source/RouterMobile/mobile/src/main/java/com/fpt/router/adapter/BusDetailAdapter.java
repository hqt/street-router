package com.fpt.router.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Segment;

import java.util.List;

/**
 * Created by asus on 10/13/2015.
 */
public class BusDetailAdapter extends ArrayAdapter<INode> {

    private Context mContext;
    private int resource;
    private List<INode> nodeList;


    public BusDetailAdapter(Context context, int resource, List<INode> nodeList){
        super(context,resource,nodeList);
        this.mContext = context;
        this.resource = resource;
        this.nodeList = nodeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        }

        /*get ID*/
        ImageView imgRoute = (ImageView) convertView.findViewById(R.id.imgRoute);
        ImageView imgDrive = (ImageView) convertView.findViewById(R.id.imgDrive);
        TextView txtStartLocation = (TextView) convertView.findViewById(R.id.txtStartLocation);
        TextView txtEndLocation = (TextView) convertView.findViewById(R.id.txtEndLocation);
        TextView txtRouteNo = (TextView) convertView.findViewById(R.id.txtRouteNo);
        TextView txtRouteName = (TextView) convertView.findViewById(R.id.txtRouteName);
        TextView txtDetail = (TextView) convertView.findViewById(R.id.txtDetail);

        /*if walking */
        if(nodeList.get(position) instanceof Path){
            Path path = (Path) nodeList.get(position);
            imgRoute.setImageResource(R.drawable.leg_dot);
            imgDrive.setImageResource(R.drawable.ic_directions_walk_black_24dp);
            txtStartLocation.setText(path.stationFromName);
            if(position == (nodeList.size() - 1)){
                txtEndLocation.setText(path.stationToName);
            }
            double distance = path.distance / 1000;
            distance = Math.floor(distance * 100)/100;
            txtDetail.setText("Đi bộ "+ distance +" km ( Khoảng "+path.time+")");
        }
        /*if bus*/
        if(nodeList.get(position) instanceof Segment){
            Segment segment = (Segment) nodeList.get(position);
            List<Path> paths = segment.paths;
            imgRoute.setImageResource(R.drawable.line_blue);
            imgDrive.setImageResource(R.drawable.ic_directions_bus_black_24dp);
            txtStartLocation.setText(paths.get(0).stationFromName);
            txtRouteNo.setText(segment.routeNo);
            txtRouteName.setText(segment.routeName);
            txtDetail.setText("Đi "+ paths.size()+" điểm dừng ("+segment.segmentTime+")");
        }

        return convertView;
    }
}
