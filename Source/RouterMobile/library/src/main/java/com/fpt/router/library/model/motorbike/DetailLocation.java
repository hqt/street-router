package com.fpt.router.library.model.motorbike;

import android.os.Parcel;
import android.os.Parcelable;

import com.fpt.router.library.model.common.IWearableModel;
import com.fpt.router.library.model.common.Location;
import com.google.android.gms.wearable.DataMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 9/29/2015.
 */
public class DetailLocation implements IWearableModel<DetailLocation> {
    private int distance;
    private String distanceText;
    private int duration;
    private Location endLocation;
    private Location startLocation;

    public DetailLocation() {

    }

    public DetailLocation(int distance, int duration, Location endLocation, Location start_location) {
        this.distance = distance;
        this.duration = duration;
        this.endLocation = endLocation;
        this.startLocation = start_location;
    }

    public String getDistanceText() {
        return distanceText;
    }

    public void setDistanceText(String distanceText) {
        this.distanceText = distanceText;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
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
        this.distance = dataMap.getInt("distance");
        this.duration = dataMap.getInt("duration");

        this.endLocation = new Location();
        this.endLocation.dataMapToModel(dataMap.getDataMap("end_location"));

        this.startLocation = new Location();
        this.startLocation.dataMapToModel(dataMap.getDataMap("start_location"));
    }

    @Override
    public DataMap putToDataMap() {
        DataMap dataMap = new DataMap();
        dataMap.putInt("distance", distance);
        dataMap.putInt("duration", duration);
        dataMap.putDataMap("end_location", endLocation.putToDataMap());
        dataMap.putDataMap("start_location", startLocation.putToDataMap());
        return dataMap;
    }

    @Override
    public ArrayList<DetailLocation> dataMapToListModel(DataMap dataMap) {
        throw new NoSuchMethodError();
    }

    @Override
    public ArrayList<DataMap> listModelToDataMap(List<DetailLocation> items) {
        throw new NoSuchMethodError();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.distance);
        dest.writeInt(this.duration);
        dest.writeParcelable(this.endLocation, 0);
        dest.writeParcelable(this.startLocation, 0);
    }

    protected DetailLocation(Parcel in) {
        this.distance = in.readInt();
        this.duration = in.readInt();
        this.endLocation = in.readParcelable(Location.class.getClassLoader());
        this.startLocation = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Parcelable.Creator<DetailLocation> CREATOR = new Parcelable.Creator<DetailLocation>() {
        public DetailLocation createFromParcel(Parcel source) {
            return new DetailLocation(source);
        }

        public DetailLocation[] newArray(int size) {
            return new DetailLocation[size];
        }
    };
}
