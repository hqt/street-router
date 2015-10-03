package com.fpt.router.crawler.model.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by asus on 9/26/2015.
 */
@Entity
@Table(name = "Connection")
public class Connection implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConnectionID", unique = true, nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "TripID")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "PathInfoID")
    private PathInfo pathInfo;

    @Column(name = "ArrivalTime")
    @Temporal(TemporalType.TIME)
    private Date arrivalTime;

    public Connection(){

    }
    public Connection(Trip trip, PathInfo pathInfo, Date arrivalTime){
        this.trip = trip;
        this.pathInfo = pathInfo;
        this.arrivalTime = arrivalTime;
    }

    //region getter/setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    //endregion
}


