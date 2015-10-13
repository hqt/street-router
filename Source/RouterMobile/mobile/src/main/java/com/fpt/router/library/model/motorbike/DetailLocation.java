package com.fpt.router.library.model.motorbike;

import java.io.Serializable;

/**
 * Created by USER on 9/29/2015.
 */
public class DetailLocation implements Serializable {
    private int distance;
    private int duration;
    private Location end_location;
    private Location start_location;

    public DetailLocation(int distance, int duration, Location end_location, Location start_location) {
        this.distance = distance;
        this.duration = duration;
        this.end_location = end_location;
        this.start_location = start_location;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Location getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Location end_location) {
        this.end_location = end_location;
    }

    public Location getStart_location() {
        return start_location;
    }

    public void setStart_location(Location start_location) {
        this.start_location = start_location;
    }
}
