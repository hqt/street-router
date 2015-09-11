package com.fpt.router.model;

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
    RouteType routeType;
    // high frequently time
    public Date peakTimeRange;
    // low frequently time
    public Date offPeakTimeRange;
    public List<Trip> trips;
}
