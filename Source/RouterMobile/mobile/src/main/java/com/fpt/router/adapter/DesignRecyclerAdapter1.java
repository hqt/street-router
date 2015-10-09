package com.fpt.router.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.activity.GoogleMapBus;
import com.fpt.router.model.bus.DetailRoute;
import com.fpt.router.model.motorbike.Leg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 10/8/2015.
 */
public class DesignRecyclerAdapter1 extends RecyclerView.Adapter<DesignRecyclerAdapter1.ViewHolder> {
    private List<Leg> objects;
    private Leg leg;
    private int locationSize;

    public DesignRecyclerAdapter1(List<Leg> objects, int locationSize) {
        this.objects = objects;
        this.locationSize = locationSize;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        leg = objects.get(position);
        if(position == 0){
            viewHolder.holder.tvTitle.setText("Tuyến đường được đề nghị ");
        }else{
            viewHolder.holder.tvTitle.setText("Thêm kết quả cho đi motorbike ");
        }
        viewHolder.holder.tvContent.setText(leg.getStartAddress());
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class Holder {
        TextView tvTitle;
        TextView tvContent;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final Context context;
        private final Holder holder = new Holder();

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            context = v.getContext();

            holder.tvTitle = (TextView) v.findViewById(R.id.txtitemtitle);
            holder.tvContent = (TextView) v.findViewById(R.id.txtitemcontent);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, GoogleMapBus.class);

            /** add object to bundle */
           /* Bundle bundle = new Bundle();
            leg = getLeg(getPosition());
            bundle.putSerializable("Leg",leg);
            intent.putExtras(bundle);*/

            /** add List Object  */
            Bundle bundle = new Bundle();
            leg = getLeg(getPosition());
            bundle.putSerializable("lstLeg", (Serializable) objects);
            bundle.putInt("position", getPosition());
            bundle.putSerializable("Leg", leg);
            bundle.putInt("locationSize",locationSize);

            intent.putExtras(bundle);
            view.getContext().startActivity(intent);

        }

    }

    public Leg getLeg(int position) {
        return objects.get(position);
    }

}

