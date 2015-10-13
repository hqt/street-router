package com.fpt.router.library.model.motorbike;

import java.io.Serializable;

/**
 * Created by asus on 10/12/2015.
 */
public class DetailLocationTwoPoint implements Serializable {
    private String distance;
    private String duration;
    private Location end_location;
    private Location start_location;

    public DetailLocationTwoPoint() {
    }

    public DetailLocationTwoPoint(String distance, String duration, Location end_location, Location start_location) {
        this.distance = distance;
        this.duration = duration;
        this.end_location = end_location;
        this.start_location = start_location;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
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
