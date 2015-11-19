package com.fpt.router.web.action.notification.StationNof;

import com.fpt.router.artifacter.dao.StationNotificationDAO;
import com.fpt.router.artifacter.model.entity.StationNotification;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by datnt on 11/13/2015.
 */
public class StationNofAddThread {

    public List<StationNotification> stationNotifications;
    public StationNotificationDAO dao;

    public StationNofAddThread(List<StationNotification> result) {
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

            boolean canAdd = existed();
            if (canAdd) {
                System.out.println(stationNof.getStation().getStationId() + " can inserting...");
                dao.create(stationNof);
            }
        }

        // check station notification already exist but staff not approve, so I check again and compare data
        // if nothing change -> do nothing and just update time, if something change -> update, if have not exist yet -> insert
        public boolean existed() {
            boolean canAdd = false;

            StationNotification existed = dao.readByCode(this.stationNof.getStationCodeID());
            if (existed == null) {
                canAdd = true;
            } else {
                boolean canUpdate = false;
                String changeName = existed.getChangeName();
                if (changeName != null && !stationNof.getChangeName().equals(changeName)) {
                    canUpdate = true;
                }
                String changeStreet = existed.getChangeStreet();
                if (changeStreet != null && !stationNof.getChangeStreet().equals(changeStreet)) {
                    canUpdate = true;
                }

                /*int compareLat = Double.compare(existed.getChangeLatitude(), stationNof.getChangeLatitude());
                if (compareLat < 0) {
                    System.out.println("This Lat oke: " + existed.getChangeLatitude() + " - " + stationNof.getChangeLatitude());
                }

                int compareLon = Double.compare(existed.getChangeLongitude(), stationNof.getChangeLatitude());
                if (compareLon < 0) {
                    System.out.println("This Long oke: " + existed.getChangeLatitude() + " - " + stationNof.getChangeLongitude());
                }*/

                if (canUpdate) {
                    System.out.println("Can update with code ID: " +this.stationNof.getStationCodeID());
                    stationNof.setCreatedTime(new Date());
                    if (stationNof.getState() == 0) {
                        dao.update(stationNof);
                    }
                }
            }
            return canAdd;
        }

        @Override
        public void run() {
            insertStationNof();
        }
    }

}
