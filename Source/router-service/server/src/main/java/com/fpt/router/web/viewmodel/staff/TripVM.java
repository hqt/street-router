package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Trip;
import org.joda.time.LocalTime;

/**
 * Created by datnt on 10/28/2015.
 */
public class TripVM {

    private int tripId;
    private int tripNo;
    private LocalTime startTime;
    private LocalTime endTime;
    private Route route;

    public TripVM(Trip trip) {
        this.tripId = trip.getTripId();
        this.tripNo = trip.getTripNo();
        this.route = trip.getRoute();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
