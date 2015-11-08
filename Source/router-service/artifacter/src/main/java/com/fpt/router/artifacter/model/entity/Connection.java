package com.fpt.router.artifacter.model.entity;

import org.joda.time.LocalTime;

import javax.persistence.*;

/**
 * Created by asus on 9/26/2015.
 */
@Entity
@Table(name = "Connection")
public class Connection implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConnectionID", unique = true, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "TripID")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "PathInfoID")
    private PathInfo pathInfo;

    @Column(name = "ArrivalTime")
    private LocalTime arrivalTime;

    @Column(name = "DepartureTime")
    private LocalTime departureTime;

    public Connection(){

    }
    public Connection(Trip trip, PathInfo pathInfo, LocalTime arrivalTime, LocalTime departureTime){
        this.trip = trip;
        this.pathInfo = pathInfo;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    //region getter/setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public PathInfo getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(PathInfo pathInfo) {
        this.pathInfo = pathInfo;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    //endregion
}


