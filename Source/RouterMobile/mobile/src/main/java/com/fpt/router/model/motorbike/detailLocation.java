package com.fpt.router.model.motorbike;

/**
 * Created by USER on 9/29/2015.
 */
public class detailLocation {
    private int distance;
    private int duration;
    private location end_location;
    private location start_location;

    public detailLocation(int distance, int duration, location end_location, location start_location) {
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

    public location getEnd_location() {
        return end_location;
    }

    public void setEnd_location(location end_location) {
        this.end_location = end_location;
    }

    public location getStart_location() {
        return start_location;
    }

    public void setStart_location(location start_location) {
        this.start_location = start_location;
    }
}
