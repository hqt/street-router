package com.fpt.router.artifacter.model.entity;

import org.joda.time.LocalTime;

import javax.persistence.*;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 11/8/15.
 */
@Entity
@Table(name = "TripNotification")
public class TripNotification extends Notification {

    @Id
    @Column(name = "NotificationID", unique = true, nullable = false)
    private int notificationID;

    @Column(name = "ChangeStartTime", nullable = true)
    private LocalTime changeStartTime;

    @Column(name = "ChangeEndTime", nullable = true)
    private LocalTime changeEndTime;

    @OneToOne
    @JoinColumn(name = "NotificationID")
    private Notification notification;

    @ManyToOne
    @JoinColumn(name = "TripID", nullable = false)
    private Trip trip;

    public TripNotification() {

    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
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

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
