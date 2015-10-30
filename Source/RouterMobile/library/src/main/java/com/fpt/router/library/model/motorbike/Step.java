package com.fpt.router.library.model.motorbike;

import android.os.Parcel;
import android.os.Parcelable;

import com.fpt.router.library.model.common.IWearableModel;
import com.google.android.gms.wearable.DataMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 9/29/2015.
 */
public class Step implements IWearableModel<Step> {
    private String instruction;
        private String maneuver;
    private DetailLocation detailLocation;

    public Step() {

    }

    public Step(String Instruction, String Maneuver, DetailLocation DetailLocation) {

        this.instruction = Instruction;
        this.maneuver = Maneuver;
        this.detailLocation = DetailLocation;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getManeuver() {
        return maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }

    public com.fpt.router.library.model.motorbike.DetailLocation getDetailLocation() {
        return detailLocation;
    }

    public void setDetailLocation(com.fpt.router.library.model.motorbike.DetailLocation detailLocation) {
        this.detailLocation = detailLocation;
    }

    @Override
    public void dataMapToModel(DataMap dataMap) {
        this.instruction = dataMap.getString("instruction");
        this.maneuver = dataMap.getString("maneuver");
        this.detailLocation = new DetailLocation();
        this.detailLocation.dataMapToModel(dataMap.getDataMap("detail_location"));
    }

    @Override
    public DataMap putToDataMap() {
        DataMap dataMap = new DataMap();
        dataMap.putString("instruction", instruction);
        dataMap.putString("maneuver", maneuver);
        dataMap.putDataMap("detail_location", detailLocation.putToDataMap());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.instruction);
        dest.writeString(this.maneuver);
        dest.writeParcelable(this.detailLocation, 0);
    }

    protected Step(Parcel in) {
        this.instruction = in.readString();
        this.maneuver = in.readString();
        this.detailLocation = in.readParcelable(com.fpt.router.library.model.motorbike.DetailLocation.class.getClassLoader());
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
