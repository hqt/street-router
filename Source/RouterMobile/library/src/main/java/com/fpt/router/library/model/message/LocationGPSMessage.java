package com.fpt.router.library.model.message;

import com.fpt.router.library.model.common.Location;

/**
 * Created by Nguyen Trung Nam on 10/16/2015.
 */
public class LocationGPSMessage {
    public Location location;

    public LocationGPSMessage(Location location) {
        this.location = location;
    }
}
