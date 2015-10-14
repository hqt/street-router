package com.fpt.router.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.library.model.motorbike.Step;

import java.util.List;

/**
 * Created by asus on 10/5/2015.
 */
public class RouteItemAdapter extends ArrayAdapter<Step> {

    private Context mContext;
    private int resource;
    private List<Step> objects;



    public RouteItemAdapter(Context context, int resource, List<Step> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        // object item based on the position
        Step step = objects.get(position);
        TextView mTitle, mSubTitle;
        ImageView mImage;
        mTitle = (TextView) convertView.findViewById(R.id.mTitle);

        mSubTitle = (TextView) convertView.findViewById(R.id.mSubTitle);
        mImage = (ImageView) convertView.findViewById(R.id.mImage);


        mTitle.setText(step.getManeuver());
        mSubTitle.setText(step.getInstruction());
        if(step.getManeuver().equals("Đi thẳng")){
            mImage.setImageResource(R.drawable.up);
        }else if(step.getManeuver().equals("Rẽ trái")){
            mImage.setImageResource(R.drawable.up_left);
        }else if(step.getManeuver().equals("Rẽ phải")){
            mImage.setImageResource(R.drawable.up_right);
        }else if(step.getManeuver().equals("Rẽ trái một tí")){
            mImage.setImageResource(R.drawable.left_up);
        }else if(step.getManeuver().equals("Rẽ phải một tí")){
            mImage.setImageResource(R.drawable.right_up);
        }else if(step.getManeuver().equals("Vòng xoay bên phải")){
            mImage.setImageResource(R.drawable.redo);
        }else if(step.getManeuver().equals("Vòng xoay bên trái")){
            mImage.setImageResource(R.drawable.undo);
        }else if(step.getManeuver().equals("Giao nhau")){
            mImage.setImageResource(R.drawable.rotate);
        }else{
            mImage.setImageResource(R.drawable.down);
        }

       return convertView;

    }
}
