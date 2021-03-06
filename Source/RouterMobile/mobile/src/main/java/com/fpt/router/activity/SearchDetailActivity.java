package com.fpt.router.activity;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.fragment.base.AbstractMapFragment;
import com.fpt.router.fragment.base.AbstractNutiteqMapFragment;
import com.fpt.router.fragment.BusDetailFourPointFragment;
import com.fpt.router.fragment.BusDetailTwoPointFragment;
import com.fpt.router.fragment.MotorNutiteqDetailFragment;
import com.fpt.router.framework.PrefStore;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.config.AppConstants.FPT_SERVICE;
import com.fpt.router.library.config.AppConstants.FileCache;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.common.NotifyModel;
import com.fpt.router.library.model.message.LocationMessage;
import com.fpt.router.library.utils.cache.DiskLruSoundCache;
import com.fpt.router.library.utils.StringUtils;
import com.fpt.router.service.GPSServiceOld;
import com.fpt.router.utils.NetworkUtils;
import com.fpt.router.utils.NotifyUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wearable.Wearable;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import de.greenrobot.event.EventBus;


public class SearchDetailActivity extends AppCompatActivity implements LocationListener,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{
    private EventBus bus = EventBus.getDefault();
    private GoogleApiClient mGoogleApiClient;
    AbstractMapFragment fragment;
    AbstractNutiteqMapFragment fragmentNutiteq;
    private int position;
    int countListLatLng = 0;
    int countListStep = 0;

    boolean isFakeGPS = false;
    boolean isPlaySound = false;

    LatLng wrongWay;
    ImageButton wrongwayButton;
    ImageButton soundButton;
    ImageButton buttonHidenSound;
    ImageButton fakeGPSButton ;
    ImageButton buttonHideFakeGPS ;

    private static final int BUFFER_SIZE = 4096;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        GPSServiceOld.initializeState();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        wrongwayButton = (ImageButton) findViewById(R.id.btn_wrong_way);
        wrongwayButton.setVisibility(View.INVISIBLE);
        soundButton = (ImageButton) findViewById(R.id.btn_get_sound);
        buttonHidenSound = (ImageButton) findViewById(R.id.btn_hide_get_sound);
        fakeGPSButton = (ImageButton) findViewById(R.id.btn_fake_gps);
        buttonHideFakeGPS = (ImageButton) findViewById(R.id.btn_hide_fake_gps);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        Result result = (Result) getIntent().getSerializableExtra("result");
        Journey journey = (Journey) getIntent().getSerializableExtra("journey");
        position = getIntent().getIntExtra("position", -1);

        // turn off fake gps
        GPSServiceOld.isFakeGPS = false;

