package com.fpt.router.library.model.message;

import android.location.Location;

/**
 * Created by Nguyen Trung Nam on 10/15/2015.
 */
public class LocationMessage {
    public Location location;

    public LocationMessage(Location location) {
        this.location = location;
    }
}
