package com.fpt.router.library.model.motorbike;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.fpt.router.library.model.common.IWearableModel;
import com.google.android.gms.wearable.DataMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 9/29/2015.
 */
public class Leg  implements IWearableModel<Leg> {

    private String endAddress;
    private String startAddress;
    private DetailLocation detailLocation;
    private List<Step> steps;
    private String Overview_polyline;

    public Leg() {

    }

    public Leg(String endAddress, String startAddress, com.fpt.router.library.model.motorbike.DetailLocation detailLocation, ArrayList<com.fpt.router.library.model.motorbike.Step> steps, String overview_polyline) {
        this.endAddress = endAddress;
        this.startAddress = startAddress;
        this.detailLocation = detailLocation;
        this.steps = steps;
        Overview_polyline = overview_polyline;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public com.fpt.router.library.model.motorbike.DetailLocation getDetailLocation() {
        return detailLocation;
    }

    public void setDetailLocation(com.fpt.router.library.model.motorbike.DetailLocation detailLocation) {
        this.detailLocation = detailLocation;
    }

    public List<com.fpt.router.library.model.motorbike.Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<com.fpt.router.library.model.motorbike.Step> steps) {
        this.steps = steps;
    }

    public String getOverview_polyline() {
        return Overview_polyline;
    }

    public void setOverview_polyline(String overview_polyline) {
        Overview_polyline = overview_polyline;
    }

    @Override
    public void dataMapToModel(DataMap dataMap) {
        this.startAddress = dataMap.getString("start_location");
        this.endAddress = dataMap.getString("end_location");

        // dummy step for calling function
        Step step = new Step();
        this.steps = step.dataMapToListModel(dataMap);
        // dataMap.putDataMapArrayList("list_step", step.listModelToDataMap(steps));

        this.Overview_polyline = dataMap.getString("overview_polyline");
        this.detailLocation = new DetailLocation();
        this.detailLocation.dataMapToModel(dataMap.getDataMap("detail_location"));
    }

    @Override
    public DataMap putToDataMap() {
        DataMap dataMap = new DataMap();
        dataMap.putString("start_location", startAddress);
        dataMap.putString("end_location", endAddress);

        // dummy step for calling function
        Step step = new Step();
        dataMap.putDataMapArrayList("list_step", step.listModelToDataMap(steps));

        dataMap.putString("overview_polyline", Overview_polyline);
        dataMap.putDataMap("detail_location", detailLocation.putToDataMap());

        return dataMap;
    }

    @Override
    public ArrayList<Leg> dataMapToListModel(DataMap dataMap) {
        ArrayList<DataMap> dataMaps = dataMap.getDataMapArrayList("list_leg");
        ArrayList<Leg> res = new ArrayList<Leg>();
        int count = 0;
        for (DataMap dataMapItem : dataMaps) {
            Leg leg = new Leg();
            Log.e("hqthao", "item " + count++);
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

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.endAddress);
        dest.writeString(this.startAddress);
        dest.writeParcelable(this.detailLocation, 0);
        dest.writeTypedList(steps);
        dest.writeString(this.Overview_polyline);
    }

    protected Leg(Parcel in) {
        this.endAddress = in.readString();
        this.startAddress = in.readString();
        this.detailLocation = in.readParcelable(com.fpt.router.library.model.motorbike.DetailLocation.class.getClassLoader());
        this.steps = in.createTypedArrayList(com.fpt.router.library.model.motorbike.Step.CREATOR);
        this.Overview_polyline = in.readString();
    }

    public static final Parcelable.Creator<Leg> CREATOR = new Parcelable.Creator<Leg>() {
        public Leg createFromParcel(Parcel source) {
            return new Leg(source);
        }

        public Leg[] newArray(int size) {
            return new Leg[size];
        }
    };
}
