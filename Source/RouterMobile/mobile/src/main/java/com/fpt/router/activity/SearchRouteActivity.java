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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.adapter.ViewPagerAdapter;
import com.fpt.router.library.config.AppConstants.SearchField;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.common.AutocompleteObject;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchRouteActivity extends AppCompatActivity {

    public enum SearchType {
        BUS_TWO_POINT,
        BUS_FOUR_POINT,
        MOTOR_TWO_POINT,
        MOTOR_FOUR_POINT
    }

    private ImageButton optional;
    private ImageButton _depart_time;
    private ImageButton _btn_search;
    private ImageButton mbVoiceSearch;
    public static int pHour;
    public static int pMinute;
    static final int TIME_DIALOG_ID = 0;
    private TextView edit_1;
    private TextView edit_2;
    public static ViewPagerAdapter adapter;
    public static List<Result> results = new ArrayList<Result>();
    public static List<Journey> journeys = new ArrayList<Journey>();
    public static List<Leg> listLeg = new ArrayList<>();
    // public static List<AutocompleteObject> listLocation = new ArrayList<>();
    public static Map<Integer, AutocompleteObject> mapLocation = new HashMap<>();
    public static Boolean optimize = true;
    public static int walkingDistance = 300;
    public static int transferNumber = 2;
    private LinearLayout option;
    private ViewPager _view_pager;
    private ImageButton changeImageButton;

    LinearLayout top_view;
    LinearLayout below_view;
    int viewHeight;
    boolean noSwap = true;
    private static int ANIMATION_DURATION = 300;

    // variable for controlling which fragment should be to refreshed
    public boolean needToSearch = false;
    public SearchType searchType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_route);
        /**set Toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        /**get id in layout*/
        _depart_time = (ImageButton) findViewById(R.id.departtime);
        option = (LinearLayout) findViewById(R.id.option);
        _btn_search = (ImageButton) findViewById(R.id.btn_search);
        _view_pager = (ViewPager) findViewById(R.id.viewpager);
        edit_1 = (TextView) findViewById(R.id.edit_1);
        edit_2 = (TextView) findViewById(R.id.edit_2);
        changeImageButton = (ImageButton) findViewById(R.id.changeImageButton);
        top_view = (LinearLayout) findViewById(R.id.top_view);
        below_view = (LinearLayout) findViewById(R.id.below_view);
        mbVoiceSearch = (ImageButton) findViewById(R.id.btn_voice);


        if (mapLocation.get(SearchField.FROM_LOCATION) != null) {
            edit_1.setText(mapLocation.get(SearchField.FROM_LOCATION).getName());
        }

        if (mapLocation.get(SearchField.TO_LOCATION) != null) {
            edit_2.setText(mapLocation.get(SearchField.TO_LOCATION).getName());
        }

        //Tabs
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        _view_pager.setAdapter(adapter);
        // _view_pager.setCurrentItem(1);
        option.setVisibility(View.VISIBLE);
        /*_depart_time.setClickable(false);
        _depart_time.setFocusableInTouchMode(false);
        _depart_time.setEnabled(false);*/
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
                Intent intent = new Intent(SearchRouteActivity.this, AutoCompleteSearchActivity.class);
                if (noSwap) {
                    intent.putExtra("number", SearchField.FROM_LOCATION);
                } else {
                    intent.putExtra("number", SearchField.TO_LOCATION);
                }
                intent.putExtra("message", edit_1.getText());
                startActivityForResult(intent, 1);// Activity is started with requestCode 1
            }
        });
        edit_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchRouteActivity.this, AutoCompleteSearchActivity.class);
                if (noSwap) {
                    intent.putExtra("number", SearchField.TO_LOCATION);
                } else {
                    intent.putExtra("number", SearchField.FROM_LOCATION);
                }
                intent.putExtra("message", edit_2.getText());
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
            }
        });

        /**
         * click change value of two field
         */
        ViewTreeObserver viewTreeObserver = edit_1.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    edit_1.getViewTreeObserver().addOnGlobalLayoutListener(this);
                    viewHeight = edit_1.getHeight()+20;
                    edit_1.getLayoutParams();
                    edit_1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

            });
        }
        changeImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(noSwap){
                    changeImageButton.animate().rotation(180);
                    TranslateAnimation ta1 = new TranslateAnimation(0, 0, 0, viewHeight);
                    ta1.setDuration(ANIMATION_DURATION);
                    ta1.setFillAfter(true);
                    top_view.startAnimation(ta1);
                    top_view.bringToFront();
                    edit_1.setHint("Chọn điểm đến");


                    TranslateAnimation ta2 = new TranslateAnimation(0, 0, 0, -viewHeight);
                    ta2.setDuration(ANIMATION_DURATION);
                    ta2.setFillAfter(true);
                    below_view.startAnimation(ta2);
                    below_view.bringToFront();
                    edit_2.setHint("Chọn điểm khởi hành");
                    swapFromAndTo();
                    noSwap = false;
                }else{
                    changeImageButton.animate().rotation(-180);
                    TranslateAnimation ta1 = new TranslateAnimation(0, 0, viewHeight, 0);
                    ta1.setDuration(ANIMATION_DURATION);
                    ta1.setFillAfter(true);
                    top_view.startAnimation(ta1);
                    top_view.bringToFront();
                    edit_1.setHint("Chọn điểm khởi hành");

                    TranslateAnimation ta2 = new TranslateAnimation(0, 0, -viewHeight, 0);
                    ta2.setDuration(ANIMATION_DURATION);
                    ta2.setFillAfter(true);
                    below_view.startAnimation(ta2);
                    below_view.bringToFront();
                    edit_2.setHint("Chọn điểm đến");
                    swapFromAndTo();
                    noSwap = true;
                }

            }
        });


        //optional
        optional = (ImageButton) findViewById(R.id.optional);
        optional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchRouteActivity.this, SearchOptionActivity.class);
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
                // validation
                if ("".equals(edit_1.getText())) {
                    Toast.makeText(SearchRouteActivity.this, "Phải nhập điểm khởi hành", Toast.LENGTH_SHORT).show();
                } else if ("".equals(edit_2.getText())) {
                    Toast.makeText(SearchRouteActivity.this, "Phải nhập điểm đến", Toast.LENGTH_SHORT).show();
                } else if (!NetworkUtils.isNetworkConnected()) {
                    Toast.makeText(SearchRouteActivity.this, "Phải kết nối Internet", Toast.LENGTH_SHORT).show();
                }
                // try to search
                else {
                    needToSearch = true;

                    int tabPosition = _view_pager.getCurrentItem();

                    if (tabPosition == 0) {
                        if (mapLocation.size() == 2) {
                            Log.e("hqthao", "Search bus two point");
                            searchType = SearchType.BUS_TWO_POINT;
                        } else {
                            Log.e("hqthao", "Search bus four point");
                            searchType = SearchType.BUS_FOUR_POINT;
                        }
                    }

                    else if (tabPosition == 1) {
                        if (mapLocation.size() == 2) {
                            Log.e("hqthao", "Search motor two point");
                            searchType = SearchType.MOTOR_TWO_POINT;
                        } else {
                            Log.e("hqthao", "Search motor four point");
                            searchType = SearchType.MOTOR_FOUR_POINT;
                        }
                    }

                    for (Map.Entry<Integer, AutocompleteObject> entry : mapLocation.entrySet()) {
                        Log.i("hqthao", entry.getKey() + "--> " + entry.getValue().getName());
                    }

                    adapter = new ViewPagerAdapter(getSupportFragmentManager(), SearchRouteActivity.this);
                    _view_pager.setAdapter(adapter);
                    option.setVisibility(View.VISIBLE);
                    _view_pager.setCurrentItem(tabPosition);

                }
            }
        });

        /**
         * voice search
         */
        mbVoiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchRouteActivity.this,VoiceRecordActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    // Call Back method  to get the Message form other Activity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("hqthao", "called");
        super.onActivityResult(requestCode, resultCode, data);
        if ((data != null) && (requestCode == 3)) {
            optimize = data.getBooleanExtra("optimize", true);
        }
        setTextToField();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                _view_pager.setAdapter(null);
                mapLocation.clear();
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

    private void swapFromAndTo() {
        AutocompleteObject obj1 = mapLocation.get(SearchField.FROM_LOCATION);
        AutocompleteObject obj2 = mapLocation.get(SearchField.TO_LOCATION);

        if (obj2 != null) {
            mapLocation.put(SearchField.FROM_LOCATION, obj2);
        }

        if (obj1 != null) {
            mapLocation.put(SearchField.TO_LOCATION, obj1);
        }
    }

    private void setTextToField() {
        if (mapLocation.get(SearchField.FROM_LOCATION) != null) {
            edit_1.setText(mapLocation.get(SearchField.FROM_LOCATION).getName());
        }else {
            edit_1.setText("");
        }

        if (mapLocation.get(SearchField.TO_LOCATION) != null) {
            edit_2.setText(mapLocation.get(SearchField.TO_LOCATION).getName());
        }else {
            edit_2.setText("");
        }
    }

}