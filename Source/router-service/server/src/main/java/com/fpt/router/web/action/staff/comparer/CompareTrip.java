package com.fpt.router.web.action.staff.comparer;

import com.fpt.router.artifacter.model.entity.Trip;
import com.fpt.router.artifacter.model.entity.TripNotification;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by datnt on 11/13/2015.
 */
public class CompareTrip {

    private HashSet<Trip> redundantTripDB;
    private HashSet<Trip> redundantTripSource;
    private List<Trip> listTripDB;
    private List<Trip> listTripSource;
    public List<TripNotification> listTripNof;

    public CompareTrip(List<Trip> listTripDB, List<Trip> listTripSource) {
        this.redundantTripDB = new HashSet<Trip>(listTripDB);
        this.redundantTripSource = new HashSet<Trip>(listTripSource);
        this.listTripDB = listTripDB;
        this.listTripSource = listTripSource;
        this.listTripNof = new ArrayList<TripNotification>();
    }

    public void run() {
        processRedundant();
        processComparison();
    }

    protected void redundantTrip() {
        HashSet<Trip> hsTotal = new HashSet<Trip>();
        hsTotal.addAll(redundantTripDB);
        hsTotal.addAll(redundantTripSource);

        hsTotal.removeAll(redundantTripDB);
        redundantTripSource.removeAll(hsTotal);
        redundantTripDB.removeAll(redundantTripSource);

        redundantTripSource = hsTotal;

        // split list trip
        this.listTripDB.removeAll(redundantTripDB);
        this.listTripSource.removeAll(redundantTripSource);
    }

    public void processRedundant() {
        redundantTrip();
        for (Trip trip : redundantTripDB) {
            TripNotification tripNof = convertTripNof(trip);
            tripNof.setTrip(trip);
            tripNof.setType(2);
            this.listTripNof.add(tripNof);
        }
        for (Trip trip : redundantTripSource) {
            TripNotification tripNof = convertTripNof(trip);
            tripNof.setType(1);
            this.listTripNof.add(tripNof);
        }
    }

    public TripNotification convertTripNof(Trip trip) {
        TripNotification tripNof = new TripNotification();
        tripNof.setChangeStartTime(trip.getStartTime());
        tripNof.setChangeEndTime(trip.getEndTime());
        tripNof.setCreatedTime(new Date());
        tripNof.setRouteNo(trip.getRoute().getRouteNo());
        tripNof.setRouteType(trip.getRoute().getRouteType());
        tripNof.setTripNo(trip.getTripNo());
        return tripNof;
    }

    public void processComparison() {

        //System.out.println("Main compare trip thread is starting...");

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (Trip tripDB : this.listTripDB) {
            for (Trip tripSource : this.listTripSource) {
                ComparisonThreadTrip comparisonThreadTrip = new ComparisonThreadTrip(tripDB, tripSource);
                executorService.execute(comparisonThreadTrip);
            }
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Main Trip thread is interrupted");
            e.printStackTrace();
        }

        //System.out.println("Main compare trip thread is finished.");

    }

    private class ComparisonThreadTrip implements Runnable {
        private Trip tripDB;
        private Trip tripSource;

        // constructor thread with entries point are trip database and trip source.
        public ComparisonThreadTrip(Trip tripDB, Trip tripSource) {
            this.tripDB = tripDB;
            this.tripSource = tripSource;
        }

        // entry point thread for compare trip in same route
        @Override
        public void run() {
            compareTrip();
        }

        // compare trip between database and source
        // compare trip in same route and containing id equally.
        public void compareTrip() {

            int routeNoDB = this.tripDB.getRoute().getRouteNo();
            int routeNoSource = this.tripSource.getRoute().getRouteNo();
            if (routeNoDB != routeNoSource) {
                return;
            }

            if (this.tripDB.getTripNo() != this.tripSource.getTripNo()) {
                return;
            }

            boolean canAdd = false;

            // here for get various value from trip comparison.
            TripNotification tripNof = new TripNotification();

            LocalTime tripDBStartTime = this.tripDB.getStartTime();
            LocalTime tripSourceStartTime = this.tripSource.getStartTime();
            if (!tripDBStartTime.equals(tripSourceStartTime)) {
                //System.out.println("Trip Source Start Time. " + tripSourceStartTime + "************************************************");
                tripNof.setChangeStartTime(tripSourceStartTime);
                canAdd = true;
            }

            LocalTime tripDBEndTime = this.tripDB.getEndTime();
            LocalTime tripSourceEndTime = this.tripSource.getEndTime();
            if (!tripDBEndTime.equals(tripSourceEndTime)) {
                //System.out.println("Trip Source End Time. " + tripSourceStartTime + "************************************************");
                tripNof.setChangeEndTime(tripSourceEndTime);
                canAdd = true;
            }

            if (canAdd) {
                tripNof.setTrip(this.tripDB);
                tripNof.setTripNo(this.tripSource.getTripNo());
                tripNof.setRouteNo(this.tripSource.getRoute().getRouteNo());
                tripNof.setRouteType(this.tripSource.getRoute().getRouteType());
                tripNof.setType(0);
                tripNof.setCreatedTime(new Date());
                listTripNof.add(tripNof);
            }
        }
    }
}
