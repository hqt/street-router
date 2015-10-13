package com.fpt.router.library.model.motorbike;

import com.fpt.router.library.model.IWearableModel;
import com.google.android.gms.wearable.DataMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 9/29/2015.
 */
public class Step implements Serializable, IWearableModel<Step> {
    private String Instruction;
    private String Maneuver;
    private DetailLocation DetailLocation;

    public Step() {

    }

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

    @Override
    public void dataMapToModel(DataMap dataMap) {
        this.Instruction = dataMap.getString("instruction");
        this.Maneuver = dataMap.getString("maneuver");
        this.DetailLocation = new DetailLocation();
        this.DetailLocation.dataMapToModel(dataMap.getDataMap("detail_location"));
    }

    @Override
    public DataMap putToDataMap() {
        DataMap dataMap = new DataMap();
        dataMap.putString("instruction", Instruction);
        dataMap.putString("maneuver", Maneuver);
        dataMap.putDataMap("detail_location", DetailLocation.putToDataMap());
        return  dataMap;
    }

    @Override
    public ArrayList<Step> dataMapToListModel(DataMap dataMap) {
        ArrayList<DataMap> dataMaps = dataMap.getDataMapArrayList("list_step");
        ArrayList<Step> res = new ArrayList<Step>();
        for (DataMap dataMapItem : dataMaps) {
            Step step = new Step();
            step.dataMapToModel(dataMapItem);
            res.add(step);
        }
        return res;
    }

    @Override
    public ArrayList<DataMap> listModelToDataMap(List<Step> items) {
        ArrayList<DataMap> res = new ArrayList<DataMap>();
        if (items == null) return res;

        for (Step step : items) {
            DataMap dataMapItem = step.putToDataMap();
            res.add(dataMapItem);
        }
        return res;
    }
}
