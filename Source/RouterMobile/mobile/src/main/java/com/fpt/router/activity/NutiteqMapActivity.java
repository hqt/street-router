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
import com.fpt.router.library.model.motorbike.Leg;
import com.fpt.router.utils.NetworkUtils;
import com.fpt.router.utils.NutiteqMapUtil;
import com.google.android.gms.maps.model.LatLng;
import com.nutiteq.core.MapPos;
import com.nutiteq.core.MapRange;
import com.nutiteq.datasources.LocalVectorDataSource;
import com.nutiteq.graphics.Color;
import com.nutiteq.layers.NutiteqOnlineVectorTileLayer;
import com.nutiteq.layers.VectorLayer;
import com.nutiteq.layers.VectorTileLayer;
import com.nutiteq.projections.EPSG3857;
import com.nutiteq.styles.LineJointType;
import com.nutiteq.styles.LineStyleBuilder;
import com.nutiteq.styles.MarkerStyle;
import com.nutiteq.styles.MarkerStyleBuilder;
import com.nutiteq.ui.MapView;
import com.nutiteq.utils.BitmapUtils;
import com.nutiteq.vectorelements.Line;
import com.nutiteq.vectorelements.Marker;
import com.nutiteq.wrappedcommons.MapPosVector;

import java.util.ArrayList;
import java.util.List;

public class NutiteqMapActivity extends VectorMapSampleBaseActivity {
    List<Leg> listLeg = SearchRouteActivity.listLeg;
    List<Leg> listFinalLeg = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // MapSampleBaseActivity creates and configures mapView
        super.onCreate(savedInstanceState);

        // Add a pin marker to map
        // 1. Initialize a local vector data source
        LocalVectorDataSource vectorDataSource = new LocalVectorDataSource(baseProjection);
        // Initialize a vector layer with the previous data source
        VectorLayer vectorLayer1 = new VectorLayer(vectorDataSource);
        // Add the previous vector layer to the map
        mapView.getLayers().add(vectorLayer1);
        // Set visible zoom range for the vector layer
        vectorLayer1.setVisibleZoomRange(new MapRange(0, 18));

        // 2. Create marker style
        Marker marker = NutiteqMapUtil.drawMarkerNutiteq(mapView, vectorDataSource, getResources(), 10.855090, 106.628394, R.drawable.ic_notification);

        // Add first line
        listFinalLeg.add(listLeg.get(0));
        NutiteqMapUtil.drawMapWithTwoPoint(mapView, vectorDataSource, getResources(), baseProjection, listFinalLeg);
        MapPos markerPos = mapView.getOptions().getBaseProjection().fromWgs84(new MapPos(106.628394, 10.855090));
        mapView.setFocusPos(markerPos, 1);
        mapView.setZoom(12, 1);
    }

}
