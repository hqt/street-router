package com.fpt.router.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.fpt.router.R;
import com.fpt.router.framework.PrefStore;
import com.fpt.router.library.config.AppConstants;

/**
 * Created by ngoan on 11/26/2015.
 */
public class DeveloperSettingActivity extends AppCompatActivity {

    EditText portEditText;
    EditText ipEditText;
    EditText stimulateSpeedText;

    Spinner stimulateRouteTypeSpinner;

    View componentLayout;

    Button okButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        ipEditText = (EditText) findViewById(R.id.edit_ip);
        portEditText = (EditText) findViewById(R.id.edit_port);
        stimulateSpeedText = (EditText) findViewById(R.id.edit_stimulate_speed);
        componentLayout = (View) findViewById(R.id.component_layout);

        stimulateRouteTypeSpinner = (Spinner) findViewById(R.id.stimulate_route_spinner);

        okButton = (Button) findViewById(R.id.btn_ok);
        cancelButton = (Button) findViewById(R.id.btn_cancel);

        //componentLayout.setEnabled(false);

        // set default value
        ipEditText.setText(PrefStore.getServerIp());
        portEditText.setText(PrefStore.getServerPort() + "");
        stimulateSpeedText.setText(PrefStore.getSimulationSpeed() + "");

        // set spinner
        stimulateRouteTypeSpinner.setSelection(PrefStore.getSimulateRouteType());

        // set cursor at end of line
        ipEditText.setSelection(ipEditText.getText().length());
        portEditText.setSelection(portEditText.getText().length());
        stimulateSpeedText.setSelection(stimulateSpeedText.getText().length());

        /**
         * click listener
         */
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int port, simulateSpeed;
                try {
                    port = Integer.parseInt(portEditText.getText().toString());
                    simulateSpeed = Integer.parseInt(stimulateSpeedText.getText().toString());
                } catch (Exception e) {
                    port = 8080;
                    simulateSpeed = 40;
                }

                // save to pref store
                PrefStore.setBusServerIp(ipEditText.getText().toString());
                PrefStore.setBusServerPort(port);
                PrefStore.setPrefSimulationSpeed(simulateSpeed);
                PrefStore.setStimulateRouteType(stimulateRouteTypeSpinner.getSelectedItemPosition());

                // set again variable
                AppConstants.SERVER_ADDRESS = "http://" + PrefStore.getServerIp() + ":" + PrefStore.getServerPort();
                AppConstants.buildAPILink();

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
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
