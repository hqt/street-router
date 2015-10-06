package com.fpt.router.crawler.model.algorithm;

import com.fpt.router.crawler.model.helper.RouteType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Huynh Quang Thao on 9/26/2015.
 */
public class Route implements IAlgorithmModel {

    public int routeId;

    public int routeNo;

    public RouteType routeType;

    public String routeName;

    public List<Trip> trips;

    public List<PathInfo> pathInfos;

    // map from station to order
    public Map<Integer, Integer> stationOrder;


    public Route(){
    }

    public int getTotalStations() {
        return pathInfos.size() + 1;
    }

    public Station getStationByOrder(int order) {
        return pathInfos.get(order).from;
    }

    public int getOrderByStation(Station station) {
        return stationOrder.get(station.id);
    }

    public int compareOrder(Station first, Station second) {
        return compareOrder(first.id, second.id);
    }

    public int compareOrder(int first, int second) {
        if (first == second) return 0;

        Integer firstStationOrder = stationOrder.get(first);
        Integer secondStationOrder = stationOrder.get(second);
        if (firstStationOrder == null || secondStationOrder == null) {
            throw new IllegalStateException("those stationMap not belong to this route");
        }

        return firstStationOrder.compareTo(secondStationOrder);


    }


    @Override
    public void build() {

    }

}
