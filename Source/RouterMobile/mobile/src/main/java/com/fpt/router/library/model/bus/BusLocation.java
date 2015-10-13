package com.fpt.router.library.model.bus;

import java.io.Serializable;

/**
 * Created by asus on 10/12/2015.
 */
public class BusLocation implements Serializable {
    private double latitude;
    private double longitude;
    private String address;

    public BusLocation() {
    }

    public BusLocation(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
