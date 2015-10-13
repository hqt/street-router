package com.fpt.router.model.bus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by asus on 10/13/2015.
 */
public class BusDetailTwoPoint implements Serializable {
    private double totalDistance;
    private BusTotalTime totalTime;
    private int totalTransfer;
    private List<BusNode> busNodeList;
    public BusDetailTwoPoint(){

    }

    public BusDetailTwoPoint(double totalDistance, BusTotalTime totalTime, int totalTransfer, List<BusNode> busNodeList) {
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.totalTransfer = totalTransfer;
        this.busNodeList = busNodeList;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public BusTotalTime getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(BusTotalTime totalTime) {
        this.totalTime = totalTime;
    }

    public int getTotalTransfer() {
        return totalTransfer;
    }

    public void setTotalTransfer(int totalTransfer) {
        this.totalTransfer = totalTransfer;
    }

    public List<BusNode> getBusNodeList() {
        return busNodeList;
    }

    public void setBusNodeList(List<BusNode> busNodeList) {
        this.busNodeList = busNodeList;
    }
}
