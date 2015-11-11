package com.fpt.router.dal;

import com.fpt.router.model.SearchLocation;

import java.util.Collections;
import java.util.Comparator;
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


        SearchLocation searchLocation = new SearchLocation();
        searchLocation.setPlaceId(id);
        searchLocation.setPlaceName(name);
        searchLocation.setLatestUsage(new Date());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(searchLocation);
        realm.commitTransaction();

        return searchLocation;
    }

    public static List<SearchLocation> getListSearchLocation() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<SearchLocation> query = realm.where(SearchLocation.class);
        RealmResults<SearchLocation> searchLocations = query.findAllSorted("latestUsage", RealmResults.SORT_ORDER_DESCENDING);
        List<SearchLocation> res =  searchLocations.subList(0, searchLocations.size());

        return res;
    }

    public boolean deleteSearchLocation() {
        return false;
    }


}
