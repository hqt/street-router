package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.RouteNotification;
import com.fpt.router.artifacter.model.helper.RouteType;

/**
 * Created by datnt on 11/20/2015.
 */
public class NofRouteVM {

    public int notificationId;
    public int routeId;
    public int routeNo;
    public RouteType routeType;
    public String changeRouteName;
    public String notification;

    public NofRouteVM(RouteNotification nof) {
        this.notificationId = nof.getNotificationId();
        this.routeId = nof.getRoute().getRouteId();
        this.routeNo = nof.getRouteNo();
        this.changeRouteName = nof.getChangeRouteName();
        this.routeType = nof.getRouteType();
    }

    public void buildNotification() {
        String change = changeRouteName == null ? "" : "tên chuyến thành " + changeRouteName;
        this.notification = "Có thay đổi: " + change;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public int getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(int routeNo) {
        this.routeNo = routeNo;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getChangeRouteName() {
        return changeRouteName;
    }

    public void setChangeRouteName(String changeRouteName) {
        this.changeRouteName = changeRouteName;
    }
}
