package com.fpt.router.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fpt.router.R;
import com.fpt.router.library.model.motorbike.RouterDetailTwoPoint;

/**
 * Created by asus on 10/12/2015.
 */
public class TwoPointMotorbikeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        /*Leg leg = (Leg) getIntent().getSerializableExtra("Leg");
        Log.i("GoogleMapBus Leg",leg.toString());*/
        Bundle bundle  = getIntent().getExtras();
        RouterDetailTwoPoint routerDetailTwoPoint = (RouterDetailTwoPoint) getIntent().getSerializableExtra("routerDetailTwoPoint");

        if (savedInstanceState == null) {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.fragment, MainShowDetailMotorbikeTowPointMaps.newInstance(routerDetailTwoPoint));
            trans.commit();
        }
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
