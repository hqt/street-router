package com.fpt.router.library.model.common;

import android.location.*;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nguyen Trung Nam on 10/30/2015.
 */
public class NotifyModel {
    public Location location;
    public String smallTittle;
    public String longTittle;
    public String smallMessage;
    public String longMessage;

    public NotifyModel(Location location, String smallTittle, String longTittle, String smallMessage, String longMessage) {
        this.location = location;
        this.smallTittle = smallTittle;
        this.longTittle = longTittle;
        this.smallMessage = smallMessage;
        this.longMessage = longMessage;
    }
}
