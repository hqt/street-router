package com.hqt.model;

import java.util.List;
import java.util.Map;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/20/15.
 */
public class Route {
    public enum RouteType {
        DEPART,
        RETURN
    }
    public int routeId;
    public int routeNo;
    public RouteType routeType;
    public List<PathInfo> pathInfos;

    // list all trip in one day
    public List<Trip> trips;

    // map from station to order
    public Map<Integer, Integer> stationOrder;

    public void build() {

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
            throw new IllegalStateException("those stations not belong to this route");
        }

        return firstStationOrder.compareTo(secondStationOrder);


    }
}
