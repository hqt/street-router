package com.fpt.router.adapter.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpt.router.R;

import java.util.List;

/**
 * Created by ngoan on 10/15/2015.
 */
public class TestAdapter extends ArrayAdapter<Integer> {
    private final Context context;
    private final List<Integer> values;

    public TestAdapter(Context context, List<Integer> values){
        super(context, R.layout.simple_list_item_1, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.simple_list_item_1, parent, false);
       /* TextView textView = (TextView) rowView.findViewById(R.id.text1);

        textView.setText(values.get(position));*/
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        imageView.setImageResource(values.get(position));


        return rowView;
    }
}
