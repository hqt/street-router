package com.fpt.router.library.model.motorbike;

import com.fpt.router.library.model.common.IWearableModel;
import com.google.android.gms.wearable.DataMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 9/29/2015.
 */
public class Leg  implements Serializable, IWearableModel<Leg> {

    private String EndAddress;
    private String StartAddress;
    private DetailLocation DetailLocation;
    private List<Step> Step;
    private String Overview_polyline;

    public Leg() {

    }

    public Leg(String endAddress, String startAddress, com.fpt.router.library.model.motorbike.DetailLocation detailLocation, ArrayList<com.fpt.router.library.model.motorbike.Step> step, String overview_polyline) {
        EndAddress = endAddress;
        StartAddress = startAddress;
        DetailLocation = detailLocation;
        Step = step;
        Overview_polyline = overview_polyline;
    }

    public String getEndAddress() {
        return EndAddress;
    }

    public void setEndAddress(String endAddress) {
        EndAddress = endAddress;
    }

    public String getStartAddress() {
        return StartAddress;
    }

    public void setStartAddress(String startAddress) {
        StartAddress = startAddress;
    }

    public com.fpt.router.library.model.motorbike.DetailLocation getDetailLocation() {
        return DetailLocation;
    }

    public void setDetailLocation(com.fpt.router.library.model.motorbike.DetailLocation detailLocation) {
        DetailLocation = detailLocation;
    }

    public List<com.fpt.router.library.model.motorbike.Step> getStep() {
        return Step;
    }

    public void setStep(ArrayList<com.fpt.router.library.model.motorbike.Step> step) {
        Step = step;
    }

    public String getOverview_polyline() {
        return Overview_polyline;
    }

    public void setOverview_polyline(String overview_polyline) {
        Overview_polyline = overview_polyline;
    }

    @Override
    public void dataMapToModel(DataMap dataMap) {
        this.StartAddress = dataMap.getString("start_location");
        this.EndAddress = dataMap.getString("end_location");

        // dummy step for calling function
        Step step = new Step();
        dataMap.putDataMapArrayList("list_step", step.listModelToDataMap(Step));

        this.Overview_polyline = dataMap.getString("overview_polyline");
        this.DetailLocation = new DetailLocation();
        this.DetailLocation.dataMapToModel(dataMap.getDataMap("detail_location"));
    }

    @Override
    public DataMap putToDataMap() {
        DataMap dataMap = new DataMap();

        dataMap.putString("start_location", StartAddress);
        dataMap.putString("end_location", EndAddress);

        // dummy step for calling function
        Step step = new Step();
        dataMap.putDataMapArrayList("list_step", step.listModelToDataMap(Step));

        dataMap.putString("overview_polyline", Overview_polyline);
        dataMap.putDataMap("detail_location", DetailLocation.putToDataMap());

        return dataMap;
    }

    @Override
    public ArrayList<Leg> dataMapToListModel(DataMap dataMap) {
        ArrayList<DataMap> dataMaps = dataMap.getDataMapArrayList("list_leg");
        ArrayList<Leg> res = new ArrayList<Leg>();
        for (DataMap dataMapItem : dataMaps) {
            Leg leg = new Leg();
            leg.dataMapToModel(dataMapItem);
            res.add(leg);
        }
        return res;
    }

    @Override
    public ArrayList<DataMap> listModelToDataMap(List<Leg> items) {
        ArrayList<DataMap> res = new ArrayList<DataMap>();
        if (items == null) return res;

        for (Leg leg : items) {
            DataMap dataMapItem = leg.putToDataMap();
            res.add(dataMapItem);
        }
        return res;
    }
}
