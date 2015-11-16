package com.fpt.router.web.action.staff.comparer;

import com.fpt.router.artifacter.model.entity.Station;
import com.fpt.router.artifacter.model.entity.StationNotification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompareStation {

    private List<Station> listStationDB;
    private List<Station> listStationSource;
    public List<StationNotification> listStationVarious;

    public CompareStation(List<Station> listStationDB, List<Station> listStationSource) {
        this.listStationDB = listStationDB;
        this.listStationSource = listStationSource;
        this.listStationVarious = new ArrayList<>();
    }

    public void run() {
        processComparison();
    }

    public void processComparison() {
        System.out.println("Main thread station comparison starting.");

        ExecutorService executorService = Executors.newCachedThreadPool();
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

            if (stationDB.getLatitude() != stationSource.getLatitude()) {
                stationNotification.setChangeLatitude(stationSource.getLatitude());
                canAdd = true;
            }

            if (stationDB.getLongitude() != stationSource.getLongitude()) {
                stationNotification.setChangeLongitude(stationSource.getLongitude());
                canAdd = true;
            }

            if (canAdd) {
                stationNotification.setCreatedTime(new Date());
                listStationVarious.add(this.stationNotification);
            }
        }

        @Override
        public void run() {
            equalStation();
        }
    }

}
