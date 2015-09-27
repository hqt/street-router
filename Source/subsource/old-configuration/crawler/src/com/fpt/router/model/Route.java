package com.fpt.router.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/5/15.
 */
public class Route {
    public enum RouteType {
        DEPART,
        RETURN
    }

    public int routeId;
    public RouteType routeType;
    public String roundName;

    // high frequently time
    public Date peakTimeRange;
    // low frequently time
    public Date offPeakTimeRange;
    public List<Trip> trips = new ArrayList<Trip>();

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public Date getPeakTimeRange() {
        return peakTimeRange;
    }

    public void setPeakTimeRange(Date peakTimeRange) {
        this.peakTimeRange = peakTimeRange;
    }

    public Date getOffPeakTimeRange() {
        return offPeakTimeRange;
    }

    public void setOffPeakTimeRange(Date offPeakTimeRange) {
        this.offPeakTimeRange = offPeakTimeRange;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
