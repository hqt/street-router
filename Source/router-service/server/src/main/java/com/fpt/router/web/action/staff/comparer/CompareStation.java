package com.fpt.router.web.action.staff.comparer;

import com.fpt.router.artifacter.model.entity.Station;
import com.fpt.router.artifacter.model.entity.StationNotification;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompareStation {

    private HashSet<Station> redundantStationDB;
    private HashSet<Station> redundantStationSource;
    private List<Station> listStationDB;
    private List<Station> listStationSource;
    public List<StationNotification> listStationNof;

    public CompareStation(List<Station> listStationDB, List<Station> listStationSource) {
        this.redundantStationDB = new HashSet<Station>(listStationDB);
        this.redundantStationSource = new HashSet<Station>(listStationSource);
        this.listStationDB = listStationDB;
        this.listStationSource = listStationSource;
        this.listStationNof = new ArrayList<StationNotification>();
    }

    public void run() {
        processRedundant();
        processComparison();
    }

    public void redundantStation() {
        HashSet<Station> hsTotal = new HashSet<Station>();
        hsTotal.addAll(redundantStationDB);
        hsTotal.addAll(redundantStationSource);

        hsTotal.removeAll(redundantStationDB);
        redundantStationSource.removeAll(hsTotal);
        redundantStationDB.removeAll(redundantStationSource);

        redundantStationSource = hsTotal;

        // split list trip
        this.listStationDB.removeAll(redundantStationDB);
        this.listStationSource.removeAll(redundantStationSource);
    }

    public void processRedundant() {
        redundantStation();
        for (Station stationDB : redundantStationDB) {
            StationNotification stationNof = convertStationNof(stationDB);
            stationNof.setStation(stationDB);
            stationNof.setType(2);
            this.listStationNof.add(stationNof);
        }
        for (Station stationSource : redundantStationSource) {
            StationNotification stationNof = convertStationNof(stationSource);
            stationNof.setType(1);
            this.listStationNof.add(stationNof);
        }
    }

    public StationNotification convertStationNof(Station station) {
        StationNotification nof = new StationNotification();
        nof.setChangeName(station.getName());
        nof.setChangeStreet(station.getStreet());
        nof.setChangeLatitude(station.getLatitude());
        nof.setChangeLongitude(station.getLongitude());
        nof.setStationCodeID(station.getCodeId());
        nof.setCreatedTime(new Date());
        return nof;
    }

    public void processComparison() {
        System.out.println("Main thread station comparison starting.");

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for (Station stationDB : this.listStationDB) {
            for (Station stationSource : this.listStationSource) {
                ComparisonThreadStation comparisonThreadStation = new ComparisonThreadStation(stationDB, stationSource);
                executorService.execute(comparisonThreadStation);
            }
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Main thread is interrupted");
            e.printStackTrace();
        }

        System.out.println("Main thread station comparison finished.");
    }

    private class ComparisonThreadStation implements Runnable {

        private Station stationDB;
        private Station stationSource;
        private StationNotification stationNotification;

        public ComparisonThreadStation(Station stationDB, Station stationSource) {
            this.stationDB = stationDB;
            this.stationSource = stationSource;
        }

        public void equalStation() {

            if (!stationDB.getCodeId().equals(stationSource.getCodeId())) {
                return;
            }

            boolean canAdd = false;

            stationNotification = new StationNotification();
            stationNotification.setStation(stationDB);

            if (!stationDB.getName().equals(stationSource.getName())) {
                stationNotification.setChangeName(stationSource.getName());
                canAdd = true;
            }

            if (!stationDB.getStreet().equals(stationSource.getStreet())) {
                stationNotification.setChangeStreet(stationSource.getStreet());
                canAdd = true;
            }

           /* if (stationDB.getLatitude() != stationSource.getLatitude()) {
                stationNotification.setChangeLatitude(stationSource.getLatitude());
                canAdd = true;
            }

            if (stationDB.getLongitude() != stationSource.getLongitude()) {
                stationNotification.setChangeLongitude(stationSource.getLongitude());
                canAdd = true;
            }*/

            if (canAdd) {
                stationNotification.setStationCodeID(stationSource.getCodeId());
                stationNotification.setType(0);
                //System.out.println("Station Code ID From Server " +stationSource.getCodeId());
                stationNotification.setCreatedTime(new Date());
                listStationNof.add(this.stationNotification);
            }
        }

        @Override
        public void run() {
            equalStation();
        }
    }

}
