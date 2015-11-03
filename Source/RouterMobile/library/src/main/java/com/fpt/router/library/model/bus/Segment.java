package com.fpt.router.library.model.bus;

import android.os.Parcel;

import com.fpt.router.library.model.entity.PathType;
import com.google.android.gms.wearable.DataMap;

import org.joda.time.Period;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: a segment is a collection of stations on same route on that journey
 * Created by Huynh Quang Thao on 10/10/15.
 */
public class Segment implements INode,Serializable {
    // route information
    public int routeId;
    public String routeName;
    public int routeNo;

    // the i-th transfer of the journey
    public int tranferNo;

    public PathType pathType;

    public double segmentDistance;

    public Period segmentTime;

    public List<Path> paths;

    @Override
    public void dataMapToModel(DataMap dataMap) {

    }

    @Override
    public DataMap putToDataMap() {
        return null;
    }

    @Override
    public ArrayList dataMapToListModel(DataMap dataMap) {
        return null;
    }

    @Override
    public ArrayList<DataMap> listModelToDataMap(List items) {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
