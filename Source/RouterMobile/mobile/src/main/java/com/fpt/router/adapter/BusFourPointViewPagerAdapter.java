package com.fpt.router.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fpt.router.fragment.BusFourPointDesignFragment;
import com.fpt.router.library.model.bus.Journey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngoan on 11/3/2015.
 */
public class BusFourPointViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    List<Journey> journeys= new ArrayList<>();
    public static int tab_position ;

    public BusFourPointViewPagerAdapter(FragmentManager fm, Context context, List<Journey> journeys) {
        super(fm);
        this.context = context;
        this.journeys = journeys;
    }

    @Override
    public Fragment getItem(int position) {
        Journey journey = journeys.get(position);
        tab_position = position;
        BusFourPointDesignFragment busFourPointDesignFragment = new BusFourPointDesignFragment(journey);
        return busFourPointDesignFragment;
    }

    @Override
    public int getCount() {
        return journeys.size();
    }


}
