package com.fpt.router.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Nguyen Trung Nam on 10/22/2015.
 */
public abstract class AbstractMapFragment extends Fragment {
    public abstract  void drawCurrentLocation(Double lat, Double lng);

}
