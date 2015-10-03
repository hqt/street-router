package com.fpt.router.crawler.model.algorithm;


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

    public Date startTime;

    public Date endTime;

    public Route route;

    public List<Connection> connections;

    public Trip(){
        connections =  new ArrayList<Connection>();
    }

    @Override
    public void build() {

    }

    /*public double getArrivalTime(Station station) {
        return 0;
    }

    public double getDepartureTime(Station station) {
        for (Connection connection : connections) {
            if (connection.pathInfo.from.id == station.id) {
                return connection.arrivalTime;
            }
        }
        return 0;
    }*/

}
