package com.fpt.router.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fpt.router.R;
import com.fpt.router.adapter.BusFourPointViewPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by ngoan on 11/6/2015.
 */
public class DetailBusFourPointActivity extends AppCompatActivity {

    private ViewPager pager;
    BusFourPointViewPagerAdapter adapter;
    CirclePageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bus_four_point);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);*/

        int position = getIntent().getIntExtra("postion",0);

        pager = (ViewPager) findViewById(R.id.pager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        adapter = new BusFourPointViewPagerAdapter(getSupportFragmentManager(), this, SearchRouteActivity.journeys);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        indicator.setPageColor(Color.parseColor("#F44336"));
        pager.setCurrentItem(position);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
