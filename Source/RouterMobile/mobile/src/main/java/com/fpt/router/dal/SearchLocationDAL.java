package com.fpt.router.dal;

import com.fpt.router.model.SearchLocation;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Huynh Quang Thao on 11/10/15.
 */
public class SearchLocationDAL {

    public static SearchLocation insertSearchLocation(String id, String name) {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        SearchLocation searchLocation = realm.createObject(SearchLocation.class);
        searchLocation.setPlaceId(id);
        searchLocation.setPlaceName(name);
        searchLocation.setLatestUsage(new Date());
        realm.commitTransaction();

        return searchLocation;
    }

    public static List<SearchLocation> getListSearchLocation() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<SearchLocation> query = realm.where(SearchLocation.class);
        RealmResults<SearchLocation> searchLocations = query.findAll();
        return searchLocations.subList(0, searchLocations.size());
    }

    public boolean deleteSearchLocation() {
        return false;
    }


}
