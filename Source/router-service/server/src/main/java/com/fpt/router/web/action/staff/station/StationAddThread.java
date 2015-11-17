package com.fpt.router.web.action.staff.station;

import com.fpt.router.artifacter.dao.StationNotificationDAO;
import com.fpt.router.artifacter.model.entity.StationNotification;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by datnt on 11/13/2015.
 */
public class StationAddThread {

    public List<StationNotification> stationNotifications;
    public StationNotificationDAO dao;

    public StationAddThread(List<StationNotification> result) {
        this.stationNotifications = result;
        dao = new StationNotificationDAO();
    }

    public void run() {
        insertListStation();
    }

    public void insertListStation() {

        ExecutorService executorService = Executors.newFixedThreadPool(30);
        for (StationNotification stationNof : this.stationNotifications) {
            StationInsertThread stationInsertThread = new StationInsertThread(stationNof);
            executorService.execute(stationInsertThread);
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Main Insert Thread is interrupted");
            e.printStackTrace();
        }

    }

    private class StationInsertThread implements Runnable {

        private StationNotification stationNof;

        StationInsertThread(StationNotification stationNotification) {
            this.stationNof = stationNotification;
        }

        public void insertStationNof() {
            System.out.println(stationNof.getStation().getStationId() + " inserting...");
            dao.create(stationNof);
        }

        @Override
        public void run() {
            insertStationNof();
        }
    }

}
