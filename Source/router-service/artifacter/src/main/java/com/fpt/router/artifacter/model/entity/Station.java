package com.fpt.router.artifacter.model.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by asus on 9/26/2015.
 */
@Entity
@Table(name = "Station")
public class Station implements IEntity {
    @Id
    @Column(name = "StationID", unique = true, nullable = false)
    private int stationId;

    @Column(name = "CodeID", nullable = false)
    private String codeId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Street", nullable = false)
    private String street;

    @Column(name = "Latitude", nullable = false)
    private double latitude;

    @Column(name = "Longitude", nullable = false)
    private double longitude;

    @OneToMany(mappedBy = "from")
    private List<PathInfo> from;

    @OneToMany(mappedBy = "to")
    private List<PathInfo> to;

    public Station(){

    }

    public Station(int stationId, String codeId, String name, String street, double latitude, double longitude) {
        this.stationId = stationId;
        this.codeId = codeId;
        this.name = name;
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<PathInfo> getFrom() {
        return from;
    }

    public void setFrom(List<PathInfo> from) {
        this.from = from;
    }

    public List<PathInfo> getTo() {
        return to;
    }

    public void setTo(List<PathInfo> to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj instanceof Station){
            Station station = (Station) obj;
            return station.codeId.equals(this.codeId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode = getCodeId().hashCode();
        return hashCode;
    }
}
