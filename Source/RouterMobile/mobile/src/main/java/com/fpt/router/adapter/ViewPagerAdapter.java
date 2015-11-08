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
import com.fpt.router.activity.SearchRouteActivity;
import com.fpt.router.fragment.BusFourPointFragment;
import com.fpt.router.fragment.BusFourPointFragmentDemo;
import com.fpt.router.fragment.BusTwoPointFragment;
import com.fpt.router.fragment.MotorFourPointFragment;
import com.fpt.router.fragment.MotorTwoPointFragment;
import com.fpt.router.library.model.common.AutocompleteObject;

import java.util.Map;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private String tabTitles[] = {"Xe bus","Xe máy","Bản đồ"};
    private int[] imageResId = {R.drawable.bus,
            R.drawable.motorbike,
            R.drawable.map};
    private Map<Integer, AutocompleteObject> mapLocation = SearchRouteActivity.mapLocation;
    public ViewPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            if(mapLocation.size() == 2){
                BusTwoPointFragment busFragmentTwoPoint = new BusTwoPointFragment();
                return busFragmentTwoPoint;
            }else if(mapLocation.size() == 3){
                BusFourPointFragment busFragmentThirdPoint = new BusFourPointFragment();
                return busFragmentThirdPoint;

            }else{
                BusFourPointFragment busFragmentFourPoint = new BusFourPointFragment();
                return busFragmentFourPoint;
            }

        }else if(position == 1){
            if(mapLocation.size() == 2){
                MotorTwoPointFragment motorTwoPointFragment = new MotorTwoPointFragment();
                return motorTwoPointFragment;
            }else if(mapLocation.size() == 3) {
                MotorFourPointFragment motorbikeFragmentThirdPoint = new MotorFourPointFragment();
                return motorbikeFragmentThirdPoint;
            }else {
                MotorFourPointFragment motorbikeFragmentFourPoint = new MotorFourPointFragment();
                return motorbikeFragmentFourPoint;
            }
        }else{
            BusTwoPointFragment busTwoPointFragment = new BusTwoPointFragment();
            return busTwoPointFragment;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.viewpager_tab_layout, null);
        TextView tv= (TextView) view.findViewById(R.id.tab_title);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
        img.setImageResource(imageResId[position]);
        return view;
    }
}
