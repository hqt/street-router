package com.fpt.router.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.library.model.bus.BusImage;

import java.util.List;

/**
 * Created by asus on 10/13/2015.
 */
public class BusImageAdapter extends ArrayAdapter<BusImage> {
    private final Context context;
    private final List<BusImage> values;

    public BusImageAdapter(Context context, List<BusImage> values){
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
        TextView textView = (TextView) rowView.findViewById(R.id.txtBusNumber);
        BusImage busImage = values.get(position);
        imageView.setImageResource(busImage.image);
        textView.setText(busImage.number);
        return rowView;
    }
}
