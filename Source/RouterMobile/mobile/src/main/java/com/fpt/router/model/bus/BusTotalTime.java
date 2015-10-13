package com.fpt.router.model.bus;

import java.io.Serializable;

/**
 * Created by asus on 10/13/2015.
 */
public class BusTotalTime implements Serializable {
    private int hours;
    private int minutes;
    private int seconds;

    public BusTotalTime() {
    }

    public BusTotalTime(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
