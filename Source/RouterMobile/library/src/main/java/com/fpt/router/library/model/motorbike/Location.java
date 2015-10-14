package com.fpt.router.library.model.motorbike;

import android.os.Parcel;
import android.os.Parcelable;

import com.fpt.router.library.model.common.IWearableModel;
import com.google.android.gms.wearable.DataMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 9/29/2015.
 */
public class Location implements IWearableModel<Location> {
    private double latitude;
    private double longitude;

    public Location() {

    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public void dataMapToModel(DataMap dataMap) {
        this.latitude = dataMap.getDouble("latitude");
        this.longitude = dataMap.getDouble("longitude");
    }

    @Override
    public DataMap putToDataMap() {
        DataMap map = new DataMap();
        map.putDouble("latitude", latitude);
        map.putDouble("longitude", longitude);
        return map;
    }

    @Override
    public ArrayList<Location> dataMapToListModel(DataMap dataMap) {
        throw new NoSuchMethodError();
    }

    @Override
    public ArrayList<DataMap> listModelToDataMap(List<Location> items) {
        throw new NoSuchMethodError();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    protected Location(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}

