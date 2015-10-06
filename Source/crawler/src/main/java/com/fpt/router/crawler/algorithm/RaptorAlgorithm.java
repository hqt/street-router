package com.fpt.router.crawler.algorithm;


import com.fpt.router.crawler.model.algorithm.*;
import com.fpt.router.crawler.model.helper.Location;
import com.fpt.router.crawler.model.helper.PathType;
import com.fpt.router.crawler.model.viewmodel.Path;
import com.fpt.router.crawler.model.viewmodel.Result;
import org.joda.time.LocalTime;

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
    Path startPath;
    Path endPath;
    LocalTime departure_time;

    public Result run(CityMap map, Station start, Station end, Path startPath, Path endPath, int K, boolean isOptimizeK, LocalTime departureTime) {
        this.map = map;
        this.start = start;
        this.end = end;
        this.startPath = startPath;
        this.endPath = endPath;
        this.K = K;
        this.isOptimizeK = isOptimizeK;
        this.departure_time = departureTime;

        initialize();
        raptor();

        return buildResult();
    }

    // number of change bus
    int K;
    boolean isOptimizeK;

    // limit route for each turn. just care station that can be hop on
    List<Integer> markedStationIds;

    // list for storing pair(rId, pId) for traversing purpose. Using Map for searching effective
    Map<Integer, Integer> Q;
    // List<Pair<Integer, Integer>> Q = new ArrayList<Pair<Integer, Integer>>();

    // earliest arrival time at station p
    LocalTime[] earliest_arrival_time;

    LocalTime[][] result;

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
        earliest_arrival_time = new LocalTime[map.stations.size()];
        Arrays.fill(earliest_arrival_time, null);
        earliest_arrival_time[start.id] = departure_time;

        // initialize for result
        result = new LocalTime[K+1][map.stations.size()];
        Arrays.fill(result[0], null);
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
                        LocalTime arrivalTime = t.getArrivalTime(p_i);
                        // condition for update. we can go thi station earlier due to update trip
                        LocalTime minimum;
                        if (earliest_arrival_time[p_i.id].compareTo(earliest_arrival_time[end.id]) <= 0) {
                            minimum = earliest_arrival_time[p_i.id];
                        } else {
                            minimum = earliest_arrival_time[end.id];
                        }

                        if (arrivalTime.compareTo(minimum) < 0) {
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
                        if (result[k-1][p_i.id].compareTo(trip.getDepartureTime(p_i)) < 0) {
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

    private Result buildResult() {

        int transferTurn = K;
        double totalDistance = startPath.distance + endPath.distance;


        int currentHopStationId = end.id;
        Station currentHopStation = map.getStationById(currentHopStationId);

        List<Path> res = new ArrayList<Path>();
        while(traceFromStation[currentHopStationId] != -1) {
            int previousHopStationId = traceFromStation[end.id];
            int routeId = traceUsedRoute[end.id];
            Station previousHopStation = map.getStationById(previousHopStationId);
            Route route = map.getRouteById(routeId);

            // create middle stationMap in same route
            List<Path> middlePaths = buildMiddleResult(previousHopStation, currentHopStation, route, transferTurn--);
            middlePaths.addAll(res);
            res = middlePaths;

            currentHopStationId = previousHopStationId;
        }

        // make final result
        Result result = new Result();
        result.nodeList = res;
        result.distance = totalDistance;
        result.k = K;                   // wrong here
        return result;
    }

    /**
     * build a node result from begin to end station. use just for clearer when view.
     * user don't do anything in those bus.
     * last path will always type critical. means user should take care
     */
    private List<Path> buildMiddleResult(Station begin, Station end, Route route, int transferTurn) {
        List<Path> res = new ArrayList<Path>();
        res.add(startPath);
        int startOrder = route.getOrderByStation(begin);
        int endOrder = route.getOrderByStation(end);
        for (int order = startOrder; order <= endOrder ; order++) {
            PathInfo pathInfo = route.pathInfos.get(order);
            Path path = new Path();
            path.transferTurn = transferTurn;
            path.routeNo = route.routeNo;
            path.points = pathInfo.middleLocations;
            path.type = PathType.CONNECTED_BUS;
            path.stationFromName = pathInfo.from.name;
            path.stationToName = pathInfo.to.name;
            res.add(path);
        }
        res.add(endPath);
        return res;
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
