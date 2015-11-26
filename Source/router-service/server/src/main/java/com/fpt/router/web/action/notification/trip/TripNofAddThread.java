package com.fpt.router.web.action.notification.trip;

import com.fpt.router.artifacter.dao.TripNotificationDAO;
import com.fpt.router.artifacter.model.entity.TripNotification;
import org.joda.time.LocalTime;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by datnt on 11/18/2015.
 */
public class TripNofAddThread {

    public List<TripNotification> tripNofs;
    private TripNotificationDAO dao;

    public TripNofAddThread(List<TripNotification> items) {
        this.tripNofs = items;
        dao = new TripNotificationDAO();
    }

    public void run() {
        processAddThread();
    }

    public void processAddThread() {

        ExecutorService executor = Executors.newFixedThreadPool(15);
        for (TripNotification tripNof : this.tripNofs) {
            TripNofInsertThread thread = new TripNofInsertThread(tripNof);
            executor.execute(thread);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Main Insert Trip Notification is interrupted");
            e.printStackTrace();
        }
    }

    private class TripNofInsertThread implements Runnable {

        private TripNotification tripNof;

        public TripNofInsertThread(TripNotification tripNof) {
            this.tripNof = tripNof;
        }

        public void insertTripNof() {
            boolean canAdd = existed();
            if (canAdd) {
                System.out.println("Insert Trip Notification in route Route: " + tripNof.getRouteNo() + " - " + tripNof.getTripNo());
                dao.create(tripNof);
            }

        }

        public boolean existed() {
            boolean canAdd = false;

            TripNotification existed = dao.readTripNof(tripNof.getRouteNo(), tripNof.getTripNo(), tripNof.getRouteType());

            if (existed == null) {
                canAdd = true;
            } else {
                // compare the change between tripnof and tripdb
                // if nothing change -> just update createtime,
                // if something change -> update trip with this change and create time
                boolean canUpdate = false;

                LocalTime existStartTime = existed.getChangeStartTime();
                LocalTime nofStartTime = tripNof.getChangeStartTime();
                if (existStartTime == null && nofStartTime != null) {
                    existed.setChangeStartTime(nofStartTime);
                    canUpdate = true;
                }
                if (nofStartTime != null && !nofStartTime.equals(existStartTime)) {
                    existed.setChangeStartTime(nofStartTime);
                    canUpdate = true;
                }

                LocalTime existEndTime = existed.getChangeEndTime();
                LocalTime nofEndTime = tripNof.getChangeEndTime();
                if (existEndTime == null && nofEndTime != null) {
                    existed.setChangeEndTime(nofEndTime);
                    canUpdate = true;
                }
                if (nofEndTime != null && !nofEndTime.equals(existEndTime)) {
                    existed.setChangeEndTime(nofEndTime);
                    canUpdate = true;
                }

                if (canUpdate) {
                    System.out.println("Update Trip Notification in route " + tripNof.getRouteNo() + " - " + tripNof.getTripNo());
                    existed.setCreatedTime(new Date());
                    if (existed.getState() == 0) {
                        dao.update(existed);
                    }
                }
            }

            return canAdd;
        }

        @Override
        public void run() {
            insertTripNof();
        }
    }

}
