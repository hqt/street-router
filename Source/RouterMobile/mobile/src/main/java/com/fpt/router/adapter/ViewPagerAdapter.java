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
import com.fpt.router.activity.MainSecond;
import com.fpt.router.fragment.BusFragment1;
import com.fpt.router.fragment.MotorbikeFragmentFourPoint;
import com.fpt.router.fragment.MotorbikeFragmentCuaNam;
import com.fpt.router.fragment.MotorbikeFragmentTwoPoint;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private String tabTitles[] = {"43 phút","65 phút","Bản đồ"};
    private int[] imageResId = {R.drawable.bus,
            R.drawable.motorbike,
            R.drawable.map};
    private List<String> listLocation = MainSecond.listLocation;
    public ViewPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 1){
            if(listLocation.size() == 2){
                MotorbikeFragmentTwoPoint motorbikeFragmentTwoPoint = new MotorbikeFragmentTwoPoint();
                return motorbikeFragmentTwoPoint;
            }else if(listLocation.size() == 3) {
                MotorbikeFragmentCuaNam motorbikeFragmentThirdPoint = new MotorbikeFragmentCuaNam();
                return motorbikeFragmentThirdPoint;
            }else {
                MotorbikeFragmentCuaNam motorbikeFragmentFourPoint = new MotorbikeFragmentCuaNam();
                return motorbikeFragmentFourPoint;
            }
        } else if(position == 0){
            BusFragment1 busFragment1 = new BusFragment1();
            Fragment fragment = busFragment1.newInstance(position);
            return fragment;
        } else {

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
