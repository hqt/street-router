package com.fpt.router.artifacter.model.algorithm;


import org.joda.time.LocalTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 9/26/2015.
 */
public class Trip implements IAlgorithmModel {

    public int tripId;

    public int tripNo;

    public LocalTime startTime;

    public LocalTime endTime;

    public Route route;

    public List<Connection> connections;

    public Trip(){
        connections =  new ArrayList<Connection>();
    }

    @Override
    public void build() {

    }

    // our business. departure totalTime and arrival totalTime is same at one station
    public LocalTime getArrivalTime(Station station) {
        return getDepartureTime(station);
    }

    // our business. departure totalTime and arrival totalTime is same at one station
    public LocalTime getDepartureTime(Station station) {
        /*for (Connection connection : connections) {
            if (connection.pathInfo.from.id == station.id) {
                return connection.arrivalTime;
            }
        }*/
        for (Connection connection : connections) {
            if (connection.pathInfo.from.code.equals(station.code)) {
                return connection.arrivalTime;
            }
        }
        return null;
    }

}
