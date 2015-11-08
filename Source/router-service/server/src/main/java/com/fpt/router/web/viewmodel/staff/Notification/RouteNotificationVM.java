package com.fpt.router.web.viewmodel.staff.Notification;

/**
 * Created by datnt on 11/1/2015.
 */
public class RouteNotificationVM extends NotificationVM {

    public String routeNameServer;
    public String routeNameDB;

    public RouteNotificationVM(String routeNameServer, String routeNameDB) {
        this.routeNameServer = routeNameServer;
        this.routeNameDB = routeNameDB;
    }
}
