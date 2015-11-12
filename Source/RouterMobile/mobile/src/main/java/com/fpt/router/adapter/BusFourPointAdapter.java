package com.fpt.router.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.activity.DetailBusFourPointActivity;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.bus.Segment;
import com.fpt.router.library.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ngoan on 11/5/2015.
 */
public class BusFourPointAdapter extends RecyclerView.Adapter<BusFourPointAdapter.BusViewHoder> {

    List<Journey> journeys;
    Journey journey;
    List<Result> results;
    public BusFourPointAdapter(List<Journey> journeys){
        this.journeys = journeys;
    }
    @Override
    public BusViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bus_four_point,parent,false);
        BusViewHoder busViewHoder = new BusViewHoder(v);
        return busViewHoder;
    }

    @Override
    public void onBindViewHolder(BusViewHoder holder, int position) {

        if (position % 2 != 0) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#DFEEF7"));
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        journey = journeys.get(position);
        results = journey.results;
        List<String> numberBusFinal = new ArrayList<>();
        List<String> numberBusMiddle_1 = new ArrayList<>();
        List<String> numberBusMiddle_2 = new ArrayList<>();
        List<String> numberBusEnd = new ArrayList<>();
        // Result 1
        Result result_1 = results.get(0);
        List<INode> iNodeList_1 = result_1.nodeList;
        for (int j=0;j<iNodeList_1.size();j++){
            if(iNodeList_1.get(j) instanceof Segment){
                Segment segment = (Segment) iNodeList_1.get(j);
                numberBusMiddle_1.add(String.valueOf(segment.routeNo));
                numberBusFinal.add(String.valueOf(segment.routeNo));
            }
        }
        if(iNodeList_1.get(0) instanceof Path){
            Path path = (Path) iNodeList_1.get(0);
            holder.startLocation.setText("Đi từ "+ StringUtils.removeCharacter(path.stationFromName));
        }
        //Result 2
        Result result_2 = results.get(1);
        List<INode> iNodeList_2 = result_2.nodeList;
        for (int j=0;j<iNodeList_2.size();j++){
            if(iNodeList_2.get(j) instanceof Segment){
                Segment segment = (Segment) iNodeList_2.get(j);
                numberBusMiddle_2.add(String.valueOf(segment.routeNo));
                numberBusFinal.add(String.valueOf(segment.routeNo));
            }
        }
        if(iNodeList_2.get(0) instanceof Path){
            Path path = (Path) iNodeList_2.get(0);
            holder.way_point_1.setText("Qua "+StringUtils.removeCharacter(path.stationFromName));
        }
        //Result 3
        Result result_3 = results.get(2);
        List<INode> iNodeList_3 = result_3.nodeList;
        for (int j=0;j<iNodeList_3.size();j++){
            if(iNodeList_3.get(j) instanceof Segment){
                Segment segment = (Segment) iNodeList_3.get(j);
                numberBusEnd.add(String.valueOf(segment.routeNo));
                numberBusFinal.add(String.valueOf(segment.routeNo));
            }
        }
        if(iNodeList_3.get(0) instanceof Path){
            Path path = (Path) iNodeList_3.get(0);
            holder.way_point_2.setText("Qua "+StringUtils.removeCharacter(path.stationFromName));
        }
        if(iNodeList_3.get(iNodeList_3.size() - 1) instanceof Path){
            Path path = (Path) iNodeList_3.get(iNodeList_3.size() - 1);
            holder.endLocation.setText("Đến "+StringUtils.removeCharacter(path.stationToName));
        }

        if(position == 0){
            holder.txtTitle.setText("Tuyến đường được đề nghị ");
        }else{
            holder.txtTitle.setText("Thêm kết quả cho đi xe bus ");
        }

        holder.txtDuration.setText(journey.minutes + " phút");
        String[]  nBusFinal = numberBusFinal.toArray(new String[numberBusFinal.size()]);
        holder.txtTotalBusNumber.setText(Arrays.toString(nBusFinal));
        String[] busNumberWayPoint_1 = numberBusMiddle_1.toArray(new String[numberBusMiddle_1.size()]);
        holder.txtBusNumberWayPoint1.setText("Đi "+Arrays.toString(busNumberWayPoint_1));
        String[] busNumberWayPoint_2 = numberBusMiddle_2.toArray(new String[numberBusMiddle_2.size()]);
        holder.txtBusNumberWayPoint2.setText("Đi "+Arrays.toString(busNumberWayPoint_2));
        String[] busNumberEnd = numberBusEnd.toArray(new String[numberBusEnd.size()]);
        holder.txtBusNumberEnd.setText("Đi "+Arrays.toString(busNumberEnd));

    }

    @Override
    public int getItemCount() {
        return journeys.size();
    }

    public class BusViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final Context context;
        TextView txtDuration;
        TextView txtTotalBusNumber;
        TextView startLocation;
        TextView way_point_1;
        TextView txtBusNumberWayPoint1;
        TextView way_point_2;
        TextView txtBusNumberWayPoint2;
        TextView endLocation;
        TextView txtBusNumberEnd;
        TextView txtTitle;
        CardView cardView;

        public BusViewHoder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();

            txtDuration = (TextView) itemView.findViewById(R.id.txtDurationTime);
            txtTotalBusNumber = (TextView) itemView.findViewById(R.id.txtTotalBusNumber);
            startLocation = (TextView) itemView.findViewById(R.id.startLocation);
            way_point_1 = (TextView) itemView.findViewById(R.id.way_point_1);
            txtBusNumberWayPoint1 = (TextView) itemView.findViewById(R.id.txtBusNumberWayPoint1);
            way_point_2 = (TextView) itemView.findViewById(R.id.way_point_2);
            txtBusNumberWayPoint2 = (TextView) itemView.findViewById(R.id.txtBusNumberWayPoint2);
            endLocation = (TextView) itemView.findViewById(R.id.endLocation);
            txtBusNumberEnd = (TextView) itemView.findViewById(R.id.txtBusNumberEnd);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }


        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DetailBusFourPointActivity.class);
            intent.putExtra("postion",getPosition());
            /*Bundle bundle = new Bundle();
            Journey journey = getJourney(getPosition());
            bundle.putSerializable("journey",journey);
            intent.putExtras(bundle);*/
            view.getContext().startActivity(intent);
        }
    }
    public Journey getJourney(int position){
        return journeys.get(position);
    }
}
