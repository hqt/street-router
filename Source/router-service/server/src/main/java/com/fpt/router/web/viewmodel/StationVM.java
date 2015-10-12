package com.fpt.router.web.viewmodel;

import com.fpt.router.artifacter.model.entity.Station;

/**
 * Created by datnt on 10/11/2015.
 */
public class StationVM {

    private String stationName;
    private long stationId;
    private String street;
    private String codeId;
    private double latitude;
    private double longtitude;

    public StationVM() {

    }

    public StationVM(long stationId, String stationName, String street, String codeId, double latitude, double longtitude) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.street = street;
        this.codeId = codeId;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public StationVM(Station station) {
        this.stationId = station.getStationId();
        this.stationName = station.getName();
        this.street = station.getStreet();
        this.codeId = station.getCodeId();
        this.latitude = station.getLatitude();
        this.longtitude = station.getLongitude();
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

}
