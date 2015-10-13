package com.fpt.router.library.model.motorbike;

import com.fpt.router.library.model.IWearableModel;
import com.google.android.gms.wearable.DataMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 10/11/2015.
 */
public class RouterDetailTwoPoint implements Serializable, IWearableModel<RouterDetailTwoPoint> {
    private int duration;
    private double distance;
    private String startLocation;
    private String endLocation;
    private List<Step> steps;
    private String overview_polyline;
    private DetailLocation detailLocation;

    public RouterDetailTwoPoint(){

    }

    public RouterDetailTwoPoint(int duration, double distance, String startLocation, String endLocation, List<Step> steps,String overview_polyline,DetailLocation detailLocation) {
        this.duration = duration;
        this.distance = distance;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.steps = steps;
        this.overview_polyline = overview_polyline;
        this.detailLocation = detailLocation;
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

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public String getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(String overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public DetailLocation getDetailLocation() {
        return detailLocation;
    }

    public void setDetailLocation(DetailLocation detailLocation) {
        this.detailLocation = detailLocation;
    }

    @Override
    public void dataMapToModel(DataMap dataMap) {
        this.duration = dataMap.getInt("duration");
        this.distance = dataMap.getDouble("distance");
        this.startLocation = dataMap.getString("start_location");
        this.endLocation = dataMap.getString("end_location");

        // dummy step for calling function
        Step step = new Step();
        dataMap.putDataMapArrayList("list_step", step.listModelToDataMap(steps));

        this.overview_polyline = dataMap.getString("overview_polyline");
        this.detailLocation = new DetailLocation();
        this.detailLocation.dataMapToModel(dataMap.getDataMap("detail_location"));
    }

    @Override
    public DataMap putToDataMap() {
        DataMap dataMap = new DataMap();
        dataMap.putInt("duration", duration);
        dataMap.putDouble("distance", distance);
        dataMap.putString("start_location", startLocation);
        dataMap.putString("end_location", endLocation);

        // dummy step for calling function
        Step step = new Step();
        dataMap.putDataMapArrayList("list_step", step.listModelToDataMap(steps));

        dataMap.putString("overview_polyline", overview_polyline);
        dataMap.putDataMap("detail_location", detailLocation.putToDataMap());

        return dataMap;
    }

    @Override
    public ArrayList<RouterDetailTwoPoint> dataMapToListModel(DataMap dataMap) {
        throw new NoSuchMethodError();
    }

    @Override
    public ArrayList<DataMap> listModelToDataMap(List<RouterDetailTwoPoint> items) {
        throw new NoSuchMethodError();
    }
}
