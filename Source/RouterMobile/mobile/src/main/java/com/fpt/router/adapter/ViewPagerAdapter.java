package com.fpt.router.adapter;

/**
 * Created by USER on 9/27/2015.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.fragment.BusFragment1;
import com.fpt.router.fragment.MotorbikeFragment1;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private String tabTitles[] = {"43 phút","65 phút","Bản đồ"};
    private int[] imageResId = {R.drawable.bus,
            R.drawable.motorbike,
            R.drawable.map};
    public ViewPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 1){
           /* MotorbikeFragment motorbikeFragment = new MotorbikeFragment();*/
            MotorbikeFragment1 motorbikeFragment = new MotorbikeFragment1();
            return motorbikeFragment;
        } else {
            /*DesignDemoFragment demoFragment = new DesignDemoFragment();
            Fragment fragment = demoFragment.newInstance(position);*/
            BusFragment1 busFragment1 = new BusFragment1();
            Fragment fragment = busFragment1.newInstance(position);
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        TextView tv= (TextView) view.findViewById(R.id.tab_title);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
        img.setImageResource(imageResId[position]);
        return view;
    }
}
