package com.fpt.router.fragment.base;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.framework.OrientationManager;
import com.fpt.router.framework.OrientationManager.OnChangedListener;
import com.fpt.router.widget.LockableListView;
import com.fpt.router.widget.SlidingUpPanelLayout;
import com.fpt.router.widget.SlidingUpPanelLayout.PanelSlideListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.nutiteq.core.MapPos;
import com.nutiteq.core.MapRange;
import com.nutiteq.core.MapVec;
import com.nutiteq.datasources.CompressedCacheTileDataSource;
import com.nutiteq.datasources.NutiteqOnlineTileDataSource;
import com.nutiteq.datasources.PersistentCacheTileDataSource;
import com.nutiteq.datasources.TileDataSource;
import com.nutiteq.layers.TileLayer;
import com.nutiteq.layers.VectorTileLayer;
import com.nutiteq.projections.EPSG3857;
import com.nutiteq.projections.Projection;
import com.nutiteq.ui.MapView;
import com.nutiteq.utils.AssetUtils;
import com.nutiteq.vectorelements.Marker;
import com.nutiteq.vectorelements.NMLModel;
import com.nutiteq.vectortiles.MBVectorTileDecoder;
import com.nutiteq.vectortiles.MBVectorTileStyleSet;
import com.nutiteq.wrappedcommons.UnsignedCharVector;

/**
 * Created by Nguyen Trung Nam on 11/3/2015.
 */
