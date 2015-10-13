package com.fpt.router.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.model.bus.BusDetailTwoPoint;

import java.util.List;

/**
 * Created by asus on 10/13/2015.
 */
public class BusAdapterTwoPoint extends RecyclerView.Adapter<BusAdapterTwoPoint.BusViewHoder> {

    List<BusDetailTwoPoint> busDetailTwoPoints;

    public BusAdapterTwoPoint(List<BusDetailTwoPoint> busDetailTwoPoints){
        this.busDetailTwoPoints = busDetailTwoPoints;
    }

    @Override
    public BusViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_list_bus,parent,false);
        BusViewHoder busViewHoder = new BusViewHoder(v);
        return busViewHoder;
    }

    @Override
    public void onBindViewHolder(BusViewHoder holder, int position) {

        if(position == 0){
            holder.txtTitle.setText("Tuyến đường được đề nghị ");
        }else{
            holder.txtTitle.setText("Thêm kết quả cho đi motorbike ");
        }

    }

    @Override
    public int getItemCount() {
        return busDetailTwoPoints.size();
    }


    public class BusViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final Context context;
        TextView showImage;
        TextView txtTime;
        TextView txtContent;
        TextView txtTitle;

        public BusViewHoder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();

            showImage = (TextView) itemView.findViewById(R.id.txtShowImage);
            txtTime = (TextView) itemView.findViewById(R.id.txtDurationTime);
            txtContent = (TextView) itemView.findViewById(R.id.txtContent);


        }

        @Override
        public void onClick(View view) {

        }
    }
}
