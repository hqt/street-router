package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.TripNotification;
import com.fpt.router.artifacter.model.helper.RouteType;
import org.joda.time.LocalTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by datnt on 11/19/2015.
 */
public class NofTripVM {

    public int nofId;
    public int tripId;
    public int tripNo;
    public int routeNo;
    public RouteType routeType;
    private String oldStartTime;
    private String oldEndTime;
    public LocalTime changeStartTime;
    public LocalTime changeEndTime;
    public String viewStartTime;
    public String viewEndTime;

    public String notification;

    public NofTripVM(TripNotification tripNof) {
        this.nofId = tripNof.getNotificationId();
        if (tripNof.getTrip() != null) {
            this.tripId = tripNof.getTrip().getTripId();
        }
        this.tripNo = tripNof.getTripNo();
        this.routeNo = tripNof.getRouteNo();
        this.routeType = tripNof.getRouteType();
        this.changeStartTime = tripNof.getChangeStartTime();
        this.changeEndTime = tripNof.getChangeEndTime();
        if (tripNof.getTrip() != null) {
            this.oldStartTime = convertLocalTimeToString(tripNof.getTrip().getStartTime());
            this.oldEndTime = convertLocalTimeToString(tripNof.getTrip().getEndTime());
        }
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
        String startTime = viewStartTime == null ? "" : "thời gian khởi hành từ " + oldStartTime + " thành " + viewStartTime;
        String endTime = viewEndTime ==  null ? "" : ", thời gian đến trạm từ " + oldEndTime + " thành "+ viewEndTime;

        this.notification = "Có thay đổi~ " + startTime + endTime;
    }

    public String convertLocalTimeToString(LocalTime localTime) {
        Date date = localTime.toDateTimeToday().toDate();
        String patternTime = "h:mm a";
        SimpleDateFormat simpleTime = new SimpleDateFormat(patternTime);
        return simpleTime.format(date);
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public String getOldStartTime() {
        return oldStartTime;
    }

    public void setOldStartTime(String oldStartTime) {
        this.oldStartTime = oldStartTime;
    }

    public String getOldEndTime() {
        return oldEndTime;
    }

    public void setOldEndTime(String oldEndTime) {
        this.oldEndTime = oldEndTime;
    }

    public int getNofId() {
        return nofId;
    }

    public void setNofId(int nofId) {
        this.nofId = nofId;
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
