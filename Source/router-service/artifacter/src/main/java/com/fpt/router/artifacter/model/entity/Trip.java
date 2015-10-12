package com.fpt.router.artifacter.model.entity;


import org.joda.time.LocalTime;

import javax.persistence.*;
import java.util.*;

/**
 * Created by asus on 9/26/2015.
 */
@Entity
@Table(name = "Trip")
public class Trip implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TridID", unique = true, nullable = false)
    private int tripId;

    @Column(name = "TripNo")
    private int tripNo;

    @Column(name = "StartTime")
    private LocalTime startTime;

    @Column(name = "EndTime")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "RouteID", nullable = false)
    private Route route;

    @OneToMany(mappedBy = "trip")
    private List<Connection> connections;

    public Trip() {
        connections = new ArrayList<Connection>();
    }


    public Trip(int tripNo, LocalTime startTime, LocalTime endTime, Route route) {
        this.tripNo = tripNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.route = route;
        connections = new ArrayList<Connection>();
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getTripNo() {
        return tripNo;
    }

    public void setTripNo(int tripNo) {
        this.tripNo = tripNo;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}
