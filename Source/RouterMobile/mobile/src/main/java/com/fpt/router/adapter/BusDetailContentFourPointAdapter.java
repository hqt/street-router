package com.fpt.router.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.library.model.bus.BusDetail;

import java.util.List;

/**
 * Created by ngoan on 11/6/2015.
 */
public class BusDetailContentFourPointAdapter extends ArrayAdapter<BusDetail> {
    private final Context context;
    private final List<BusDetail> values;

    public BusDetailContentFourPointAdapter(Context context, List<BusDetail> values) {
        super(context, R.layout.adapter_bus_detail_content_four_point, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.adapter_bus_detail_content_four_point, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView txtContent = (TextView) rowView.findViewById(R.id.txtBusContent);
        TextView txtBusNumber = (TextView) rowView.findViewById(R.id.busNumber);
        BusDetail busDetail = values.get(position);
        imageView.setImageResource(busDetail.image);
        txtContent.setText(busDetail._content);
        txtBusNumber.setText(busDetail._numberBus);
        return rowView;
    }
}
