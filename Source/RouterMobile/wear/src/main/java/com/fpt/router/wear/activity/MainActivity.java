/*
 * Copyright 2015 Dejan Djurovski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fpt.router.wear.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fpt.router.R;
import com.fpt.router.library.model.message.LocationGPSMessage;
import com.fpt.router.library.model.message.LocationMessage;
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.library.model.motorbike.Location;
import com.fpt.router.library.model.motorbike.RouterDetailTwoPoint;
import com.fpt.router.library.utils.DecodeUtils;
import com.fpt.router.library.utils.MapUtils;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import android.support.wearable.view.DismissOverlayView;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class MainActivity extends Activity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener {

    private static final LatLng SYDNEY = new LatLng(-33.85704, 151.21522);
    public static ArrayList<Leg> listLeg = new ArrayList<>();
    public static long DATA_NEW_TIME_GET = -1;
    public static long DATA_OLD_TIME_GET = -1;

    Marker now;

    boolean isTracking = true;

    ImageView trackingButton;

    /**
     * Overlay that shows a short help text when first launched. It also provides an option to
     * exit the app.
     */
    private DismissOverlayView mDismissOverlay;
    private EventBus bus = EventBus.getDefault();


    /**
     * The map. It is initialized when the map has been fully loaded and is ready to be used.
     *
     * @see #onMapReady(com.google.android.gms.maps.GoogleMap)
     */
    private GoogleMap mMap;

    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        // Set the layout. It only contains a SupportMapFragment and a DismissOverlay.
        setContentView(R.layout.activity_data_map);

        /*Log.e("Nam:", "Leg: tren if");
        if(getIntent().getParcelableArrayListExtra("list_leg") != null) {
            Log.e("Nam:", "Leg: list lag != null" );
            listLeg = getIntent().getParcelableArrayListExtra("list_leg");
            Log.e("Nam:", "Leg: " + listLeg.size());
            if(mMap != null) {
                mMap.clear();
            }
        }*/

        // Retrieve the containers for the root of the layout and the map. Margins will need to be
        // set on them to account for the system window insets.
        final FrameLayout topFrameLayout = (FrameLayout) findViewById(R.id.root_container);
        final FrameLayout mapFrameLayout = (FrameLayout) findViewById(R.id.map_container);

        // Set the system view insets on the containers when they become available.
        topFrameLayout.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                // Call through to super implementation and apply insets
                insets = topFrameLayout.onApplyWindowInsets(insets);

                FrameLayout.LayoutParams params =
                        (FrameLayout.LayoutParams) mapFrameLayout.getLayoutParams();

                // Add Wearable insets to FrameLayout container holding map as margins
                params.setMargins(
                        insets.getSystemWindowInsetLeft(),
                        insets.getSystemWindowInsetTop(),
                        insets.getSystemWindowInsetRight(),
                        insets.getSystemWindowInsetBottom());
                mapFrameLayout.setLayoutParams(params);

                return insets;
            }
        });

        // Obtain the DismissOverlayView and display the intro help text.
        mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        mDismissOverlay.setIntroText(R.string.intro_text);
        mDismissOverlay.showIntroIfNecessary();

        trackingButton = (ImageView) findViewById(R.id.tracking_button);
        trackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hqthao", "tracking button click. current status: " + isTracking);
                isTracking = !isTracking;
            }
        });

        // Obtain the MapFragment and set the async listener to be notified when the map is ready.
        MapFragment mapFragment =
                (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    protected void onResume() {
        super.onResume();

        if((listLeg != null) && (DATA_NEW_TIME_GET != DATA_OLD_TIME_GET)) {
            mMap.clear();
            if (listLeg.size() == 1) {
                MapUtils.drawMapWithTwoPoint(mMap, listLeg);
            } else {
                MapUtils.drawMapWithFourPoint(mMap, listLeg);
            }
        }
        Log.e("hqthao", "register bus");
        bus.register(this);
    }

    @Override
    protected void onPause() {
        Log.e("hqthao", "unregister bus");
        bus.unregister(this);
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Map is ready to be used.
        mMap = googleMap;

        // Set the long click listener as a way to exit the map.
        mMap.setOnMapLongClickListener(this);

        if(listLeg.size() == 1){
            MapUtils.drawMapWithTwoPoint(mMap, listLeg);
        } else {
            MapUtils.drawMapWithFourPoint(mMap, listLeg);
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        // Display the dismiss overlay with a button to exit this activity.
        mDismissOverlay.show();
    }

    public void onEventMainThread(LocationGPSMessage event){
        Log.e("hqthao", event.location.getLatitude() + "");
        updateGPS(event.location);
    }

    public void updateGPS(Location location) {
        if(now != null){
            now.remove();
        }
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
        Log.e("Nam:" , latitude + "" + longitude);
        now = MapUtils.drawPointColor(mMap, latitude, longitude, "", BitmapDescriptorFactory.HUE_RED);
        if (isTracking) {
            MapUtils.moveCamera(mMap, latitude, longitude, 15);
        }

    }
}
