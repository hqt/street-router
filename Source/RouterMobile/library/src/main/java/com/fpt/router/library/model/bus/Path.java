package com.fpt.router.library.model.bus;



import android.os.Parcel;

import com.fpt.router.library.model.entity.PathType;
import com.fpt.router.library.model.common.Location;
import com.google.android.gms.wearable.DataMap;

import org.joda.time.Period;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: A Path is a detail information from stationA to stationB in one journey
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class Path implements INode {
    public int stationFromId;
    public int stationToId;
    public String stationFromName;
    public String stationToName;
    public Location stationFromLocation;
    public Location stationToLocation;
    public int transferTurn;
    public PathType type;
    public int routeNo;
    public double distance;
    public Period time;     // in minute
    public List<Location> points;

    @Override
    public void dataMapToModel(DataMap dataMap) {

    }

    /*@Override
    public DataMap putToDataMap() {
        DataMap map = new DataMap();
        map.putInt("station_from_id", stationFromId);
        map.putInt("station_to_id", stationToId);
        map.putString("station_from_name", stationFromName);
        map.putString("station_to_name", stationToName);
        map.putDataMap("station_from_location", stationFromLocation.putToDataMap());
        map.putDataMap("station_to_location", stationToLocation.putToDataMap());
        map.putInt("transfer_turn", transferTurn);
        map.put
        return map;
    }*/

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
