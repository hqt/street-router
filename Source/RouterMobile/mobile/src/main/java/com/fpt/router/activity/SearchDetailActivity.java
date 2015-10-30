package com.fpt.router.activity;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.fragment.AbstractMapFragment;
import com.fpt.router.fragment.BusDetailFourPointFragment;
import com.fpt.router.fragment.BusDetailTwoPointFragment;
import com.fpt.router.fragment.MotorDetailFragment;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.common.NotifyModel;
import com.fpt.router.library.model.message.LocationMessage;
import com.fpt.router.service.GPSServiceOld;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import de.greenrobot.event.EventBus;

public class SearchDetailActivity extends AppCompatActivity implements LocationListener,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{
    private EventBus bus = EventBus.getDefault();
    private GoogleApiClient mGoogleApiClient;
    AbstractMapFragment fragment;
    private int position;
    int countListLatLng = 0;
    int countListStep = 0;

    boolean isFakeGPS = false;
    boolean isPlaySound = false;

    private static final int BUFFER_SIZE = 4096;
    String host = "http://118.69.135.22/synthesis/file?voiceType=female&text=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        ImageButton soundButton = (ImageButton) findViewById(R.id.btn_get_sound);
        ImageButton fakeGPSButton = (ImageButton) findViewById(R.id.btn_fake_gps);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        Result result = (Result)getIntent().getSerializableExtra("result");
        Journey journey = (Journey) getIntent().getSerializableExtra("journey");
        position = getIntent().getIntExtra("position", -1);
        if(result != null){
            if (savedInstanceState == null) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                trans.add(R.id.fragment, BusDetailTwoPointFragment.newInstance(result));
                trans.commit();
            }
        }

        if(position != -1){
            if (savedInstanceState == null) {

                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                fragment = MotorDetailFragment.newInstance(position);
                trans.add(R.id.fragment, fragment);
                trans.commit();

                soundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isPlaySound = !isPlaySound;
                        if(isPlaySound) {
                            DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
                            downloadAsyncTask.execute();
                        }
                        GPSServiceOld.isPlaySound = !GPSServiceOld.isPlaySound;
                    }
                });

                fakeGPSButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isFakeGPS = !isFakeGPS;
                        if (isFakeGPS) {
                            GPSServiceOld.turnOnFakeGPS(fragment.getFakeGPSList());
                        } else {
                            GPSServiceOld.turnOffFakeGPS();
                        }
                    }
                });


            }
        }

        if(journey != null){
            if (savedInstanceState == null) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                trans.add(R.id.fragment, BusDetailFourPointFragment.newInstance(journey));
                trans.commit();
            }
        }



    }

    @Override
    protected void onStart(){
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

    public void onEventMainThread(LocationMessage event){
        onLocationChanged(event.location);
    }

    public void onEventMainThread(NotifyModel event){
        Log.e("hqthao", event.location.getLatitude() + "aaaa");
        Toast.makeText(SearchDetailActivity.this, event.smallMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("Nam", "Nam dep trai");

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        fragment.drawCurrentLocation(latitude, longitude);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class DownloadAsyncTask extends AsyncTask<String, Void, String> {

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
        protected String doInBackground(String... strings) {

            String textInput = null;
            for(int i = 0; i < GPSServiceOld.getNotifyModel().size(); i++) {
                try {
                    textInput = URLEncoder.encode(GPSServiceOld.getNotifyModel().get(i).smallMessage, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String fileURL = host + textInput;
                String saveDir = GPSServiceOld.getNotifyModel().get(i).smallMessage+".wav";

                File root = Environment.getExternalStorageDirectory();
                File dir = new File(root.getAbsolutePath() + "/smac");
                File fileSMAC = new File(dir, saveDir);
                if(fileSMAC.exists()) {
                    continue;
                }


                try {
                    URL url = new URL(fileURL);
                    Log.e("FUCKTHAO", "----" + url.toString());
                    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                    int responseCode = httpConn.getResponseCode();

                    // always check HTTP response code first
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        String fileName = "";
                        String disposition = httpConn.getHeaderField("Content-Disposition");
                        String contentType = httpConn.getContentType();
                        int contentLength = httpConn.getContentLength();

                        if (disposition != null) {
                            // extracts file name from header field
                            int index = disposition.indexOf("filename=");
                            if (index > 0) {
                                fileName = disposition.substring(index + 10,
                                        disposition.length() - 1);
                            }
                        } else {
                            // extracts file name from URL
                            fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                                    fileURL.length());
                        }

                        System.out.println("Content-Type = " + contentType);
                        System.out.println("Content-Disposition = " + disposition);
                        System.out.println("Content-Length = " + contentLength);
                        System.out.println("fileName = " + fileName);

                        // opens input stream from the HTTP connection
                        InputStream inputStream = httpConn.getInputStream();
                        String saveFilePath = saveDir + File.separator + fileName;

                        //Save file in folder
                        dir.mkdirs();
                        File file = new File(dir, saveDir);


                        // opens an output stream to save into file
                        FileOutputStream outputStream = new FileOutputStream(file);

                        int bytesRead = -1;
                        byte[] buffer = new byte[BUFFER_SIZE];
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }

                        outputStream.close();
                        inputStream.close();

                        System.out.println("File downloaded");
                    } else {
                        System.out.println("No file to download. Server replied HTTP code: " + responseCode);
                    }
                    httpConn.disconnect();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

        }
    }


}
