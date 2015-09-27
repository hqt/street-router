package com.hqt.model;

import java.sql.Time;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/20/15.
 */
public class Trip {
    public int tripId;

    // route this trip belong to
    public Route route;

    // time when bus start from first station
    public Time startTime;
    // time when bus come to last station
    public Time endTime;

    public List<Connection> connections;

    public double getArrivalTime(Station station) {
        return 0;
    }

    public double getDepartureTime(Station station) {
        for (Connection connection : connections) {
            if (connection.pathInfo.from.id == station.id) {
                return connection.arrivalTime;
            }
        }
        return 0;
    }
}
