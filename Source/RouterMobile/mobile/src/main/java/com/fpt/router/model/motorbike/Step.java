package com.fpt.router.model.motorbike;

/**
 * Created by USER on 9/29/2015.
 */
public class Step {
    private String sInstruction;
    private String sManeuver;
    private DetailLocation sDetailLocation;

    public Step(String sInstruction, String sManeuver, DetailLocation sDetailLocation) {

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

    public DetailLocation getsLocation() {
        return sDetailLocation;
    }

    public void setsLocation(DetailLocation sDetailLocation) {
        this.sDetailLocation = sDetailLocation;
    }
}
