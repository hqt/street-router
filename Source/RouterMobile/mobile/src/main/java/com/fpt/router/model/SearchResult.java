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
}
