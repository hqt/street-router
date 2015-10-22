package com.fpt.router.artifacter.algorithm;


import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.model.algorithm.*;
import com.fpt.router.artifacter.model.helper.PathType;
import com.fpt.router.artifacter.model.viewmodel.INode;
import com.fpt.router.artifacter.model.viewmodel.Path;
import com.fpt.router.artifacter.model.viewmodel.Result;
import com.fpt.router.artifacter.model.viewmodel.Segment;
import com.fpt.router.artifacter.utils.TimeUtils;
import com.google.common.collect.Lists;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import java.util.*;

/**
 * Purpose: Raptor Algorithm
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

    // earliest arrival totalTime at station p
    LocalTime[] earliest_arrival_time;

    LocalTime[][] result;

    // using for track back.
    // traceUsedRoute[i] = j. shortest arrival totalTime come to i-station using route j
    int[][] traceUsedRoute;

    // parent station of current station. so we go on bus on this station
    int[][] traceFromStation;

    private void initialize() {
        Q = new HashMap<Integer, Integer>();

        // initialize for traceUsedRoute.
        traceUsedRoute = new int[K+1][map.stations.size()];
        for (int i = 0; i < K+1; i++) {
            Arrays.fill(traceUsedRoute[i], -1);
        }

        // initialize for traceFromStation
        traceFromStation = new int[K+1][map.stations.size()];
        for (int i = 0; i < K+1; i++) {
            Arrays.fill(traceFromStation[i], -1);
        }

        // initialize for marked station
        markedStationIds = new ArrayList<Integer>();
        markedStationIds.add(start.id);

        // initialize earliest arrival totalTime
        earliest_arrival_time = new LocalTime[map.stations.size()];
        Arrays.fill(earliest_arrival_time, Config.MAXIMUM_TIME);
        earliest_arrival_time[start.id] = departure_time;

        // initialize for result
        result = new LocalTime[K+1][map.stations.size()];
        for (int i = 0; i < K+1; i++) {
            Arrays.fill(result[i], Config.MAXIMUM_TIME);
        }
        result[0][start.id] = departure_time;
    }

    public void raptor() {

        for (int k = 1; k <= K; k++) {

            // step 0. assign previous step to current step

            // assign upper bound on the earliest arrival time at p with at most k-trip
            System.arraycopy(result[k - 1], 0, result[k], 0, map.stations.size());
            System.arraycopy(traceFromStation[k-1], 0, traceFromStation[k], 0, map.stations.size());
            System.arraycopy(traceUsedRoute[k-1], 0, traceUsedRoute[k], 0, map.stations.size());


            // step 1. find the  first stop that satisfy condition
            for (Integer p : markedStationIds) {
                // all routes that go through this stop
                for (Route route : map.getStationById(p).routes) {
                    if (Config.blockRoute.contains(route.routeNo)) continue;
                    // if (!Config.allowRoute.contains(route.routeNo)) continue;
                    if (Q.containsKey(route.routeId)) {
                        // station already in queue respectively with route r
                        int _p = Q.get(route.routeId);
                        // p come before _p in sequence of route then update
                        if (route.compareOrder(p, _p) == -1) {
                            Q.put(route.routeId, p);
                        }
                    } else {
                        Q.put(route.routeId, p);
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
                // Trips has been sorted by totalTime. trip's arrival totalTime can only decrease at each station after on same route
                // so. we keep an index of current process trip for saving totalTime. so total complexity is O(T)
                int currentTripIndex = 0;

                // optimize from this station to end on same route
                // **Note 1** first station: t always null. so not trace back (true). because it has been traced at previous totalTransfer
                for (int order = startOrder; order < route.getTotalStations() ; order++) {
                    Station p_i = route.getStationByOrder(order);

                    if (t!= null) {
                        // get arrival totalTime of trip at station p (this trip has been taken at previous station)
                        LocalTime arrivalTime = t.getArrivalTime(p_i);

                        // condition for update: we can go this station earlier due to update trip
                        // condition: arrivalTime(p) < min(earliestArrivalTime(p), earliestArrivalTime(end))

                        // get minimum
                        LocalTime minimum;
                        if (earliest_arrival_time[p_i.id].compareTo(earliest_arrival_time[end.id]) <= 0) {
                            minimum = earliest_arrival_time[p_i.id];
                        } else {
                            minimum = earliest_arrival_time[end.id];
                        }

                        // condition
                        if (arrivalTime.compareTo(minimum) < 0) {
                            result[k][p_i.id] = arrivalTime;
                            earliest_arrival_time[p_i.id] = arrivalTime;
                            // from this station. maybe can update other routes at next turn
                            markedStationIds.add(p_i.id);

                            // store information for trace back
                            traceFromStation[k][p_i.id] = startStation.id;
                            traceUsedRoute[k][p_i.id] = route.routeId;
                        }
                    }

                    // find the earliest trip can catch at p_i.
                    // if can find new trip at this station. we can hop next stations at same route
                    // maybe different trip. but still on same route. so we can marked parent of new station is start station :)
                    for (int tripIndex = currentTripIndex; tripIndex < route.trips.size(); tripIndex++) {
                        Trip trip = route.trips.get(tripIndex);
                        // condition totalTime this trip go from p later than best arrival totalTime(p) + waiting totalTime. (waiting totalTime of current system = 0)
                        if (trip.getDepartureTime(p_i) != null) {
                            count++;
                        }

                        if (result[k-1] == null || result[k-1][p_i.id] == null || trip.getDepartureTime(p_i) == null) {
                            System.out.println("fucking wrong here");
                            int  a = 3;
                        }

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

    int count = 0;

    private Result buildResult() {

        int transferTurn = K;
        int realTransferTurn = 0;
        double totalDistance = startPath.distance + endPath.distance;
        Period totalTime = startPath.time.plus(endPath.time);

        int currentHopStationId = end.id;

        List<INode> res = new ArrayList<INode>();
        res.add(endPath);

        while(traceFromStation[transferTurn][currentHopStationId] != -1) {
            Station currentHopStation = map.getStationById(currentHopStationId);

            int previousHopStationId = traceFromStation[transferTurn][currentHopStationId];
            Station previousHopStation = map.getStationById(previousHopStationId);

            int routeId = traceUsedRoute[transferTurn][currentHopStation.id];
            Route route = map.getRouteById(routeId);

            // create middle stationMap in same route
            Segment segment = buildMiddleResult(previousHopStation, currentHopStation, route, transferTurn--);
            realTransferTurn++;
            res.add(segment);

            // accumulate value for distance and time
            totalDistance += segment.segmentDistance;
            totalTime = totalTime.plus(segment.segmentTime);

            currentHopStationId = previousHopStationId;
        }

        res.add(startPath);

        // save for reuse later. because realTransferTurn will be changed in next loop :)
        transferTurn = realTransferTurn;

        // build again real transfer turn
        for (int i = 0; i < res.size(); i++) {
            INode node = res.get(i);
            if (node instanceof Segment) {
                Segment segment = (Segment) node;
                segment.tranferNo = realTransferTurn;
                realTransferTurn--;
            }
        }

        // reverse again this list
        res = Lists.reverse(res);

        // make final result
        Result result = new Result();

        result.nodeList = res;
        result.totalDistance = totalDistance;
        result.totalTime = totalTime;
        result.totalTransfer = transferTurn;
        result.minutes = (int) (TimeUtils.convertToMilliseconds(totalTime) / (1000 * 60));

        if (res.size() == 2) {
            result.code = "fail";
        } else {
            result.code = "success";
        }

        return result;
    }

    /**
     * buildIndex a node result from begin to end station. use just for clearer when view.
     * user don't do anything in those bus.
     * last path will always pathType critical. means user should take care
     */
    private Segment buildMiddleResult(Station begin, Station end, Route route, int transferTurn) {

        // this segment includes many paths
        Segment segment = new Segment();
        segment.routeId = route.routeId;
        segment.routeName = route.routeName;
        segment.routeNo = route.routeNo;
        segment.tranferNo = transferTurn;
        segment.pathType = PathType.CONNECTED_BUS;

        // building middle stations for this segment
        List<Path> res = new ArrayList<Path>();

        Period totalPeriod = new Period(0);
        double totalDistance = 0.0f;

        int startOrder = route.getOrderByStation(begin);
        int endOrder = route.getOrderByStation(end);
        for (int order = startOrder; order < endOrder ; order++) {
            PathInfo pathInfo = route.pathInfos.get(order);
            Path path = new Path();

            // assign all information for path

            path.stationFromId = pathInfo.from.id;
            path.stationToId = pathInfo.to.id;
            path.stationFromName = pathInfo.from.name;
            path.stationToName = pathInfo.to.name;

            path.points = pathInfo.middleLocations;
            if (order < endOrder-1) {
                path.pathType = PathType.CONNECTED_BUS;
            } else {
                path.pathType = PathType.CRITICAL_PATH;
            }

            res.add(path);

            totalPeriod = totalPeriod.plus(pathInfo.cost);
            totalDistance += pathInfo.distance;
        }

        segment.paths = res;
        segment.segmentTime = totalPeriod;
        segment.segmentDistance = totalDistance;

        return segment;
    }
}
