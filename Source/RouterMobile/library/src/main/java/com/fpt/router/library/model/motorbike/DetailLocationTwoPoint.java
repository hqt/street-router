package com.fpt.router.library.model.motorbike;

import android.os.Parcel;
import android.os.Parcelable;

import com.fpt.router.library.model.common.IWearableModel;
import com.google.android.gms.wearable.DataMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 10/12/2015.
 */
public class DetailLocationTwoPoint implements IWearableModel<DetailLocationTwoPoint> {
    private String distance;
    private String duration;
    private Location endLocation;
    private Location startLocation;

    public DetailLocationTwoPoint() {
    }

    public DetailLocationTwoPoint(String distance, String duration, Location end_location, Location startLocation) {
        this.distance = distance;
        this.duration = duration;
        this.endLocation = end_location;
        this.startLocation = startLocation;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    @Override
    public void dataMapToModel(DataMap dataMap) {
        this.distance = dataMap.getString("distance");
        this.duration = dataMap.getString("duration");

        this.endLocation = new Location();
        this.endLocation.dataMapToModel(dataMap.getDataMap("end_location"));

        this.startLocation = new Location();
        this.startLocation.dataMapToModel(dataMap.getDataMap("start_location"));

    }

    @Override
    public DataMap putToDataMap() {
        DataMap dataMap = new DataMap();
        dataMap.putString("distance",distance);
        dataMap.putString("duration", duration);
        dataMap.putDataMap("end_location", endLocation.putToDataMap());
        dataMap.putDataMap("start_location", startLocation.putToDataMap());
        return dataMap;
    }

    @Override
    public ArrayList<DetailLocationTwoPoint> dataMapToListModel(DataMap dataMap) {
        throw new NoSuchMethodError();
    }

    @Override
    public ArrayList<DataMap> listModelToDataMap(List<DetailLocationTwoPoint> items) {
        throw new NoSuchMethodError();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.distance);
        dest.writeString(this.duration);
        dest.writeParcelable(this.endLocation, 0);
        dest.writeParcelable(this.startLocation, 0);
    }

    protected DetailLocationTwoPoint(Parcel in) {
        this.distance = in.readString();
        this.duration = in.readString();
        this.endLocation = in.readParcelable(Location.class.getClassLoader());
        this.startLocation = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Parcelable.Creator<DetailLocationTwoPoint> CREATOR = new Parcelable.Creator<DetailLocationTwoPoint>() {
        public DetailLocationTwoPoint createFromParcel(Parcel source) {
            return new DetailLocationTwoPoint(source);
        }

        public DetailLocationTwoPoint[] newArray(int size) {
            return new DetailLocationTwoPoint[size];
        }
    };
}
