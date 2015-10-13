package com.fpt.router.library.model.motorbike;

import com.fpt.router.library.model.IWearableModel;
import com.google.android.gms.wearable.DataMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 10/12/2015.
 */
public class DetailLocationTwoPoint implements Serializable, IWearableModel<DetailLocationTwoPoint> {
    private String distance;
    private String duration;
    private Location end_location;
    private Location start_location;

    public DetailLocationTwoPoint() {
    }

    public DetailLocationTwoPoint(String distance, String duration, Location end_location, Location start_location) {
        this.distance = distance;
        this.duration = duration;
        this.end_location = end_location;
        this.start_location = start_location;
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
        this.distance = dataMap.getString("distance");
        this.duration = dataMap.getString("duration");

        this.end_location = new Location();
        this.end_location.dataMapToModel(dataMap.getDataMap("end_location"));

        this.start_location = new Location();
        this.start_location.dataMapToModel(dataMap.getDataMap("start_location"));

    }

    @Override
    public DataMap putToDataMap() {
        DataMap dataMap = new DataMap();
        dataMap.putString("distance",distance);
        dataMap.putString("duration", duration);
        dataMap.putDataMap("end_location", end_location.putToDataMap());
        dataMap.putDataMap("start_location", start_location.putToDataMap());
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
}
