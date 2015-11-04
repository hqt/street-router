package com.fpt.router.fragment.base;

import com.fpt.router.library.model.common.NotifyModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Nguyen Trung Nam on 10/22/2015.
 */
public abstract class AbstractNutiteqMapFragment extends VectorMapBaseFragment {
    public abstract void drawCurrentLocation(Double lat, Double lng);
    public abstract List<LatLng> getFakeGPSList();
    public abstract List<NotifyModel> getNotifyList();
}
