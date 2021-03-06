package com.fpt.router.web.viewmodel.staff;

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
    private double longitude;

    public StationVM() {

    }

    public StationVM(long stationId, String stationName, String street, String codeId, double latitude, double longitude) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.street = street;
        this.codeId = codeId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public StationVM(Station station) {
        this.stationId = station.getStationId();
        this.stationName = station.getName();
        this.street = station.getStreet();
        this.codeId = station.getCodeId();
        this.latitude = station.getLatitude();
        this.longitude = station.getLongitude();
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
