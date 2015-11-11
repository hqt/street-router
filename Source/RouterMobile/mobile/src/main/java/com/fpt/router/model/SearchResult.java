package com.fpt.router.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Huynh Quang Thao on 11/11/15.
 */
public class SearchResult extends RealmObject {
    @PrimaryKey
    @Index
    private String placeId;
    private String placeName;
    private Date latestUsage;

    public Date getLatestUsage() {
        return latestUsage;
    }

    public void setLatestUsage(Date latestUsage) {
        this.latestUsage = latestUsage;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
