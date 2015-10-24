package com.fpt.router.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.fpt.router.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class test extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mGoogleApiClient;
    private List<String> listLocation = new ArrayList<>();
    private int PLACE_PICKER_REQUEST = 1;
    private TextView mTextView;
    private AutoCompleteTextView mPredictTextView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient
                .Builder( this )
                .enableAutoManage( this, 0, this )
                .addApi( Places.GEO_DATA_API )
                .addApi( Places.PLACE_DETECTION_API )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .build();

        displayPredictiveResults("A");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if( mGoogleApiClient != null )
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    private void findPlaceById( String id ) {
        if( TextUtils.isEmpty(id) || mGoogleApiClient == null || !mGoogleApiClient.isConnected() )
            return;

       Places.GeoDataApi.getPlaceById( mGoogleApiClient, id ) .setResultCallback(new ResultCallback<PlaceBuffer>() {
           @Override
           public void onResult(PlaceBuffer places) {
               if (places.getStatus().isSuccess()) {
                   Place place = places.get(0);
                   displayPlace(place);
                   mPredictTextView.setText("");
               }

               //Release the PlaceBuffer to prevent a memory leak
               places.release();
           }
       });
    }

    private void guessCurrentPlace() {
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace( mGoogleApiClient, null );
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {

                PlaceLikelihood placeLikelihood = likelyPlaces.get(0);
                String content = "";
                if (placeLikelihood != null && placeLikelihood.getPlace() != null && !TextUtils.isEmpty(placeLikelihood.getPlace().getName()))
                    content = "Most likely place: " + placeLikelihood.getPlace().getName() + "\n";
                if (placeLikelihood != null)
                    content += "Percent change of being there: " + (int) (placeLikelihood.getLikelihood() * 100) + "%";
                mTextView.setText(content);

                likelyPlaces.release();
            }
        });
    }

    private void displayPlacePicker() {
        if( mGoogleApiClient == null || !mGoogleApiClient.isConnected() )
            return;

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult( builder.build( getApplicationContext() ), PLACE_PICKER_REQUEST );
        } catch ( GooglePlayServicesRepairableException e ) {
                Log.d("PlacesAPI Demo", "GooglePlayServicesRepairableException thrown");
        } catch ( GooglePlayServicesNotAvailableException e ) {
            Log.d("PlacesAPI Demo", "GooglePlayServicesNotAvailableException thrown");
        }
    }

    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        if( requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK ) {
            displayPlace( PlacePicker.getPlace(data, this) );
        }
    }

    private void displayPlace( Place place ) {
        if( place == null )
            return;

        String content = "";
        if( !TextUtils.isEmpty(place.getName()) ) {
            content += "Name: " + place.getName() + "\n";
        }
        if( !TextUtils.isEmpty(place.getAddress()) ) {
            content += "Address: " + place.getAddress() + "\n";
        }
        if( !TextUtils.isEmpty(place.getPhoneNumber()) ) {
            content += "Phone: " + place.getPhoneNumber();
        }

        mTextView.setText( content );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        int id = item.getItemId();
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onConnected( Bundle bundle ) {
    }

    @Override
    public void onConnectionSuspended( int i ) {

    }

    @Override
    public void onConnectionFailed( ConnectionResult connectionResult ) {

    }

    private void displayPredictiveResults( String query )
    {
        //Southwest corner to Northeast corner.
        LatLngBounds bounds = new LatLngBounds( new LatLng( 10.645425, 106.472626 ), new LatLng( 11.085933, 106.783676 ) );

        //Filter: https://developers.google.com/places/supported_types#table3
        List<Integer> filterTypes = new ArrayList<Integer>();


        Places.GeoDataApi.getAutocompletePredictions( mGoogleApiClient, query, bounds, AutocompleteFilter.create(filterTypes) )
                .setResultCallback (
                        new ResultCallback<AutocompletePredictionBuffer>() {
                            @Override
                            public void onResult( AutocompletePredictionBuffer buffer ) {

                                if( buffer == null )
                                    return;

                                if( buffer.getStatus().isSuccess() ) {
                                    for( AutocompletePrediction prediction : buffer ) {
                                        //Add as a new item to avoid IllegalArgumentsException when buffer is released
                                        listLocation.add(prediction.getDescription());
                                        Log.e("Place id: ", prediction.getPlaceId() + " %%% " + prediction.getDescription() + " %%% " + prediction.getMatchedSubstrings());
                                    }
                                }

                                //Prevent memory leak by releasing buffer
                                buffer.release();
                                System.out.println("ee");
                            }
                        }, 60, TimeUnit.SECONDS );
    }
}
