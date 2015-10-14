package com.fpt.router.library.model.common;

import android.os.Parcel;

import com.google.android.gms.wearable.DataMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 10/13/15.
 */
public class SubModule implements IWearableModel<SubModule> {
    public int id;
    public String name;
    public long time;

    public SubModule() {

    }

    public SubModule(int id, String name) {
        this.id = id;
        this.name = name;
        this.time = (new Date()).getTime();
    }

    public SubModule(DataMap map) {
        this(map.getInt("id"), map.getString("name"));
    }

    @Override
    public void dataMapToModel(DataMap dataMap) {
        this.id = dataMap.getInt("id", id);
        this.name = dataMap.getString("name");
    }

    @Override
    public DataMap putToDataMap() {
        DataMap map = new DataMap();
        map.putInt("id", id);
        map.putString("name", name);
        return map;
    }

    @Override
    public ArrayList<SubModule> dataMapToListModel(DataMap dataMap) {
        return null;
    }

    @Override
    public ArrayList<DataMap> listModelToDataMap(List<SubModule> items) {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeLong(this.time);
    }

    protected SubModule(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.time = in.readLong();
    }

    public static final Creator<SubModule> CREATOR = new Creator<SubModule>() {
        public SubModule createFromParcel(Parcel source) {
            return new SubModule(source);
        }

        public SubModule[] newArray(int size) {
            return new SubModule[size];
        }
    };
}
