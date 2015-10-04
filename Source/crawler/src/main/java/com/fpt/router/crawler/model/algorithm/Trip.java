package com.fpt.router.crawler.model.algorithm;


import org.joda.time.LocalTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 9/26/2015.
 */
public class Trip implements IAlgorithmModel {

    public long tripId;

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

    public LocalTime getArrivalTime(Station station) {
        return null;
    }

    public LocalTime getDepartureTime(Station station) {
        for (Connection connection : connections) {
            if (connection.pathInfo.from.id == station.id) {
                return connection.arrivalTime;
            }
        }
        return null;
    }

}
