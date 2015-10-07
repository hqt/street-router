package com.fpt.router.model.bus;

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

import java.util.List;

/**
 * Created by asus on 10/5/2015.
 */
public class ArrayAdapterItem extends ArrayAdapter<DetailRoute> {

    Context mContext;
    int resource;
    List<DetailRoute> objects;


    public ArrayAdapterItem(Context context, int resource, List<DetailRoute> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(resource,parent,false);
        }

        // object item based on the position
        DetailRoute route = objects.get(position);
        TextView mTitle,mSubTitle;
        ImageView mImage;
        mTitle = (TextView) convertView.findViewById(R.id.mTitle);
        mTitle.setText(route.getTitle());
        mSubTitle = (TextView) convertView.findViewById(R.id.mSubTitle);
        mImage = (ImageView) convertView.findViewById(R.id.mImage);


        mSubTitle.setText(route.getSubTitle());
        mImage.setImageResource(route.getImage());

        return convertView;

    }
}
