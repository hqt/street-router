package com.fpt.router.activity;

import android.app.ProgressDialog;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.fragment.AbstractMapFragment;
import com.fpt.router.fragment.BusDetailFourPointFragment;
import com.fpt.router.fragment.BusDetailTwoPointFragment;
import com.fpt.router.fragment.MotorDetailFourPointFragment;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.message.LocationMessage;
import com.fpt.router.library.model.motorbike.DetailLocation;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.model.motorbike.Step;
import com.fpt.router.library.utils.DecodeUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wearable.Wearable;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class SearchDetailActivity extends AppCompatActivity implements LocationListener,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{
    private EventBus bus = EventBus.getDefault();
    private GoogleApiClient mGoogleApiClient;
    AbstractMapFragment fragment;
    private int position;
    private List<Leg> listLeg = SearchRouteActivity.listLeg;
    private List<Leg> listFinalLeg = new ArrayList<>();
    private List<Step> listStep = new ArrayList<>();
    int countListLatLng = 0;
    int countListStep = 0;

    boolean isFakeGPS = false;
    boolean isPlaySound = false;

    private static final int BUFFER_SIZE = 4096;
    String host = "http://118.69.135.22/synthesis/file?voiceType=female&text=";
    MediaPlayer mp;
    Uri uri;
    File fileSMAC;
    FileDescriptor fd = null;

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
                fragment = MotorDetailFourPointFragment.newInstance(position);
                trans.add(R.id.fragment, fragment);
                trans.commit();
            }
        }

        if(journey != null){
            if (savedInstanceState == null) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                trans.add(R.id.fragment, BusDetailFourPointFragment.newInstance(journey));
                trans.commit();
            }
        }

        if(SearchRouteActivity.listLocation.size() == 2) {
            listFinalLeg.add(listLeg.get(position));
        } else if (SearchRouteActivity.listLocation.size() == 3) {
            listFinalLeg.addAll(listLeg);
        } else {
            for(int n = position*3; n < position*3+3; n++) {
                listFinalLeg.add(listLeg.get(n));
            }
        }
        for(int n = 0; n < listFinalLeg.size(); n ++) {
            listStep.addAll(listFinalLeg.get(n).getSteps());
        }

        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlaySound = !isPlaySound;
                if(isFakeGPS) {
                    DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
                    downloadAsyncTask.execute();
                }
            }
        });

        fakeGPSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFakeGPS = !isFakeGPS;
            }
        });

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

    public void onEvent(LocationMessage event){
        Log.e("hqthao", event.location.getLatitude() + "");
        onLocationChanged(event.location);
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

        List<Leg> listFinalLeg = new ArrayList<>();
        if(SearchRouteActivity.mapLocation.size() == 2) {
            listFinalLeg.add(listLeg.get(position));
        } else if (SearchRouteActivity.mapLocation.size() == 3) {
            listFinalLeg.addAll(listLeg);
        } else {
            for(int n = position*3; n < position*3+3; n++) {
                listFinalLeg.add(listLeg.get(n));
            }
        }
        List<LatLng> listLatLng = DecodeUtils.getListLocationToFakeGPS(listFinalLeg);

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        if(isFakeGPS) {
            latitude = listLatLng.get(countListLatLng).latitude;
            longitude = listLatLng.get(countListLatLng).longitude;
            if (countListLatLng == listLatLng.size() - 1) {
                countListLatLng = 0;
            } else {
                countListLatLng++;
            }
        }
        com.fpt.router.library.model.motorbike.Location startLocation = listStep.get(countListStep).getDetailLocation().getStartLocation();
        LatLng latlngOfStep = new LatLng(startLocation.getLatitude(), startLocation.getLongitude());
        Log.e("Khoang cach:", ""+ DecodeUtils.calculateDistance(listLatLng.get(countListLatLng), latlngOfStep));
        if(DecodeUtils.calculateDistance(listLatLng.get(countListLatLng), latlngOfStep) < 50) {
            String delimiter = "<div style=\"font-size:0.9em\">";
            String[] temp;
            String str = listStep.get(countListStep).getInstruction();
            temp = str.split(delimiter);
            Toast.makeText(SearchDetailActivity.this, temp[0], Toast.LENGTH_SHORT).show();
            if(isPlaySound) {
                playSound(countListStep);
            }
            if(countListStep == listStep.size()-1) {
                countListStep = 0;
            } else {
                countListStep++;
            }

        }
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
            for(int i = 0; i < listStep.size(); i++) {
                try {
                    String delimiter = "<div style=\"font-size:0.9em\">";
                    String[] temp;
                    String str = listStep.get(i).getInstruction();
                    temp = str.split(delimiter);
                    textInput = URLEncoder.encode(temp[0], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String fileURL = host + textInput;
                String saveDir = i+".wav";

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
                        File root = Environment.getExternalStorageDirectory();
                        File dir = new File(root.getAbsolutePath() + "/smac");
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

    public void playSound (int count) {
        File root = Environment.getExternalStorageDirectory();
        mp = new MediaPlayer();
        File dir = new File(root.getAbsolutePath() + "/smac");
        dir.mkdirs();
        fileSMAC = new File(dir, count+".wav");
        uri = Uri.fromFile(fileSMAC);
        Log.d("QUYYY111", "--" + fileSMAC.getAbsolutePath() + "--");
        try {
            FileInputStream fis = new FileInputStream(fileSMAC);
            fd = fis.getFD();



            MediaPlayer mp = new MediaPlayer();
            try {
                mp.setDataSource(fd);
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.start();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
