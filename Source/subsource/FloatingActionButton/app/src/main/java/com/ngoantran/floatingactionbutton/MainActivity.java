package com.ngoantran.floatingactionbutton;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);


        //Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Snackbar Action", Toast.LENGTH_LONG).show();
            }
        });


        //Tabs
        DesignDemoPagerAdapter adapter = new DesignDemoPagerAdapter(getSupportFragmentManager(),this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        // custom bar


        // Tab view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    //Fragment
    public static class DesignDemoFragment extends Fragment{
        private static final String TAB_POSITION = "tab_position";
        public DesignDemoFragment(){

        }
        public DesignDemoFragment newInstance(int tabPosition){

            DesignDemoFragment fragment = new DesignDemoFragment();
            Bundle args = new Bundle();
            args.putInt(TAB_POSITION,tabPosition);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            Bundle args = getArguments();
            int tabPosition = args.getInt(TAB_POSITION);
            TextView tv = new TextView(getActivity());
            tv.setGravity(Gravity.CENTER);
            tv.setText("Text in Tab #" + tabPosition);
            return tv;
        }
    }

    class DesignDemoPagerAdapter extends FragmentStatePagerAdapter{

        private Context context;
        private String tabTitles[] = {"43 phút","65 phút","Bản đồ"};
        private int[] imageResId = {R.drawable.bus,
                R.drawable.motorbike,
                R.drawable.map};
        public DesignDemoPagerAdapter(FragmentManager fm,Context context){
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            DesignDemoFragment demoFragment = new DesignDemoFragment();
            Fragment fragment = demoFragment.newInstance(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
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


}