public class NutiteqMapBaseFragment extends Fragment implements OnChangedListener,
        PanelSlideListener{
    protected MapView mapView;
    protected Projection baseProjection;
    protected TileLayer baseLayer;
    protected View rootView;
    protected String vectorStyleName = "nutibright"; // default style name, each style has corresponding .zip asset
    protected String vectorStyleLang = "vi"; // default map language
    protected TileDataSource vectorTileDataSource;
    protected MBVectorTileDecoder vectorTileDecoder;
    protected boolean persistentTileCache = false;
    FloatingActionButton fab_gps_off;
    FloatingActionButton fab_gps_on;
    FloatingActionButton fab_compass;
    protected boolean GPS_ON_FLAG = false;
    protected boolean COMPASS_FLAG = false;
    private OrientationManager mOrientationManager;
    protected NMLModel model;
    protected Marker marker;
    protected TextView txt_cua_nam;
    protected LockableListView mListView;
    protected SlidingUpPanelLayout mSlidingUpPanelLayout;


    protected View mTransparentHeaderView;
    private View mTransparentView;
    private View mSpaceView;
    private Toolbar toolbar;

    public NutiteqMapBaseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_nutiteq, container, false);
        com.nutiteq.utils.Log.setShowDebug(true);
        com.nutiteq.utils.Log.setShowInfo(true);
        MapView.registerLicense("XTUN3Q0ZCdzBJSXRNV0tRNG9LMFVwemozek5ieGFpd2ZBaFFOQVc2WWdMM3puS01oNGdKRHpXYmxjZm5ybWc9PQoKcHJvZHVjdHM9c2RrLWFuZHJvaWQtMy4qCnBhY2thZ2VOYW1lPWNvbS5mcHQucm91dGVyCndhdGVybWFyaz1udXRpdGVxCnVzZXJLZXk9NjJlNDNjOGY3YWNkMmQ3NTNlZmQ5NzExZTAwYTM3MTYK", getActivity().getApplicationContext());
        mapView = (MapView) rootView.findViewById(R.id.mapView);

        fab_gps_off = (FloatingActionButton) rootView.findViewById(R.id.fab_gps_off);
        fab_gps_on = (FloatingActionButton) rootView.findViewById(R.id.fab_gps_on);
        fab_compass = (FloatingActionButton) rootView.findViewById(R.id.fab_compass);

        fab_gps_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab_gps_off.setVisibility(View.GONE);
                fab_gps_on.setVisibility(View.VISIBLE);
                GPS_ON_FLAG = true;
            }
        });

        fab_gps_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab_gps_on.setVisibility(View.GONE);
                fab_compass.setVisibility(View.VISIBLE);
                COMPASS_FLAG = true;
            }
        });

        fab_compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab_compass.setVisibility(View.GONE);
                fab_gps_off.setVisibility(View.VISIBLE);
                GPS_ON_FLAG = false;
                COMPASS_FLAG = false;
            }
        });

        SensorManager sensorManager =
                (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        mOrientationManager = new OrientationManager(sensorManager);

        mOrientationManager.addOnChangedListener(this);
        mOrientationManager.start();

        // Set the base projection, that will be used for most MapView, MapEventListener and Options methods
        baseProjection = new EPSG3857();
        mapView.getOptions().setBaseProjection(baseProjection);

        // General options
        mapView.getOptions().setTileDrawSize(256);
//        mapView.getOptions().setTileThreadPoolSize(4);

        // Review following and change if needed
        mapView.getOptions().setRotatable(true);
        mapView.getOptions().setZoomRange(new MapRange(0, 18));

        Log.d("NAM:", "autoconfigured DPI=" + mapView.getOptions().getDPI());

        // Set default location
//      mapView.setFocusPos(baseProjection.fromWgs84(new MapPos(24.650415, 59.420773)), 0); // tallinn
//      mapView.setFocusPos(baseProjection.fromWgs84(new MapPos(19.04468, 47.4965)), 0); // budapest
        mapView.setFocusPos(baseProjection.fromWgs84(new MapPos(13.38933, 52.51704)), 0); // berlin
        mapView.setZoom(2, 0);
        mapView.setMapRotation(0, 0);
        mapView.setTilt(90, 0);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        mListView = (LockableListView) rootView.findViewById(android.R.id.list);
        mListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        mSlidingUpPanelLayout = (SlidingUpPanelLayout) rootView.findViewById(R.id.slidingLayout);
        mSlidingUpPanelLayout.setEnableDragViewTouchEvents(true);

        int mapHeight = getResources().getDimensionPixelSize(R.dimen.map_height);
        int panelHeight = getResources().getDimensionPixelSize(R.dimen.panel_height);
        /*int panelHeight = 50;*/
        mSlidingUpPanelLayout.setPanelHeight(panelHeight); // you can use different height here
        mSlidingUpPanelLayout.setScrollableView(mListView, mapHeight);

        mSlidingUpPanelLayout.setPanelSlideListener(this);

        // transparent view at the top of ListView
        mTransparentView = rootView.findViewById(R.id.transparentView);

        // init header view for ListView
        mTransparentHeaderView = inflater.inflate(R.layout.transparent_header_view, mListView, false);
        mSpaceView = mTransparentHeaderView.findViewById(R.id.space);

        collapseMap();

        mSlidingUpPanelLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mSlidingUpPanelLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mSlidingUpPanelLayout.onPanelDragged(0);
            }
        });
        return rootView;
    }

    @Override
    public void onOrientationChanged(OrientationManager orientationManager) {
        float azimut = orientationManager.getHeading(); // orientation contains: azimut, pitch and roll
        //System.out.println(azimut);
        if(COMPASS_FLAG) {
            mapView.setTilt(30f, 0.5f);
            mapView.setZoom(24f, 05f);
            mapView.setMapRotation(360 - azimut, 0f);

        }
        /*if (marker != null) {
            marker.setRotation(360 - azimut);
        }*/
        if(model != null) {
            model.setRotation(new MapVec(0, 0, 1), 360-azimut);
        }


    }

    @Override
    public void onAccuracyChanged(OrientationManager orientationManager) {

    }

    @Override
    public void onPanelSlide(View view, float v) {
    }

    @Override
    public void onPanelCollapsed(View view) {
        expandMap();
    }

    @Override
    public void onPanelExpanded(View view) {
        collapseMap();
    }

    @Override
    public void onPanelAnchored(View view) {

    }

    private void collapseMap() {
        mSpaceView.setVisibility(View.VISIBLE);
        mTransparentView.setVisibility(View.GONE);

        mListView.setScrollingEnabled(true);
    }

    private void expandMap() {
        mSpaceView.setVisibility(View.GONE);
        mTransparentView.setVisibility(View.INVISIBLE);

        mListView.setScrollingEnabled(false);
    }
}
