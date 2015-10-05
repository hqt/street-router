package com.fpt.router.model.motorbike;

/**
 * Created by USER on 9/29/2015.
 */
public class Step {
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

    public com.fpt.router.model.motorbike.DetailLocation getDetailLocation() {
        return DetailLocation;
    }

    public void setDetailLocation(com.fpt.router.model.motorbike.DetailLocation detailLocation) {
        DetailLocation = detailLocation;
    }
}
