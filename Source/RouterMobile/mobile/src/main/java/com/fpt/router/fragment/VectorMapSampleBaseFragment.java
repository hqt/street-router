package com.fpt.router.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpt.router.framework.RouterApplication;
import com.fpt.router.library.model.common.NotifyModel;
import com.google.android.gms.maps.model.LatLng;
import com.nutiteq.datasources.CompressedCacheTileDataSource;
import com.nutiteq.datasources.NutiteqOnlineTileDataSource;
import com.nutiteq.datasources.PersistentCacheTileDataSource;
import com.nutiteq.datasources.TileDataSource;
import com.nutiteq.layers.VectorTileLayer;
import com.nutiteq.utils.AssetUtils;
import com.nutiteq.vectortiles.MBVectorTileDecoder;
import com.nutiteq.vectortiles.MBVectorTileStyleSet;
import com.nutiteq.wrappedcommons.UnsignedCharVector;

import java.util.List;

/**
 * Created by Nguyen Trung Nam on 11/3/2015.
 */
public class VectorMapSampleBaseFragment extends MapSampleBaseFragment {
    protected TileDataSource vectorTileDataSource;
    protected MBVectorTileDecoder vectorTileDecoder;
    protected boolean persistentTileCache = false;

    // Style parameters
    protected String vectorStyleName = "nutibright"; // default style name, each style has corresponding .zip asset
    protected String vectorStyleLang = "vi"; // default map language

    public VectorMapSampleBaseFragment() {
    }

    public static VectorMapSampleBaseFragment newInstance(int position) {
        VectorMapSampleBaseFragment fragment = new VectorMapSampleBaseFragment();
        Bundle args = new Bundle();
        args.putSerializable("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        updateBaseLayer();
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

    // loading map offline
    protected TileDataSource createTileDataSource() {
        if (RouterApplication.dataSource != null) {
            return loadOfflineMap();
        } else {
            return loadOnlineMap();
        }
    }

    private TileDataSource loadOfflineMap() {
        Log.e("hqthao", "Load offline map");
        return RouterApplication.dataSource;
    }

    private TileDataSource loadOnlineMap() {
        Log.e("hqthao", "Load online map");

        TileDataSource vectorTileDataSource = new NutiteqOnlineTileDataSource("nutiteq.osm");

        // We don't use vectorTileDataSource directly (this would be also option),
        // but via caching to cache data locally persistently/non-persistently
        // Note that persistent cache requires WRITE_EXTERNAL_STORAGE permission
        TileDataSource cacheDataSource = vectorTileDataSource;
        if (persistentTileCache) {
            String cacheFile = getActivity().getApplicationContext().getExternalFilesDir(null) + "/mapcache.db";
            Log.i("hqthao", "cacheFile = " + cacheFile);
            cacheDataSource = new PersistentCacheTileDataSource(vectorTileDataSource, cacheFile);
        } else {
            cacheDataSource = new CompressedCacheTileDataSource(vectorTileDataSource);
        }
        return cacheDataSource;
    }

}