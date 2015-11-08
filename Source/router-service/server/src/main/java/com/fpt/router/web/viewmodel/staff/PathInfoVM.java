package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.PathInfo;

/**
 * Created by datnt on 10/28/2015.
 */
public class PathInfoVM {

    private int pathInfoId;
    private int pathInfoNo;
    private String middleLocations;
    private StationVM from;
    private StationVM to;

    public PathInfoVM(PathInfo pathInfo) {
        this.pathInfoId = pathInfo.getPathInfoId();
        this.pathInfoNo = pathInfo.getPathInfoNo();
        this.middleLocations = pathInfo.getMiddleLocations();
        if (pathInfo.getFrom() != null) {
            this.from = new StationVM(pathInfo.getFrom());
        }
        if (pathInfo.getTo() != null) {
            this.to = new StationVM(pathInfo.getTo());
        }
    }

    public int getPathInfoId() {
        return pathInfoId;
    }

    public void setPathInfoId(int pathInfoId) {
        this.pathInfoId = pathInfoId;
    }

    public int getPathInfoNo() {
        return pathInfoNo;
    }

    public void setPathInfoNo(int pathInfoNo) {
        this.pathInfoNo = pathInfoNo;
    }

    public String getMiddleLocations() {
        return middleLocations;
    }

    public void setMiddleLocations(String middleLocations) {
        this.middleLocations = middleLocations;
    }

    public StationVM getFrom() {
        return from;
    }

    public void setFrom(StationVM from) {
        this.from = from;
    }

    public StationVM getTo() {
        return to;
    }

    public void setTo(StationVM to) {
        this.to = to;
    }
}
