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
public class BusImageAdapter extends ArrayAdapter<Integer> {
    private final Context context;
    private final List<Integer> values;

    public BusImageAdapter(Context context, List<Integer> values){
        super(context, R.layout.simple_list_item_1, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.simple_list_item_1, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        imageView.setImageResource(values.get(position));
        return rowView;
    }
}
