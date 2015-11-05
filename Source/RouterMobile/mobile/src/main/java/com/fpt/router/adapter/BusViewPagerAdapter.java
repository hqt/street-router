package com.fpt.router.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.fragment.DesignDemoFragment;
import com.fpt.router.library.model.bus.Journey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngoan on 11/3/2015.
 */
public class BusViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    List<Journey> journeys= new ArrayList<>();
    public static int tab_position ;

    public BusViewPagerAdapter(FragmentManager fm, Context context,List<Journey> journeys) {
        super(fm);
        this.context = context;
        this.journeys = journeys;
    }

    @Override
    public Fragment getItem(int position) {
        Journey journey = journeys.get(position);
        tab_position = position;
        DesignDemoFragment designDemoFragment = new DesignDemoFragment(journey);
        return  designDemoFragment;
    }

    @Override
    public int getCount() {
        return journeys.size();
    }


}
