package com.fpt.router.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.activity.DetailGMActivity;
import com.fpt.router.activity.GoogleMapBus;

import java.util.List;

/**
 * Created by asus on 10/5/2015.
 */
public class DesignDemoRecyclerAdapter extends RecyclerView.Adapter<DesignDemoRecyclerAdapter.ViewHolder> {
    String [] resultTitle;
    String [] resultContent;
    int [] imageId;

    public DesignDemoRecyclerAdapter(String[] titleNameList,String[] conNameList, int[] prgmImages) {
        resultTitle=titleNameList;
        resultContent = conNameList;
        imageId=prgmImages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.holder.tvTitle.setText(resultTitle[position]);
        viewHolder.holder.tvContent.setText(resultContent[position]);
        viewHolder.holder.img.setImageResource(imageId[position]);
    }

    @Override
    public int getItemCount() {
        return resultTitle.length;
    }

    public class Holder
    {
        TextView tvTitle;
        TextView tvContent;
        ImageView img;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final Context context;
        private final Holder holder=new Holder();
        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            context =  v.getContext();

            holder.tvTitle=(TextView) v.findViewById(R.id.txtitemtitle);
            holder.tvContent = (TextView) v.findViewById(R.id.txtitemcontent);
        }

        @Override
        public void onClick(View view) {
            Intent  intent =  new Intent(context, GoogleMapBus.class);
            view.getContext().startActivity(intent);
        }

    }
}

