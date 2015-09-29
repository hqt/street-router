package com.fpt.router.model.motorbike;

import java.util.ArrayList;

/**
 * Created by USER on 9/29/2015.
 */
public class leg {
    private String lEndAddress;
    private String lStartAddress;
    private detailLocation lDetailLocation;
    private ArrayList<step> lStep;
    private String lOverview_polyline;

    public leg(String lEndAddress, String lStartAddress, detailLocation lDetailLocation, ArrayList<step> lStep, String lOverview_polyline) {
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

    public detailLocation getlDetailLocation() {
        return lDetailLocation;
    }

    public void setlDetailLocation(detailLocation lDetailLocation) {
        this.lDetailLocation = lDetailLocation;
    }

    public ArrayList<step> getlStep() {
        return lStep;
    }

    public void setlStep(ArrayList<step> lStep) {
        this.lStep = lStep;
    }

    public String getlOverview_polyline() {
        return lOverview_polyline;
    }

    public void setlOverview_polyline(String lOverview_polyline) {
        this.lOverview_polyline = lOverview_polyline;
    }
}
