package com.fpt.router.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainSecond extends AppCompatActivity {

    private TextView optional;
    private TextView _depart_time;
    private Button _btn_search;
    private int pHour;
    private int pMinute;
    static final int TIME_DIALOG_ID = 0;
    private TextView edit_1;
    private TextView edit_2;
    public static ViewPagerAdapter adapter;
    public static List<String> listLocation = new ArrayList<>();
    private List<String> location;
    public static Boolean optimize = true;
    private LinearLayout option;
    private ViewPager _view_pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_second);
        /**set Toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        /**get id in layout*/
        _depart_time = (TextView) findViewById(R.id.departtime);
        option = (LinearLayout) findViewById(R.id.option);
        _btn_search = (Button) findViewById(R.id.btn_search);
        _view_pager = (ViewPager) findViewById(R.id.viewpager);
        edit_1 = (TextView) findViewById(R.id.edit_1);
        edit_2 = (TextView) findViewById(R.id.edit_2);

        if (listLocation.size() > 0) {
            edit_1.setText(listLocation.get(0));
        }
        if (listLocation.size() > 1) {
            edit_2.setText(listLocation.get(1));
        }
        //Tabs
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        _view_pager.setAdapter(adapter);
        _view_pager.setCurrentItem(1);
        option.setVisibility(View.VISIBLE);
        _depart_time.setClickable(false);
        _depart_time.setFocusableInTouchMode(false);
        _depart_time.setEnabled(false);
        tabLayout.setupWithViewPager(_view_pager);
        // custom bar

        // Tab view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }


        //edit text 1
        edit_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainSecond.this, MainSearch.class);
                intent.putExtra("number", 1);
                intent.putExtra("message", edit_1.getText());
                startActivityForResult(intent, 1);// Activity is started with requestCode 1
            }
        });
        edit_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainSecond.this, MainSearch.class);
                intent.putExtra("number", 2);
                intent.putExtra("message", edit_2.getText());
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
            }
        });


        //optional
        optional = (TextView) findViewById(R.id.optional);
        optional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainSecond.this, MainOptional.class);
                int positionTab = tabLayout.getSelectedTabPosition();
                intent.putExtra("postionTab", positionTab);
                startActivityForResult(intent, 3);
                adapter = null;
            }
        });

        //get time


        _depart_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        /** search when click button */
        _btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check from and to not null
                if ("".equals(edit_1.getText())) {
                    Toast.makeText(MainSecond.this, "Phải nhập điểm khởi hành", Toast.LENGTH_SHORT).show();
                } else if ("".equals(edit_2.getText())) {
                    Toast.makeText(MainSecond.this, "Phải nhập điểm đến", Toast.LENGTH_SHORT).show();
                } else {
                    adapter = new ViewPagerAdapter(getSupportFragmentManager(), MainSecond.this);
                    _view_pager.setAdapter(adapter);
                    _view_pager.setCurrentItem(1);
                    option.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // Call Back method  to get the Message form other Activity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        // set Fragmentclass Arguments
        if (data != null) {
            if (requestCode == 1) {
                String message = data.getStringExtra("MESSAGE");
                if (!"".equals(message)) {
                    edit_1.setText(message);
                    if (listLocation.size() > 0) {
                        listLocation.set(0, message);

                    } else {
                        listLocation.add(message);
                    }
                }

            }
            if (requestCode == 2) {
                String message = data.getStringExtra("MESSAGE");
                if (!"".equals(message)) {
                    edit_2.setText(message);
                    if (listLocation.size() > 1) {
                        listLocation.set(1, message);
                    } else {
                        listLocation.add(message);
                    }
                }
            }
            if (requestCode == 3) {
                optimize = data.getBooleanExtra("optimize", true);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                _view_pager.setAdapter(null);
                listLocation.clear();
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
