package com.fpt.router.artifacter.model.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 11/8/15.
 */
@Entity
@Table(name = "StationNotification")
@PrimaryKeyJoinColumn(name="NotificationID")
public class StationNotification extends Notification {

   /* @Id
    @Column(name = "NotificationID", unique = true, nullable = false)
    private int notificationID;*/

    @Column(name = "ChangeName", nullable = true)
    private String changeName;

    @Column(name = "ChangeStreet", nullable = true)
    private String changeStreet;

    @Column(name = "ChangeLatitude", nullable = true)
    private double changeLatitude;

    @Column(name = "ChangeLongitude", nullable = true)
    private double changeLongitude;

    @Column(name = "StationCodeID", nullable = false)
    private String stationCodeID;

    @Column(name = "Type", nullable = false)
    private int type;

   /* @OneToOne
    @JoinColumn(name = "NotificationID")
    private Notification notification;*/

    @ManyToOne
    @JoinColumn(name = "StationID", nullable = false)
    private Station station;

    public StationNotification() {

    }

/*
    public StationNotification(String changeName, String changeStreet,
                               double changeLatitude, double changeLongitude, String stationCodeID, int type) {
        this.changeName =  changeName;
        this.changeStreet = changeStreet;
        this.changeLatitude = changeLatitude;
        this.changeLongitude = changeLongitude;
        this.stationCodeID = stationCodeID;
        this.type = type;
    }
*/

    /*public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }*/

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStationCodeID() {
        return stationCodeID;
    }

    public void setStationCodeID(String stationCodeID) {
        this.stationCodeID = stationCodeID;
    }

    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }

    public String getChangeStreet() {
        return changeStreet;
    }

    public void setChangeStreet(String changeStreet) {
        this.changeStreet = changeStreet;
    }

    public double getChangeLatitude() {
        return changeLatitude;
    }

    public void setChangeLatitude(double changeLatitude) {
        this.changeLatitude = changeLatitude;
    }

    public double getChangeLongitude() {
        return changeLongitude;
    }

    public void setChangeLongitude(double changeLongitude) {
        this.changeLongitude = changeLongitude;
    }

       /* public Notification getNotification() {
            return notification;
        }

        public void setNotification(Notification notification) {
            this.notification = notification;
        }*/

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
