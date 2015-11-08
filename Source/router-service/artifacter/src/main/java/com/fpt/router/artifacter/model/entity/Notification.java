package com.fpt.router.artifacter.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 11/8/15.
 */
@Entity
@Table(name = "Notification")
public class Notification implements IEntity {
    //region Hibernate field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationID", unique = true, nullable = false)
    private int notificationId;

    @Column(name = "CreatedTime", columnDefinition="DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    public Notification() {

    }

    public Notification(Date createdTime) {
        this.createdTime = createdTime;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
