package com.fpt.router.model;


import com.fpt.router.model.algorithm.Connection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/5/15.
 */
public class Trip {

    public int tripId;
    public int tripNo;
    public Date startTime;
    public Date endTime;
    public Route route;
    public List<Connection> connections;


    public Trip(){
        startTime = new Date();
        endTime = new Date();
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getTripNo() {
        return tripNo;
    }

    public void setTripNo(int tripNo) {
        this.tripNo = tripNo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
