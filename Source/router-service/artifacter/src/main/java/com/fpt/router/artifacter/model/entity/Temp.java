package com.fpt.router.artifacter.model.entity;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import javax.persistence.*;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/4/15.
 */
@Entity
@Table(name = "Temp")
public class Temp implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TempID", unique = true, nullable = false)
    private long tempID;

    @Column(name = "TempNo")
    private int TempNo;

    @Column(name = "Date")
    private LocalTime date;

    public Temp() {

    }

    public Temp(int tempNo, LocalTime date) {
        TempNo = tempNo;
        this.date = date;
    }

    public long getTempID() {
        return tempID;
    }

    public void setTempID(long tempID) {
        this.tempID = tempID;
    }

    public int getTempNo() {
        return TempNo;
    }

    public void setTempNo(int tempNo) {
        TempNo = tempNo;
    }

    public LocalTime getDate() {
        return date;
    }

    public void setDate(LocalTime date) {
        this.date = date;
    }
}
