package com.hqt.algorithm;

import com.hqt.model.*;

import java.sql.Time;
import java.util.*;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/20/15.
 */
public class RaptorAlgorithm {
    CityMap map;
    Station start;
    Station end;
    double departure_time;

    public String run(Location start, Location end, String startAddress, String endAddress) {
        return null;
    }

    // number of change bus
    int K;

    // limit route for each turn. just care station that can be hop on
    List<Integer> markedStationIds;

    // list for storing pair(rId, pId) for traversing purpose. Using Map for searching effective
    Map<Integer, Integer> Q;
    // List<Pair<Integer, Integer>> Q = new ArrayList<Pair<Integer, Integer>>();

    // earliest arrival time at station p
    double[] earliest_arrival_time;

    double[][] result;

    // using for track back.
    // traceUsedRoute[i] = j. shortest arrival time come to i-station using route j
    int[] traceUsedRoute;

    // parent station of current station. so we go on bus on this station
    int[] traceFromStation;

    private void initialize() {
        Q = new HashMap<Integer, Integer>();

        // initialize for traceUsedRoute.
        traceUsedRoute = new int[map.stations.size()];
        Arrays.fill(traceUsedRoute, -1);

        // initialize for traceFromStation
        traceFromStation = new int[map.stations.size()];
        Arrays.fill(traceFromStation, -1);

        // initialize for marked station
        markedStationIds = new ArrayList<Integer>();
        markedStationIds.add(start.id);

        // initialize earliest arrival time
        earliest_arrival_time = new double[map.stations.size()];
        Arrays.fill(earliest_arrival_time, Double.MAX_VALUE);
        earliest_arrival_time[start.id] = departure_time;

        // initialize for result
        result = new double[K+1][map.stations.size()];
        Arrays.fill(result[0], Double.MAX_VALUE);
        result[0][start.id] = departure_time;
    }

    public void raptor() {

        for (int k = 1; k <= K; k++) {

            // step 1. find the  first stop that satisfy condition
            for (Integer p : markedStationIds) {
                // all routes that go through this stop
                for (Route route : map.getStationById(p).routes) {
                    if (Q.containsKey(route.routeId)) {
                        int _p = Q.get(route.routeId);
                        // p come before _p in sequence of route then update
                        if (route.compareOrder(p, _p) == -1) {
                            Q.put(route.routeId, p);
                        }
                    }
                }
            }
            markedStationIds.clear();

            // step 2. traversal each route
            Trip t;
            for (Map.Entry<Integer, Integer> pair : Q.entrySet()) {
                t = null;
                Route route = map.getRouteById(pair.getKey());
                Station startStation = map.getStationById(pair.getValue());

                // order of this station in this route.
                int startOrder = route.getOrderByStation(startStation);

                // because this sequence number is always increased. we can find this way for more optimal
                int currentTripIndex = 0;

                // optimize from this station to end on same route
                for (int order = startOrder; order < route.getTotalStations() ; order++) {
                    Station p_i = route.getStationByOrder(order);

                    if (t!= null) {
                        double arrivalTime = t.getArrivalTime(p_i);
                        // condition for update. we can go thi station earlier due to update trip
                        if (arrivalTime < Math.min(earliest_arrival_time[p_i.id], earliest_arrival_time[end.id])) {
                            result[k][p_i.id] = arrivalTime;
                            earliest_arrival_time[p_i.id] = arrivalTime;
                            // from this station. maybe can update other routes at next turn
                            markedStationIds.add(p_i.id);
                            // store information for trace back
                            traceFromStation[p_i.id] = startStation.id;
                            traceUsedRoute[p_i.id] = route.routeId;
                        }
                    }

                    // find the earliest trip can catch at p_i.
                    // because trips has been sorted by time. trip's arrival time can only decrease at each station after on same route
                    // so. we keep an index of current process trip for saving time. so total complexity is O(T)
                    for (int tripIndex = currentTripIndex; tripIndex < route.trips.size(); tripIndex++) {
                        Trip trip = route.trips.get(tripIndex);
                        // condition of trip that can go at p_i
                        if (result[k-1][p_i.id] < trip.getDepartureTime(p_i)) {
                            t = trip;
                            currentTripIndex = tripIndex;
                            break;
                        }
                    }
                }
            }

            // step 3. look at foot-path

            // step 4. check if no need to optimize anymore
            if (markedStationIds.size() == 0) {
                break;
            }
        }
    }

    private void buildResult() {
        int currentStationId = end.id;
        Station currentStation = map.getStationById(currentStationId);
        while(traceFromStation[currentStationId] != -1) {
            int previousStationId = traceFromStation[end.id];
            Station previousStation = map.getStationById(previousStationId);
            // build edge from previousStation to currentStation
            for (PathInfo pathInfo : previousStation.pathInfos) {

            }

            currentStationId = previousStationId;

        }

        Time time;
    }
}


/*
public class Node {
    public String name;
    public String street;
    public Location location;
    public String type;
    public double distance;
    public double time;
    public int routeNo;
    public List<Location> points;
}
*/
