package com.fpt.router.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpt.router.R;
import com.fpt.router.library.model.common.NotifyModel;
import com.google.android.gms.maps.model.LatLng;
import com.nutiteq.core.MapPos;
import com.nutiteq.core.MapRange;
import com.nutiteq.datasources.CompressedCacheTileDataSource;
import com.nutiteq.datasources.LocalVectorDataSource;
import com.nutiteq.datasources.NutiteqOnlineTileDataSource;
import com.nutiteq.datasources.PersistentCacheTileDataSource;
import com.nutiteq.datasources.TileDataSource;
import com.nutiteq.layers.TileLayer;
import com.nutiteq.layers.VectorTileLayer;
import com.nutiteq.projections.EPSG3857;
import com.nutiteq.projections.Projection;
import com.nutiteq.ui.MapView;
import com.nutiteq.utils.AssetUtils;
import com.nutiteq.vectortiles.MBVectorTileDecoder;
import com.nutiteq.vectortiles.MBVectorTileStyleSet;
import com.nutiteq.wrappedcommons.UnsignedCharVector;

import java.util.List;

/**
 * Created by Nguyen Trung Nam on 11/3/2015.
 */
public class MapSampleBaseFragment extends Fragment {
    protected MapView mapView;
    protected Projection baseProjection;
    protected TileLayer baseLayer;
    protected View rootView;
    protected String vectorStyleName = "nutibright"; // default style name, each style has corresponding .zip asset
    protected String vectorStyleLang = "vi"; // default map language
    protected TileDataSource vectorTileDataSource;
    protected MBVectorTileDecoder vectorTileDecoder;
    protected boolean persistentTileCache = false;
    public MapSampleBaseFragment() {
    }

    public static MapSampleBaseFragment newInstance(int position) {
        MapSampleBaseFragment fragment = new MapSampleBaseFragment();
        Bundle args = new Bundle();
        args.putSerializable("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail_nutiteq, container, false);
        com.nutiteq.utils.Log.setShowDebug(true);
        com.nutiteq.utils.Log.setShowInfo(true);
        MapView.registerLicense("XTUN3Q0ZCdzBJSXRNV0tRNG9LMFVwemozek5ieGFpd2ZBaFFOQVc2WWdMM3puS01oNGdKRHpXYmxjZm5ybWc9PQoKcHJvZHVjdHM9c2RrLWFuZHJvaWQtMy4qCnBhY2thZ2VOYW1lPWNvbS5mcHQucm91dGVyCndhdGVybWFyaz1udXRpdGVxCnVzZXJLZXk9NjJlNDNjOGY3YWNkMmQ3NTNlZmQ5NzExZTAwYTM3MTYK", getActivity().getApplicationContext());
        mapView = (MapView) rootView.findViewById(R.id.mapView);

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
        //updateBaseLayer();
        return rootView;
    }

    private void updateBaseLayer() {
        String styleAssetName = vectorStyleName + ".zip";
        boolean styleBuildings3D = false;
        if (vectorStyleName.equals("nutibright3d")) {
            styleAssetName = "nutibright.zip";
            styleBuildings3D = true;
        }
        UnsignedCharVector styleBytes = AssetUtils.loadBytes(styleAssetName);
        if (styleBytes != null){
            // Create style set
            MBVectorTileStyleSet vectorTileStyleSet = new MBVectorTileStyleSet(styleBytes);
            vectorTileDecoder = new MBVectorTileDecoder(vectorTileStyleSet);

            // Set language, language-specific texts from vector tiles will be used
            vectorTileDecoder.setStyleParameter("lang", vectorStyleLang);

            // OSM Bright style set supports choosing between 2d/3d buildings. Set corresponding parameter.
            if (styleAssetName.equals("nutibright.zip")) {
                vectorTileDecoder.setStyleParameter("buildings3d", styleBuildings3D);
                vectorTileDecoder.setStyleParameter("markers3d",styleBuildings3D ? "1" : "0");
                vectorTileDecoder.setStyleParameter("texts3d",styleBuildings3D ? "1" : "0");
            }

            // Create tile data source for vector tiles
            if (vectorTileDataSource == null) {
                vectorTileDataSource = createTileDataSource();
            }

            // Remove old base layer, create new base layer
            if (baseLayer != null) {
                mapView.getLayers().remove(baseLayer);
            }
            baseLayer = new VectorTileLayer(vectorTileDataSource, vectorTileDecoder);
            mapView.getLayers().insert(0, baseLayer);
        } else {
            Log.e("NAM:", "map style file must be in project assets: " + vectorStyleName);
        }
    }

    protected TileDataSource createTileDataSource() {
        TileDataSource vectorTileDataSource = new NutiteqOnlineTileDataSource("nutiteq.osm");

        // We don't use vectorTileDataSource directly (this would be also option),
        // but via caching to cache data locally persistently/non-persistently
        // Note that persistent cache requires WRITE_EXTERNAL_STORAGE permission
        TileDataSource cacheDataSource = vectorTileDataSource;
        if (persistentTileCache) {
            String cacheFile = getActivity().getApplicationContext().getExternalFilesDir(null)+"/mapcache.db";
            Log.i("NAM:","cacheFile = "+cacheFile);
            cacheDataSource = new PersistentCacheTileDataSource(vectorTileDataSource, cacheFile);
        } else {
            cacheDataSource = new CompressedCacheTileDataSource(vectorTileDataSource);
        }
        return cacheDataSource;
    }

}
