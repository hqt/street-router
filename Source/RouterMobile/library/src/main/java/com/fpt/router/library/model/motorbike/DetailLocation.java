package com.fpt.router.library.model.motorbike;

import com.fpt.router.library.model.common.IWearableModel;
import com.google.android.gms.wearable.DataMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 9/29/2015.
 */
public class DetailLocation implements Serializable, IWearableModel<DetailLocation> {
    private int distance;
    private int duration;
    private Location end_location;
    private Location start_location;

    public DetailLocation() {

    }

    public DetailLocation(int distance, int duration, Location end_location, Location start_location) {
        this.distance = distance;
        this.duration = duration;
        this.end_location = end_location;
        this.start_location = start_location;
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

    public Location getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Location end_location) {
        this.end_location = end_location;
    }

    public Location getStart_location() {
        return start_location;
    }

    public void setStart_location(Location start_location) {
        this.start_location = start_location;
    }

    @Override
    public void dataMapToModel(DataMap dataMap) {
        this.distance = dataMap.getInt("distance");
        this.duration = dataMap.getInt("duration");

        this.end_location = new Location();
        this.end_location.dataMapToModel(dataMap.getDataMap("end_location"));

        this.start_location = new Location();
        this.start_location.dataMapToModel(dataMap.getDataMap("start_location"));
    }

    @Override
    public DataMap putToDataMap() {
        DataMap dataMap = new DataMap();
        dataMap.putInt("distance", distance);
        dataMap.putInt("duration", duration);
        dataMap.putDataMap("end_location", end_location.putToDataMap());
        dataMap.putDataMap("start_location", start_location.putToDataMap());
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
}