        if (result != null) {
            if (savedInstanceState == null) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                fragmentNutiteq = BusDetailTwoPointFragment.newInstance(result);
                trans.add(R.id.fragment, fragmentNutiteq);
                trans.commit();
                test(fragmentNutiteq, PrefStore.getBusNotifyDistance());
            }
        }

        if (position != -1) {
            if (savedInstanceState == null) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                fragmentNutiteq = MotorNutiteqDetailFragment.newInstance(position);
                trans.add(R.id.fragment, fragmentNutiteq);
                wrongwayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentNutiteq.setUpMapAgain(wrongWay);
                        isPlaySound = false;
                        isFakeGPS = false;
                        GPSServiceOld.isPlaySound = isPlaySound;
                        GPSServiceOld.turnOffFakeGPS();
                        soundButton.setVisibility(View.GONE);
                        buttonHidenSound.setVisibility(View.VISIBLE);
                        buttonHideFakeGPS.setVisibility(View.VISIBLE);
                        fakeGPSButton.setVisibility(View.GONE);
                        wrongwayButton.setVisibility(View.INVISIBLE);
                    }
                });
                trans.commit();
                test(fragmentNutiteq, PrefStore.getMotorNotifyDistance());
            }
        }

        if (journey != null) {
            if (savedInstanceState == null) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                fragmentNutiteq = BusDetailFourPointFragment.newInstance(journey);
                trans.add(R.id.fragment, fragmentNutiteq);
                trans.commit();
                test(fragmentNutiteq, PrefStore.getBusNotifyDistance());
            }
        }
    }

    public void test (final AbstractNutiteqMapFragment frag, final int distance){
        buttonHidenSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHidenSound.setVisibility(View.GONE);
                soundButton.setVisibility(View.VISIBLE);
                isPlaySound = true;
                DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
                downloadAsyncTask.execute();
                GPSServiceOld.isPlaySound = isPlaySound;
            }
        });
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundButton.setVisibility(View.GONE);
                buttonHidenSound.setVisibility(View.VISIBLE);
                isPlaySound = false;
                GPSServiceOld.isPlaySound = isPlaySound;
            }
        });

        buttonHideFakeGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHideFakeGPS.setVisibility(View.GONE);
                fakeGPSButton.setVisibility(View.VISIBLE);
                GPSServiceOld.setDistance((double) distance);
                GPSServiceOld.turnOnFakeGPS(frag.getFakeGPSList());
            }
        });

        fakeGPSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHideFakeGPS.setVisibility(View.VISIBLE);
                fakeGPSButton.setVisibility(View.GONE);
                GPSServiceOld.turnOffFakeGPS();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Intent intent = new Intent(MainActivity.this, GPSServiceOld.class);
        // startService(intent);
    }

    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();

        bus.register(this);
    }

    @Override
    protected void onPause() {
        bus.unregister(this);
        mGoogleApiClient.disconnect();
        super.onPause();
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

    public void onEventMainThread(LocationMessage event) {
        if(GPSServiceOld.getIsTrueWay()) {
            wrongwayButton.setVisibility(View.INVISIBLE);
        } else {
            wrongwayButton.setVisibility(View.VISIBLE);
        }

        onLocationChanged(event.location);
    }

    public void onEventMainThread(NotifyModel event) {
        Toast.makeText(SearchDetailActivity.this, event.smallMessage, Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(String event) {
        NotifyUtils.notifyUnderRequest(event, SearchDetailActivity.this, isPlaySound);
        if(!event.equals(AppConstants.SAI_DUONG) && !event.equals(AppConstants.DI_THANG)) {
            String text = event.replace(", Hồ Chí Minh", "");
            event = text.replace(", Việt Nam", "");
            TextView test = (TextView) findViewById(R.id.txt_cua_nam);
            if (!test.getText().equals("Đang đi tới:" + event)) {
                test.setText("Đang đi tới:" + event);
            }
        }
    }

    public void onEventMainThread(LatLng event) {
        wrongWay = event;
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        fragmentNutiteq.drawCurrentLocation(latitude, longitude);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class DownloadAsyncTask extends AsyncTask<String, Integer, String> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchDetailActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int percent = (int) (1.0 * values[0] /GPSServiceOld.getNotifyModel().size() * 100);
            pDialog.setProgress(percent);
            pDialog.setMessage("Download " + percent + "% complete");
        }

        @Override
        protected String doInBackground(String... strings) {

            String textInput = null;
            boolean isServiceAvailable = true;
            DiskLruSoundCache soundCache = new DiskLruSoundCache(getApplicationContext(), FileCache.FOLDER_NAME, FileCache.SYSTEM_SIZE);

            for (int i = 0; i < GPSServiceOld.getNotifyModel().size(); i++) {
                NotifyModel model = GPSServiceOld.getNotifyModel().get(i);

                // check cache
                String key = StringUtils.normalizeFileCache(model.smallMessage);

                // already exist. doesn't need to download anymore
                if (soundCache.containsKey(key)) {
                    continue;
                }

                try {
                    textInput = URLEncoder.encode(model.smallMessage, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String fileURL = FPT_SERVICE.TEXT_TO_SPEECH + textInput;


                byte[] data = NetworkUtils.downloadSoundFile(fileURL);

                if (data == null) {
                    isServiceAvailable = false;
                }

                soundCache.put(key, data);
                publishProgress(i);

            }

            if (isServiceAvailable) {
                return "success";
            } else {
                return "fail";
            }
        }

        @Override
        protected void onPostExecute(String result) {

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (result.equals("fail")) {
                Toast.makeText(SearchDetailActivity.this, "FPT Service is not available, please try again later.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
