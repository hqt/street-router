package com.fpt.router.wear.activity;

import com.fpt.router.R;
import com.fpt.router.library.model.message.LocationMessage;
import com.fpt.router.library.utils.MapUtils;
import com.fpt.router.wear.service.GPSFuseService;
import com.fpt.router.wear.service.GPSServiceOld;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import de.greenrobot.event.EventBus;

public class MapsActivity extends Activity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, LocationListener {

    private EventBus bus = EventBus.getDefault();
    private Marker now;
    /**
     * Overlay that shows a short help text when first launched. It also provides an option to exit the app.
     */
    private DismissOverlayView mDismissOverlay;

    /**
     * The map. It is initialized when the map has been fully loaded and is ready to be used.
     *
     * @see #onMapReady(com.google.android.gms.maps.GoogleMap)
     */
    private GoogleMap mMap;

    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        Log.e("hqthao", "Oncreated");

        // Set the layout. It only contains a MapFragment and a DismissOverlay.
        setContentView(R.layout.activity_maps);

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

        // Obtain the DismissOverlayView and display the introductory help text.
        mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        mDismissOverlay.setIntroText(R.string.intro_text);
        mDismissOverlay.showIntroIfNecessary();

        // Obtain the MapFragment and set the async listener to be notified when the map is ready.
        MapFragment mapFragment =
                (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // start service
        Intent intent = new Intent(MapsActivity.this, GPSServiceOld.class);
        startService(intent);
        GPSServiceOld gpsService = new GPSServiceOld(MapsActivity.this);
       // MapUtils.drawPointColor(mMap, gpsService.getLatitude(), gpsService.getLongitude(), "Current", BitmapDescriptorFactory.HUE_RED);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("hqthao", "Map is ready to use");

        // Map is ready to be used.
        mMap = googleMap;

        // Set the long click listener as a way to exit the map.
        mMap.setOnMapLongClickListener(this);

        // Add a marker in Sydney, Australia and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        // Display the dismiss overlay with a button to exit this activity.
        mDismissOverlay.show();
    }

    public void onEvent(LocationMessage event){
        Log.e("hqthao", event.location.getLatitude() + "");
        onLocationChanged(event.location);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(now != null){
            //now.remove();

        }
        if(location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            MapUtils.drawPointColor(mMap, latitude, longitude, "Current", BitmapDescriptorFactory.HUE_RED);
            MapUtils.moveCamera(mMap, latitude, longitude, 15);
        }
    }
}
