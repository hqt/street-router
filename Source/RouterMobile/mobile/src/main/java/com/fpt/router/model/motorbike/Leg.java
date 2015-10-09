package com.fpt.router.model.motorbike;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by USER on 9/29/2015.
 */
public class Leg  implements Serializable{

    private String EndAddress;
    private String StartAddress;
    private DetailLocation DetailLocation;
    private ArrayList<Step> Step;
    private String Overview_polyline;

    public Leg(String endAddress, String startAddress, com.fpt.router.model.motorbike.DetailLocation detailLocation, ArrayList<com.fpt.router.model.motorbike.Step> step, String overview_polyline) {
        EndAddress = endAddress;
        StartAddress = startAddress;
        DetailLocation = detailLocation;
        Step = step;
        Overview_polyline = overview_polyline;
    }



    public String getEndAddress() {
        return EndAddress;
    }

    public void setEndAddress(String endAddress) {
        EndAddress = endAddress;
    }

    public String getStartAddress() {
        return StartAddress;
    }

    public void setStartAddress(String startAddress) {
        StartAddress = startAddress;
    }

    public com.fpt.router.model.motorbike.DetailLocation getDetailLocation() {
        return DetailLocation;
    }

    public void setDetailLocation(com.fpt.router.model.motorbike.DetailLocation detailLocation) {
        DetailLocation = detailLocation;
    }

    public ArrayList<com.fpt.router.model.motorbike.Step> getStep() {
        return Step;
    }

    public void setStep(ArrayList<com.fpt.router.model.motorbike.Step> step) {
        Step = step;
    }

    public String getOverview_polyline() {
        return Overview_polyline;
    }

    public void setOverview_polyline(String overview_polyline) {
        Overview_polyline = overview_polyline;
    }
}
