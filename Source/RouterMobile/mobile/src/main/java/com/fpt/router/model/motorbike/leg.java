package com.fpt.router.model.motorbike;

import java.util.ArrayList;

/**
 * Created by USER on 9/29/2015.
 */
public class Leg {
    private String lEndAddress;
    private String lStartAddress;
    private DetailLocation lDetailLocation;
    private ArrayList<Step> lStep;
    private String lOverview_polyline;

    public Leg(String lEndAddress, String lStartAddress, DetailLocation lDetailLocation, ArrayList<Step> lStep, String lOverview_polyline) {
        this.lEndAddress = lEndAddress;
        this.lStartAddress = lStartAddress;
        this.lDetailLocation = lDetailLocation;
        this.lStep = lStep;
        this.lOverview_polyline = lOverview_polyline;
    }

    public String getlEndAddress() {
        return lEndAddress;
    }

    public void setlEndAddress(String lEndAddress) {
        this.lEndAddress = lEndAddress;
    }

    public String getlStartAddress() {
        return lStartAddress;
    }

    public void setlStartAddress(String lStartAddress) {
        this.lStartAddress = lStartAddress;
    }

    public DetailLocation getlDetailLocation() {
        return lDetailLocation;
    }

    public void setlDetailLocation(DetailLocation lDetailLocation) {
        this.lDetailLocation = lDetailLocation;
    }

    public ArrayList<Step> getlStep() {
        return lStep;
    }

    public void setlStep(ArrayList<Step> lStep) {
        this.lStep = lStep;
    }

    public String getlOverview_polyline() {
        return lOverview_polyline;
    }

    public void setlOverview_polyline(String lOverview_polyline) {
        this.lOverview_polyline = lOverview_polyline;
    }
}
