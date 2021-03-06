package com.fpt.router.activity;

import android.app.Notification;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.framework.PrefStore;
import com.fpt.router.framework.RouterApplication;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.config.AppConstants.NUTITEQ;
import com.fpt.router.library.model.common.NotifyModel;
import com.fpt.router.library.model.message.MapPackageMessage;
import com.fpt.router.service.GPSServiceOld;
import com.nutiteq.datasources.PackageManagerTileDataSource;
import com.nutiteq.packagemanager.NutiteqPackageManager;
import com.nutiteq.packagemanager.PackageManager;

import de.greenrobot.event.EventBus;

import static com.fpt.router.library.config.AppConstants.Vibrator.DELAY_VIBRATE;
import static com.fpt.router.library.config.AppConstants.Vibrator.ON_VIBRATE;

/**
 * Created by ngoan on 10/25/2015.
 */
public class SettingActivity extends AppCompatActivity {

    private EventBus bus = EventBus.getDefault();

    EditText busNotifyDistanceText;
    EditText motorNotifyDistanceText;

    Spinner busSortTypeSpinner;
    Spinner motorSortTypeSpinner;

    Button okButton;
    Button cancelButton;

    ProgressBar progressBar;
    CheckBox downloadCheckBox;

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

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        downloadCheckBox = (CheckBox) findViewById(R.id.checkBox);

        /**
         * get id of IP and Port editext
         */
        busNotifyDistanceText = (EditText) findViewById(R.id.edit_bus_distance);
        motorNotifyDistanceText = (EditText) findViewById(R.id.edit_motor_distance);

        busSortTypeSpinner = (Spinner) findViewById(R.id.bus_sort_spinner);
        motorSortTypeSpinner = (Spinner) findViewById(R.id.motor_sort_spinner);

        okButton = (Button) findViewById(R.id.btn_ok);
        cancelButton = (Button) findViewById(R.id.btn_cancel);

        // set default value
        busNotifyDistanceText.setText(PrefStore.getBusNotifyDistance() + "");
        motorNotifyDistanceText.setText(PrefStore.getMotorNotifyDistance() + "");

        // set spinner
        busSortTypeSpinner.setSelection(PrefStore.getBusSortType());
        motorSortTypeSpinner.setSelection(PrefStore.getMotorSortType());

        // set cursor at end of line
        busNotifyDistanceText.setSelection(busNotifyDistanceText.getText().length());

        // set status for download map function
        progressBar.setVisibility(View.INVISIBLE);
        downloadCheckBox.setChecked(PrefStore.getIsMapDownloaded());

        // signal will notify on PackageListener (implementation on RouterApplication)
        downloadCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                PackageManager packageManager = RouterApplication.packageManager;
                // start download
                if (isChecked) {
                    Log.e("hqthao", "start downloading package");
                     packageManager.startPackageListDownload();

                    // waiting this line until list download
                    // packageManager.startPackageDownload(NUTITEQ.NOTITEQ_VN_CODE);
                    progressBar.setVisibility(View.VISIBLE);
                }
                // remove this package. if is downloading. cancel
                else {
                    PrefStore.setIsMapDownloaded(false);
                    Log.e("hqthao", "remove package");
                    // should not remove :)
                    // packageManager.startPackageRemove(NUTITEQ.NOTITEQ_VN_CODE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        /**
         * click listener
         */
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int busNotifyDistance, motorNotifyDistance;
                try {
                    busNotifyDistance = Integer.parseInt(busNotifyDistanceText.getText().toString());
                    motorNotifyDistance = Integer.parseInt(motorNotifyDistanceText.getText().toString());
                } catch (Exception e) {
                    busNotifyDistance  = 120;
                    motorNotifyDistance = 120;
                }

                PrefStore.setBusNotifyDistance(busNotifyDistance);
                PrefStore.setMotorNotifyDistance(motorNotifyDistance);
                PrefStore.setBusSortType(busSortTypeSpinner.getSelectedItemPosition());
                PrefStore.setMotorSortType(motorSortTypeSpinner.getSelectedItemPosition());

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

    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        super.onPause();
    }

    public void onEventMainThread(MapPackageMessage mapPackage) {
        switch (mapPackage.packageStatus) {
            case NUTITEQ.PackageStatus.PACKAGE_ACTION_READY:
                progressBar.setVisibility(View.INVISIBLE);
                break;
            case NUTITEQ.PackageStatus.PACKAGE_ACTION_DOWNLOADING:
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress((int) mapPackage.progress);
                buildDownloadNotification((int) mapPackage.progress);
                /*RouterApplication.dataSource = new PackageManagerTileDataSource(
                        RouterApplication.packageManager);*/
                break;
            case NUTITEQ.PackageStatus.PACKAGE_ACTION_REMOVING:
                progressBar.setVisibility(View.INVISIBLE);
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        mapPackage.packageId + ":" + mapPackage.packageStatus, Toast.LENGTH_SHORT).show();
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

    private void buildDownloadNotification(int progress) {
        int notificationId = 10;

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(com.fpt.router.library.R.drawable.ic_notification)
                        .setContentTitle("Downloading")
                        .setContentText("Download " + progress + "%");

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
