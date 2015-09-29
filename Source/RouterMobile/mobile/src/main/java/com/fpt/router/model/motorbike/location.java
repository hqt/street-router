package com.fpt.router.model.motorbike;

/**
 * Created by USER on 9/29/2015.
 */
public class location {
    private double latitude;
    private double longitude;

    public location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

