package com.fpt.router.artifacter.model.helper;

import com.fpt.router.artifacter.config.Config;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/5/15.
 */
public class Location {
    public double latitude;
    public double longitude;

    public Location() {

    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "latitude: " + latitude + " longitude: " + longitude;
    }
    @Override
    public int hashCode() {
        return Long.valueOf(Double.doubleToLongBits(latitude)).hashCode() +
                Long.valueOf(Double.doubleToLongBits(latitude)).hashCode();
    }

    @Override
    public boolean equals(Object l2) {
        if (this == l2) return true;
        Location that = (Location) l2;
        if (Math.abs(this.latitude - that.latitude) <= Config.EPS) {
            if (Math.abs(this.longitude - that.longitude) <= Config.EPS) {
                return true;
            }
        }
        return false;
    }
}