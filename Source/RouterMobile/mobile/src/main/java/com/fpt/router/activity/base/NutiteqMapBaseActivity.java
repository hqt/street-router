package com.fpt.router.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fpt.router.R;
import com.nutiteq.core.MapPos;
import com.nutiteq.core.MapRange;
import com.nutiteq.layers.TileLayer;
import com.nutiteq.projections.EPSG3857;
import com.nutiteq.projections.Projection;
import com.nutiteq.ui.MapView;

/**
 * Base activity for map samples. Includes simple lifecycle management
 */
public class NutiteqMapBaseActivity extends AppCompatActivity {

    protected MapView mapView;
    protected Projection baseProjection;
    protected TileLayer baseLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutiteq_map);

        com.nutiteq.utils.Log.setShowDebug(true);
        com.nutiteq.utils.Log.setShowInfo(true);

        // 0. The initial step: register your license. This must be done before using MapView!
        // You can get your free/commercial license from: http://developer.nutiteq.com
        // The license string used here is intended only for Nutiteq demos and WILL NOT WORK with other apps!
        // already loaded when application start. on loading PackageManager
        MapView.registerLicense("XTUN3Q0ZCdzBJSXRNV0tRNG9LMFVwemozek5ieGFpd2ZBaFFOQVc2WWdMM3puS01oNGdKRHpXYmxjZm5ybWc9PQoKcHJvZHVjdHM9c2RrLWFuZHJvaWQtMy4qCnBhY2thZ2VOYW1lPWNvbS5mcHQucm91dGVyCndhdGVybWFyaz1udXRpdGVxCnVzZXJLZXk9NjJlNDNjOGY3YWNkMmQ3NTNlZmQ5NzExZTAwYTM3MTYK", getApplicationContext());

        // 1. Basic map setup
        // Create map view
        mapView = (MapView) this.findViewById(R.id.mapView);

        // Set the base projection, that will be used for most MapView, MapEventListener and Options methods
        baseProjection = new EPSG3857();
        mapView.getOptions().setBaseProjection(baseProjection);
        // General options
        mapView.getOptions().setTileDrawSize(256);
//        mapView.getOptions().setTileThreadPoolSize(4);

        // Review following and change if needed
        mapView.getOptions().setRotatable(true);

        mapView.getOptions().setZoomRange(new MapRange(0, 18));

        Log.d("NAM:", "autoconfigured DPI="+mapView.getOptions().getDPI());

        // Set default location
//      mapView.setFocusPos(baseProjection.fromWgs84(new MapPos(24.650415, 59.420773)), 0); // tallinn
//      mapView.setFocusPos(baseProjection.fromWgs84(new MapPos(19.04468, 47.4965)), 0); // budapest
        mapView.setFocusPos(baseProjection.fromWgs84(new MapPos(13.38933, 52.51704)), 0); // berlin
        mapView.setZoom(2, 0);
        mapView.setMapRotation(0, 0);
        mapView.setTilt(90, 0);

    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        MapPos focusPos = new MapPos(bundle.getDouble("focusX"), bundle.getDouble("focusY"));
        mapView.setFocusPos(focusPos, 0);
        mapView.setZoom(bundle.getFloat("zoom"), 0);
        mapView.setMapRotation(bundle.getFloat("rotation"), 0);
        mapView.setTilt(bundle.getFloat("tilt"), 0);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        MapPos focusPos = mapView.getFocusPos();
        bundle.putDouble("focusX", focusPos.getX());
        bundle.putDouble("focusY", focusPos.getY());
        bundle.putFloat("zoom", mapView.getZoom());
        bundle.putFloat("rotation", mapView.getMapRotation());
        bundle.putFloat("tilt", mapView.getTilt());
    }
}
