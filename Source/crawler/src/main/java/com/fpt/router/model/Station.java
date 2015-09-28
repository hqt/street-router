package com.fpt.router.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by asus on 9/26/2015.
 */
@Entity
@Table(name = "Station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StationID", unique = true, nullable = false)
    private long stationId;
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

    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    private Set<PathInfo> from;
    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL)
    private Set<PathInfo> to;
    public Station(){

    }

    public Station(String codeId, String name, String street, double latitude, double longitude){
        this.codeId = codeId;
        this.name = name;
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
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

    public Set<PathInfo> getFrom() {
        return from;
    }

    public void setFrom(Set<PathInfo> from) {
        this.from = from;
    }

    public Set<PathInfo> getTo() {
        return to;
    }

    public void setTo(Set<PathInfo> to) {
        this.to = to;
    }
}