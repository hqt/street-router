package com.fpt.router.library.model.common;

import android.os.Parcel;

import com.google.android.gms.wearable.DataMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 10/13/15.
 */
public class Model implements IWearableModel<Model> {
    public static int count = 0;

    public int id;
    public String name;
    public long time;
    public SubModule module;


    public Model() {

    }

    public Model(int id, String name) {
        this.id = id + count++;
        this.name = name;
        this.time = (new Date()).getTime();
    }

    @Override
    public void dataMapToModel(DataMap map) {
        this.id = map.getInt("id");
        this.name = map.getString("name");
        this.module = new SubModule();
        module.dataMapToModel(map.getDataMap("module"));
    }

    @Override
    public DataMap putToDataMap() {
        DataMap map = new DataMap();
        map.putInt("id", id);
        map.putString("name", name);
        map.putDataMap("module", module.putToDataMap());
        return map;
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeLong(this.time);
        dest.writeParcelable(this.module, 0);
    }

    protected Model(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.time = in.readLong();
        this.module = in.readParcelable(SubModule.class.getClassLoader());
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        public Model createFromParcel(Parcel source) {
            return new Model(source);
        }

        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
}