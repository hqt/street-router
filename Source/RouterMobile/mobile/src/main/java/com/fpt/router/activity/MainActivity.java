package com.fpt.router.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.TabLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.adapter.DesignDemoPagerAdapter;
import com.fpt.router.fragment.MotorbikeFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private TextView optional;
    private TextView _depart_time;
    private int pHour;
    private int pMinute;
    static final int TIME_DIALOG_ID = 0;
    private TextView edit_1;
    private TextView edit_2;
    private DesignDemoPagerAdapter adapter;
    public static List<String> test = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        //Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        adapter = new DesignDemoPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        // custom bar


        // Tab view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }


        edit_1 = (TextView) findViewById(R.id.edit_1);
        edit_2 = (TextView) findViewById(R.id.edit_2);
        //edit text 1
        edit_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("number", 1);
                startActivityForResult(intent, 1);// Activity is started with requestCode 1
            }
        });
        edit_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("number", 2);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
            }
        });


        //optional
        optional = (TextView) findViewById(R.id.optional);
        optional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Optional cdd = new Optional(MainActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });

        //get time

        _depart_time = (TextView) findViewById(R.id.departtime);
        _depart_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(TIME_DIALOG_ID);
            }
        });


    }


    // Call Back method  to get the Message form other Activity


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        // set Fragmentclass Arguments
        if (requestCode == 1) {
            String message = data.getStringExtra("MESSAGE");
            edit_1.setText(message);
            test.add(0, message);
        }
        if (requestCode == 2) {
            String message = data.getStringExtra("MESSAGE");
            edit_2.setText(message);
            test.add(1, message);
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            adapter = new DesignDemoPagerAdapter(getSupportFragmentManager(), MainActivity.this);
            viewPager.setAdapter(adapter);
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


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, pHour, pMinute, false);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour = hourOfDay;
                    pMinute = minute;

                    /*updateDisplay();*/
                    displayToast();
                }
            };

    /**
     *
     */
    private void displayToast() {

        Toast.makeText(this, "Time choosen is " + pad(pHour) + ":" + pad(pMinute), Toast.LENGTH_SHORT).show();

    }

    /**
     * Add padding to numbers less than ten
     */
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

}
