package com.fpt.router.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.activity.GoogleMapBus;
import com.fpt.router.model.motorbike.Leg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 10/10/2015.
 */
public class RecyclerAdapterShowError extends RecyclerView.Adapter<RecyclerAdapterShowError.ViewHolder> {
    private List<String> listError;

    public RecyclerAdapterShowError(List<String> listError) {
        this.listError = listError;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_error, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.txtError.setText(listError.get(position));
    }

    @Override
    public int getItemCount() {
        return listError.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtError;

        public ViewHolder(View v) {
            super(v);
            txtError = (TextView) v.findViewById(R.id.txtError);
        }
    }

}

