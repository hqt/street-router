package com.fpt.router.library.model.motorbike;

import android.os.Parcel;
import android.os.Parcelable;

import com.fpt.router.library.model.common.IWearableModel;
import com.google.android.gms.wearable.DataMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 10/11/2015.
 */
public class RouterDetailFourPoint implements IWearableModel<RouterDetailFourPoint> {
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

    @Override
    public void dataMapToModel(DataMap dataMap) {
        this.duration = dataMap.getInt("duration");
        this.distance = dataMap.getDouble("distance");
        this.startLocation = dataMap.getString("start_location");
        this.endLocation = dataMap.getString("end_location");
        this.way_point_1 = dataMap.getString("way_point_1");
        this.way_point_2 = dataMap.getString("way_point_2");


        // dummy step for calling function
        Step step = new Step();
        dataMap.putDataMapArrayList("list_step", step.listModelToDataMap(steps));

        // dummy leg for calling function
        Leg leg = new Leg();
        dataMap.putDataMapArrayList("list_leg", leg.listModelToDataMap(legs));
    }

    @Override
    public DataMap putToDataMap() {
        DataMap dataMap = new DataMap();
        dataMap.putInt("duration", duration);
        dataMap.putDouble("distance", distance);
        dataMap.putString("start_location", startLocation);
        dataMap.putString("end_location", endLocation);
        dataMap.putString("way_point_1", way_point_1);
        dataMap.putString("way_point_2", way_point_2);

        // dummy step for calling function
        Step step = new Step();
        dataMap.putDataMapArrayList("list_step", step.listModelToDataMap(steps));

        // dummy step for calling function
        Leg leg = new Leg();
        dataMap.putDataMapArrayList("list_leg", leg.listModelToDataMap(legs));

        return dataMap;
    }

    @Override
    public ArrayList<RouterDetailFourPoint> dataMapToListModel(DataMap dataMap) {
        throw new NoSuchMethodError();
    }

    @Override
    public ArrayList<DataMap> listModelToDataMap(List<RouterDetailFourPoint> items) {
        throw new NoSuchMethodError();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.duration);
        dest.writeDouble(this.distance);
        dest.writeString(this.startLocation);
        dest.writeString(this.endLocation);
        dest.writeString(this.way_point_1);
        dest.writeString(this.way_point_2);
        dest.writeTypedList(steps);
        dest.writeTypedList(legs);
    }

    protected RouterDetailFourPoint(Parcel in) {
        this.duration = in.readInt();
        this.distance = in.readDouble();
        this.startLocation = in.readString();
        this.endLocation = in.readString();
        this.way_point_1 = in.readString();
        this.way_point_2 = in.readString();
        this.steps = in.createTypedArrayList(Step.CREATOR);
        this.legs = in.createTypedArrayList(Leg.CREATOR);
    }

    public static final Parcelable.Creator<RouterDetailFourPoint> CREATOR = new Parcelable.Creator<RouterDetailFourPoint>() {
        public RouterDetailFourPoint createFromParcel(Parcel source) {
            return new RouterDetailFourPoint(source);
        }

        public RouterDetailFourPoint[] newArray(int size) {
            return new RouterDetailFourPoint[size];
        }
    };
}
