package com.fpt.router.model;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by asus on 9/26/2015.
 */
@Entity
@Table(name = "Trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TridID", unique = true, nullable = false)
    private long tripId;
    @Column(name = "TripNo")
    private int tripNo;
    @Column(name = "StartTime")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Column(name = "EndTime")
    @Temporal(TemporalType.TIME)
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "RouteID", nullable = false)
    private Route route;

    @OneToMany(mappedBy = "trip")
    private Set<Connection> connections;
    public Trip(){

    }



    public Trip(Date startTime, Date endTime, Route route){
        this.startTime = startTime;
        this.endTime = endTime;
        this.route = route;
    }



    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public int getTripNo() {
        return tripNo;
    }

    public void setTripNo(int tripNo) {
        this.tripNo = tripNo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Set<Connection> getConnections() {
        return connections;
    }

    public void setConnections(Set<Connection> connections) {
        this.connections = connections;
    }
}
