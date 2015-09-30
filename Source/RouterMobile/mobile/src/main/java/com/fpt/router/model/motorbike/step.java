package com.fpt.router.model.motorbike;

/**
 * Created by USER on 9/29/2015.
 */
public class step {
    private String sInstruction;
    private String sManeuver;
    private detailLocation sDetailLocation;

    public step(String sInstruction, String sManeuver, detailLocation sDetailLocation) {

        this.sInstruction = sInstruction;
        this.sManeuver = sManeuver;
        this.sDetailLocation = sDetailLocation;
    }

    public String getsInstruction() {
        return sInstruction;
    }

    public void setsInstruction(String sInstruction) {
        this.sInstruction = sInstruction;
    }

    public String getsManeuver() {
        return sManeuver;
    }

    public void setsManeuver(String sManeuver) {
        this.sManeuver = sManeuver;
    }

    public detailLocation getsLocation() {
        return sDetailLocation;
    }

    public void setsLocation(detailLocation sDetailLocation) {
        this.sDetailLocation = sDetailLocation;
    }
}
