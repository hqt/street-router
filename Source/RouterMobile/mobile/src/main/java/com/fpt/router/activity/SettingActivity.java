package com.fpt.router.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fpt.router.R;
import com.fpt.router.framework.PrefStore;
import com.fpt.router.library.config.AppConstants;

/**
 * Created by ngoan on 10/25/2015.
 */
public class SettingActivity extends AppCompatActivity {
    EditText portEditText;
    EditText ipEditText;
    Button okButton;
    Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        /**
         * get id of IP and Port editext
         */
        ipEditText = (EditText) findViewById(R.id.edit_ip);
        portEditText = (EditText) findViewById(R.id.edit_port);
        okButton = (Button) findViewById(R.id.btn_ok);
        cancelButton = (Button) findViewById(R.id.btn_cancel);

        ipEditText.setText(PrefStore.getServerIp());
        portEditText.setText(PrefStore.getServerPort() + "");

        /**
         * click listener
         */
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save to pref store
                PrefStore.setBusServerIp(ipEditText.getText().toString());
                int port;
                try {
                    port = Integer.parseInt(portEditText.getText().toString());
                } catch (Exception e) {
                    port = 8080;
                }
                PrefStore.setBusServerPort(port);

                // set again variable
             /*   AppConstants.SERVER_IP = PrefStore.getServerIp();
                AppConstants.SERVER_PORT = PrefStore.getServerPort();*/

                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
