package com.fpt.router.web.action.notification.route;

import com.fpt.router.artifacter.dao.RouteNotificationDAO;
import com.fpt.router.artifacter.model.entity.RouteNotification;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by datnt on 11/18/2015.
 */
public class RouteNofAddThread {

    private List<RouteNotification> routeNofs;
    private RouteNotificationDAO dao;

    public RouteNofAddThread(List<RouteNotification> items) {
        this.routeNofs = items;
        dao = new RouteNotificationDAO();
    }

    public void run() {
        processAddThread();
    }

    public void processAddThread() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (RouteNotification routeNof : this.routeNofs) {
            InsertThread thread = new InsertThread(routeNof);
            executor.execute(thread);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Main Thread insert route notification is interrupted");
            e.printStackTrace();
        }
    }

    private class InsertThread implements Runnable{

        private RouteNotification routeNof;

        public InsertThread(RouteNotification route) {
            this.routeNof = route;
        }

        @Override
        public void run() {
            routeInsertThread();
        }

        public void routeInsertThread() {
            boolean canAdd = existed();
            if (canAdd) {
                System.out.println("Insert Route Notification thread with no: " + routeNof.getRouteNo());
                dao.create(routeNof);
            }
        }

        // compare the change between routenof and existed route in db
        // if nothing change -> just update createtime,
        // if some thing change -> update route nof with this change and create time
        // if not exist -> can insert
        public boolean existed() {
            boolean canAdd = false;

            RouteNotification existed = dao.readRouteNofByRouteNo(this.routeNof.getRouteNo(), this.routeNof.getRouteType());

            if (existed == null) {
                canAdd = true;
            } else {
                boolean canUpdate = false;

                String existedName = existed.getChangeRouteName();
                String routeNofName = routeNof.getChangeRouteName();
                if (!existedName.equals(routeNofName)) {
                    canUpdate = true;
                }

                if (canUpdate) {
                    System.out.println("Update Route Notification thread with no: " + routeNof.getRouteNo());
                    routeNof.setCreatedTime(new Date());
                    dao.update(routeNof);
                }
            }

            return canAdd;
        }
    }

}
