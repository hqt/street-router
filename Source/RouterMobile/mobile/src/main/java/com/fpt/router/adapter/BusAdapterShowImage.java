package com.fpt.router.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpt.router.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 10/13/2015.
 */
public class BusAdapterShowImage extends ArrayAdapter<Integer> {
    Context mContext;
    int resource;
    List<Integer> images;

    public BusAdapterShowImage(Context context, int resource, List<Integer> images){
        super(context,resource);
        this.mContext = context;
        this.resource = resource;
        this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        image.setImageResource(images.get(position));
        return convertView;
    }
}
