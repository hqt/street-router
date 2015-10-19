package com.fpt.router.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.fpt.router.R;

import java.util.List;

/**
 * Created by asus on 10/13/2015.
 */
public class BusImageAdapter extends ArrayAdapter<Integer> {
    private final Context context;
    private final List<Integer> values;

    public BusImageAdapter(Context context, List<Integer> values){
        super(context, R.layout.bus_item_image, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.bus_item_image, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        imageView.setImageResource(values.get(position));
        return rowView;
    }
}
