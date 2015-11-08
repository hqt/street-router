package com.fpt.router.web.viewmodel.staff.Notification;

import com.fpt.router.artifacter.model.entity.Trip;
import com.fpt.router.web.viewmodel.staff.TripVM;
import org.joda.time.LocalTime;

/**
 * Created by datnt on 11/1/2015.
 */
public class TripNotificationVM extends NotificationVM {
    public TripVM tripServer;
    public TripVM tripDB;

    public TripNotificationVM(Trip tripServer, Trip tripDB) {
        this.tripServer = new TripVM(tripServer);
        this.tripDB = new TripVM(tripDB);
    }

}
