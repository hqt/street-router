package com.fpt.router.library.model.motorbike;

import java.io.Serializable;

/**
 * Created by USER on 9/29/2015.
 */
public class Step implements Serializable {
    private String Instruction;
    private String Maneuver;
    private DetailLocation DetailLocation;

    public Step(String Instruction, String Maneuver, DetailLocation DetailLocation) {

        this.Instruction = Instruction;
        this.Maneuver = Maneuver;
        this.DetailLocation = DetailLocation;
    }

    public String getInstruction() {
        return Instruction;
    }

    public void setInstruction(String instruction) {
        Instruction = instruction;
    }

    public String getManeuver() {
        return Maneuver;
    }

    public void setManeuver(String maneuver) {
        Maneuver = maneuver;
    }

    public com.fpt.router.library.model.motorbike.DetailLocation getDetailLocation() {
        return DetailLocation;
    }

    public void setDetailLocation(com.fpt.router.library.model.motorbike.DetailLocation detailLocation) {
        DetailLocation = detailLocation;
    }
}
