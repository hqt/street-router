package com.fpt.router.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.TabLayout;
import android.widget.EditText;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.adapter.DesignDemoPagerAdapter;
import com.fpt.router.fragment.MotorbikeFragment;


public class MainActivity extends AppCompatActivity {
    private EditText edit_1;
    private EditText edit_2;

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

        edit_1 = (EditText) findViewById(R.id.edit_1);
        edit_2 = (EditText) findViewById(R.id.edit_2);

        //edit text 1
        edit_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, 1);// Activity is started with requestCode 1
            }
        });
        edit_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
            }
        });



    }


    // Call Back method  to get the Message form other Activity


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2

        Bundle bundle = new Bundle();

        // set Fragmentclass Arguments
        MotorbikeFragment motoFra = new MotorbikeFragment();


        if(requestCode == 1)
        {
            String message = data.getStringExtra("MESSAGE");
            edit_1.setText(message);
            bundle.putString("from", message);
        }
        if(requestCode == 2){
            String message = data.getStringExtra("MESSAGE");
            edit_2.setText(message);
            bundle.putString("to", message);
        }
        motoFra.setArguments(bundle);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
