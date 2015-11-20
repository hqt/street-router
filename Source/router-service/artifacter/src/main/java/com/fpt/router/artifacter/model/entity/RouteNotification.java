package com.fpt.router.artifacter.model.entity;

import javax.persistence.*;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 11/8/15.
 */
@Entity
@Table(name = "RouteNotification")
@PrimaryKeyJoinColumn(name="NotificationID")
public class RouteNotification extends Notification {
   /* @Id
    @Column(name = "NotificationID", unique = true, nullable = false)
    private int notificationId;*/

    @Column(name = "ChangeRouteName", nullable = true)
    private String changeRouteName;

    @Column(name = "RouteNo", nullable = false)
    private int routeNo;

   /* @OneToOne
    @JoinColumn(name = "NotificationID")
    private Notification notification;*/

    @ManyToOne
    @JoinColumn(name = "RouteID", nullable = true)
    private Route route;

    public RouteNotification() {

    }

    /*public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }*/

    public int getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(int routeNo) {
        this.routeNo = routeNo;
    }

    public String getChangeRouteName() {
        return changeRouteName;
    }

    public void setChangeRouteName(String changeRouteName) {
        this.changeRouteName = changeRouteName;
    }

   /* public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }*/

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
