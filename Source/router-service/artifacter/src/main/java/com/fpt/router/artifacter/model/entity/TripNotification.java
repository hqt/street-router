package com.fpt.router.artifacter.model.entity;

import org.joda.time.LocalTime;

import javax.persistence.*;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 11/8/15.
 */
@Entity
@Table(name = "TripNotification")
@PrimaryKeyJoinColumn(name="NotificationID")
public class TripNotification extends Notification {

   /* @Id
    @Column(name = "NotificationID", unique = true, nullable = false)
    private int notificationID;*/

    @Column(name = "ChangeStartTime", nullable = true)
    private LocalTime changeStartTime;

    @Column(name = "ChangeEndTime", nullable = true)
    private LocalTime changeEndTime;

    @Column(name = "RouteNo", nullable = false)
    private int routeNo;

    @Column(name = "TripNo", nullable = false)
    private int tripNo;
/*
    @OneToOne
    @JoinColumn(name = "NotificationID")
    private Notification notification;*/

    @ManyToOne
    @JoinColumn(name = "TripID", nullable = false)
    private Trip trip;

    public TripNotification() {

    }

   /* public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }*/

    public int getTripNo() {
        return tripNo;
    }

    public void setTripNo(int tripNo) {
        this.tripNo = tripNo;
    }

    public int getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(int routeNo) {
        this.routeNo = routeNo;
    }

    public LocalTime getChangeStartTime() {
        return changeStartTime;
    }

    public void setChangeStartTime(LocalTime changeStartTime) {
        this.changeStartTime = changeStartTime;
    }

    public LocalTime getChangeEndTime() {
        return changeEndTime;
    }

    public void setChangeEndTime(LocalTime changeEndTime) {
        this.changeEndTime = changeEndTime;
    }

   /* public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }*/

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
