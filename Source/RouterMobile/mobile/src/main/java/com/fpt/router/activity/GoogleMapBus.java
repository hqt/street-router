package com.fpt.router.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.model.motorbike.Leg;

import java.util.List;

/**
 * Created by asus on 10/7/2015.
 */
public class GoogleMapBus extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        /*Leg leg = (Leg) getIntent().getSerializableExtra("Leg");
        Log.i("GoogleMapBus Leg",leg.toString());*/
        Bundle bundle  = getIntent().getExtras();
        List<Leg> lstObj = (List<Leg>) bundle.getSerializable("lstLeg");
        int position = bundle.getInt("position");
        Leg leg = (Leg) getIntent().getSerializableExtra("Leg");
        int locationSize = bundle.getInt("locationSize");
        /*if (savedInstanceState == null) {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.fragment, MainFragment.newInstance(null));
            trans.commit();
        }*/
        /*if (savedInstanceState == null) {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.fragment, MainFragment.newInstance(leg));
            trans.commit();
        }*/
        if (savedInstanceState == null) {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.fragment, MainFragment.newInstance(lstObj, position, leg, locationSize));
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
