package com.fpt.router.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.fpt.router.R;

public class  CarouselAdapter  extends BaseAdapter {

    private ArrayList<Integer> mData = new ArrayList<Integer>(0);
    private Context mContext;

    public CarouselAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<Integer> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int pos) {
        return mData.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView mImage= new ImageView(mContext);
        Log.v("UITEST", "position: " + position);
        mImage.setImageResource(mData.get(position));
        mImage.setScaleType(ScaleType.CENTER_CROP);
        mImage.setPadding(5, 5, 5, 5);
        mImage.setBackgroundResource(R.drawable.slider_bg);
        return mImage;
    }

}
