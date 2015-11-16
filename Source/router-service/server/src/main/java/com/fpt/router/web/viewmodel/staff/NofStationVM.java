package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.StationNotification;

/**
 * Created by datnt on 11/16/2015.
 */
public class NofStationVM {

    public int nofId;
    public int stationId;
    public String codeId;
    public String changeName;
    public String changeStreet;
    public double latitude;
    public double longitude;

    public String notification;

    public void buildNotification() {

        String name = (changeName == null ? "" : "tên trạm " + changeName + ", ");
        String street = (changeStreet == null ? "" : "tên đường " + changeStreet + ", ");
        String lat =  (latitude == 0.0 ? "" : ("vĩ độ thành "  + latitude + ", "));
        String longti = (longitude == 0.0 ? "" : ("kinh độ thành " + longitude));
        this.notification = "Có thay đổi " + name  + street
                 + lat + longti;
    }

    public NofStationVM(StationNotification entity) {
        this.nofId = entity.getNotificationId();
        this.stationId = entity.getStation().getStationId();
        this.codeId = entity.getStation().getCodeId();
        this.changeName = entity.getChangeName();
        this.changeStreet = entity.getChangeStreet();
        this.latitude = entity.getChangeLatitude();
        this.longitude = entity.getChangeLongitude();
    }

    public int getNofId() {
        return nofId;
    }

    public void setNofId(int nofId) {
        this.nofId = nofId;
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

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
