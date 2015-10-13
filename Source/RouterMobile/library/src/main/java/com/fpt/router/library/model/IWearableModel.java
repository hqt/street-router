package com.fpt.router.library.model;

import com.google.android.gms.wearable.DataMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 10/13/15.
 */
public  interface IWearableModel<T> {
    void dataMapToModel(DataMap dataMap);
    DataMap putToDataMap();
    ArrayList<T> dataMapToListModel(DataMap dataMap);
    ArrayList<DataMap> listModelToDataMap(List<T> items);
}
