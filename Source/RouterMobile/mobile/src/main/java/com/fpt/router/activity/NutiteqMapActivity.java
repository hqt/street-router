package com.fpt.router.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fpt.router.R;
import com.nutiteq.core.MapPos;
import com.nutiteq.core.MapRange;
import com.nutiteq.datasources.LocalVectorDataSource;
import com.nutiteq.layers.NutiteqOnlineVectorTileLayer;
import com.nutiteq.layers.VectorLayer;
import com.nutiteq.layers.VectorTileLayer;
import com.nutiteq.projections.EPSG3857;
import com.nutiteq.styles.MarkerStyle;
import com.nutiteq.styles.MarkerStyleBuilder;
import com.nutiteq.ui.MapView;
import com.nutiteq.utils.BitmapUtils;
import com.nutiteq.vectorelements.Marker;

public class NutiteqMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutiteq_map);
     //   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // 0. The initial step: register your license. This must be done before using MapView!
        // You can get your free/commercial license from: http://developer.nutiteq.com
        // The license string used here is intended only for Nutiteq demos and WILL NOT WORK with other apps!
        MapView.registerLicense("XTUMwQ0ZRQ2dmcjhNMW1BeG5BLzh5OFhyYnRUbnVwbnBPQUlVWFRud3JhUUQ5cklqdXJDMEdKVG9CUDc0dEVjPQoKcHJvZHVjdHM9c2RrLWFuZHJvaWQtMy4qCnBhY2thZ2VOYW1lPWNvbS5udXRpdGVxLmhlbGxvbWFwMwp3YXRlcm1hcms9bnV0aXRlcQp1c2VyS2V5PTA2ZmRlNDU4ODI0NTliMDI4ZDdjMzViODVhMWMyYjY1Cg",
                getApplicationContext());

        // 1. Basic map setup
        // Create map view
        MapView mapView = (MapView) this.findViewById(R.id.map_view);

        // Set the base projection, that will be used for most MapView, MapEventListener and Options methods
        EPSG3857 proj = new EPSG3857();
        mapView.getOptions().setBaseProjection(proj); // note: EPSG3857 is the default, so this is actually not required

        // General options
        mapView.getOptions().setRotatable(true); // make map rotatable (this is also the default)
        mapView.getOptions().setTileThreadPoolSize(2); // use 2 download threads for tile downloading

        // Set initial location and other parameters, don't animate
        mapView.setFocusPos(proj.fromWgs84(new MapPos(13.38933, 52.51704)), 0); // Berlin
        mapView.setZoom(2, 0); // zoom 2, duration 0 seconds (no animation)
        mapView.setMapRotation(0, 0);
        mapView.setTilt(90, 0);

        // Create base layer. Use vector style from assets (osmbright.zip)
        VectorTileLayer baseLayer = new NutiteqOnlineVectorTileLayer("osmbright.zip");
        mapView.getLayers().add(baseLayer);

        // 2. Add a pin marker to map
        // Initialize a local vector data source
        LocalVectorDataSource vectorDataSource1 = new LocalVectorDataSource(proj);

        // Create marker style, by first loading marker bitmap
        Bitmap androidMarkerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notification);
        com.nutiteq.graphics.Bitmap markerBitmap = BitmapUtils.createBitmapFromAndroidBitmap(androidMarkerBitmap);
        MarkerStyleBuilder markerStyleBuilder = new MarkerStyleBuilder();
        markerStyleBuilder.setBitmap(markerBitmap);
        markerStyleBuilder.setSize(30);
        MarkerStyle sharedMarkerStyle = markerStyleBuilder.buildStyle();
        // Add marker to the local data source
        Marker marker1 = new Marker(proj.fromWgs84(new MapPos(13.38933, 52.51704)), sharedMarkerStyle);
        vectorDataSource1.add(marker1);

        // Create a vector layer with the previously created data source
        VectorLayer vectorLayer1 = new VectorLayer(vectorDataSource1);
        // Add the previous vector layer to the map
        mapView.getLayers().add(vectorLayer1);
        // Set visible zoom range for the vector layer
        vectorLayer1.setVisibleZoomRange(new MapRange(0, 24)); // this is optional, by default layer is visible for all zoom levels


    }

}
