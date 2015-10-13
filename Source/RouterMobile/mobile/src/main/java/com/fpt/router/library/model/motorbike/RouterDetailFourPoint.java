package com.fpt.router.library.model.motorbike;

import java.io.Serializable;
import java.util.List;

/**
 * Created by asus on 10/11/2015.
 */
public class RouterDetailFourPoint implements Serializable {
    private int duration;
    private double distance;
    private String startLocation;
    private String endLocation;
    private String way_point_1;
    private String way_point_2;
    private List<Step> steps;
    private List<Leg> legs;
    public RouterDetailFourPoint(){

    }

    public RouterDetailFourPoint(int duration, double distance, String startLocation, String endLocation, String way_point_1, String way_point_2, List<Step> steps, List<Leg> legs) {
        this.duration = duration;
        this.distance = distance;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.way_point_1 = way_point_1;
        this.way_point_2 = way_point_2;
        this.steps = steps;
        this.legs = legs;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getWay_point_1() {
        return way_point_1;
    }

    public void setWay_point_1(String way_point_1) {
        this.way_point_1 = way_point_1;
    }

    public String getWay_point_2() {
        return way_point_2;
    }

    public void setWay_point_2(String way_point_2) {
        this.way_point_2 = way_point_2;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }
}
