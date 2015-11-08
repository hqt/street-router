package com.fpt.router.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.library.model.motorbike.Step;

import java.util.Arrays;
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
        TextView mTitle, mSubTitle, mDistance;
        ImageView mImage,mImageWarning;
        LinearLayout linearTitle;

        mTitle = (TextView) convertView.findViewById(R.id.mTitle);
        mSubTitle = (TextView) convertView.findViewById(R.id.mSubTitle);
        mDistance = (TextView) convertView.findViewById(R.id.txtDistance);
        mImage = (ImageView) convertView.findViewById(R.id.mImage);
        mImageWarning = (ImageView) convertView.findViewById(R.id.imageWarning);
        linearTitle = (LinearLayout) convertView.findViewById(R.id.linearSubTitle);

        /**
         * slice html_instructions
         */
        /* delimiter */
        String delimiter = "<div style=\"font-size:0.9em\">";
        String[] temp;
        String str = step.getInstruction();
        temp = str.split(delimiter);
        Log.i("Ngoan delimiter ---->", Arrays.toString(temp));
        String subTitle = "";
        if(temp.length >1){
            for (int i= 1; i<temp.length;i++){
                subTitle += temp[i]+"\n";
                Log.i("Ngoan ----------->",subTitle);
                if(temp[i].equals("Đường bị giới hạn sử dụng")){
                    mImageWarning.setImageResource(R.drawable.warning_yellow);
                }
                if((!temp[i].equals("")) && (!temp[i].equals("Đường bị giới hạn sử dụng")) && (!temp[i].equals(null))){
                    mImageWarning.setImageResource(R.drawable.information_blue);
                }
            }
        }else{
            mImageWarning.setImageResource(R.drawable.mwhile);
            linearTitle.setVisibility(View.GONE);
        }

        mTitle.setText(temp[0]);
        mSubTitle.setText(subTitle);
        mDistance.setText(step.getDetailLocation().getDistanceText());

        if(step.getManeuver().equals("turn-sharp-left")){
            mImage.setImageResource(R.drawable.turn_sharp_left);
        }
        if(step.getManeuver().equals("uturn-right")){
            mImage.setImageResource(R.drawable.uturn_right);
        }
        if(step.getManeuver().equals("turn-slight-right")){
            mImage.setImageResource(R.drawable.turn_slight_right);
        }
        if(step.getManeuver().equals("merge")) {
            mImage.setImageResource(R.drawable.merge);
        }
        if(step.getManeuver().equals("roundabout-left")){
            mImage.setImageResource(R.drawable.roundabout_left);
        }
        if(step.getManeuver().equals("roundabout-right")) {
            mImage.setImageResource(R.drawable.roundabout_right);
        }
        if(step.getManeuver().equals("uturn-left")) {
            mImage.setImageResource(R.drawable.uturn_left);
        }
        if(step.getManeuver().equals("turn-slight-left")){
            mImage.setImageResource(R.drawable.turn_slight_left);
        }
        if(step.getManeuver().equals("turn-left")){
            mImage.setImageResource(R.drawable.turn_left);
        }
        if(step.getManeuver().equals("ramp-right")){
            mImage.setImageResource(R.drawable.ramp_right);
        }
        if(step.getManeuver().equals("turn-right")){
            mImage.setImageResource(R.drawable.turn_right);
        }
        if(step.getManeuver().equals("fork-right")){
            mImage.setImageResource(R.drawable.fork_right);
        }
        if(step.getManeuver().equals("straight")) {
            mImage.setImageResource(R.drawable.straight);
        }
        if(step.getManeuver().equals("fork-left")){
            mImage.setImageResource(R.drawable.fork_left);
        }
        if(step.getManeuver().equals("ferry-train")) {
            mImage.setImageResource(R.drawable.ferry_train);
        }
        if(step.getManeuver().equals("turn-sharp-right")) {
            mImage.setImageResource(R.drawable.turn_sharp_right);
        }
        if(step.getManeuver().equals("ramp-left")){
            mImage.setImageResource(R.drawable.ramp_left);
        }
        if(step.getManeuver().equals("ferry")){
            mImage.setImageResource(R.drawable.ferry);
        }
        if(step.getManeuver().equals("keep-left")) {
            mImage.setImageResource(R.drawable.keep_left);
        }
        if(step.getManeuver().equals("keep-right")) {
            mImage.setImageResource(R.drawable.keep_right);
        }
        if(step.getManeuver().equals("Keep going")){
            mImage.setImageResource(R.drawable.straight);
        }
       return convertView;

    }


}
