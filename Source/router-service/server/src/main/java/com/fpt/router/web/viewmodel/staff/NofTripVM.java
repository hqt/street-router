package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.TripNotification;
import org.joda.time.LocalTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by datnt on 11/19/2015.
 */
public class NofTripVM {

    public int tripId;
    public int tripNo;
    public int routeNo;
    public LocalTime changeStartTime;
    public LocalTime changeEndTime;
    public String viewStartTime;
    public String viewEndTime;

    public String notification;

    public NofTripVM(TripNotification tripNof) {
        if (tripNof.getTrip() != null) {
            this.tripId = tripNof.getTrip().getTripId();
        }
        this.tripNo = tripNof.getTripNo();
        this.routeNo = tripNof.getRouteNo();
        this.changeStartTime = tripNof.getChangeStartTime();
        this.changeEndTime = tripNof.getChangeEndTime();
        if (changeStartTime != null) {
            this.viewStartTime = convertLocalTimeToString(changeStartTime);
        } else {
            this.viewStartTime = null;
        }
        if (changeEndTime != null) {
            this.viewEndTime = convertLocalTimeToString(changeEndTime);
        } else {
            this.viewEndTime = null;
        }
    }

    public void buildNotification() {
        String startTime = viewStartTime == null ? "" : "thời gian khởi hành " + viewStartTime + ", ";
        String endTime = viewEndTime ==  null ? "" : "thời gian đến trạm " + viewEndTime + ", ";

        this.notification = "Có thay đổi: " + startTime + endTime;
    }

    public String convertLocalTimeToString(LocalTime localTime) {
        Date date = localTime.toDateTimeToday().toDate();
        String patternTime = "h:mm a";
        SimpleDateFormat simpleTime = new SimpleDateFormat(patternTime);
        return simpleTime.format(date);
    }

    public String getViewStartTime() {
        return viewStartTime;
    }

    public void setViewStartTime(String viewStartTime) {
        this.viewStartTime = viewStartTime;
    }

    public String getViewEndTime() {
        return viewEndTime;
    }

    public void setViewEndTime(String viewEndTime) {
        this.viewEndTime = viewEndTime;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
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
}
