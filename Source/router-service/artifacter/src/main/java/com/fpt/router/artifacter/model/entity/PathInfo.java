package com.fpt.router.artifacter.model.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by asus on 9/26/2015.
 */
@Entity
@Table(name = "PathInfo")
public class PathInfo implements IEntity {

    //region Hibernate field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PathInfoID",unique = true,nullable = false)
    private int pathInfoId;

    @Column(name = "PathInfoNo",nullable = false)
    private int pathInfoNo;

    @Column(name = "MiddlePoint")
    private String middleLocations;

    @ManyToOne
    @JoinColumn(name = "RouteID")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "FromStationID")
    private Station from;

    @ManyToOne
    @JoinColumn(name = "ToStationID")
    private Station to;

    @OneToMany(mappedBy = "pathInfo")
    private List<Connection> connections;

    //endregion

    public PathInfo(){

    }

    public PathInfo(Route route, Station from, Station to, int pathInfoNo, String middleLocations){
        this.route = route;
        this.from = from;
        this.to = to;
        this.pathInfoNo = pathInfoNo;
        this.middleLocations = middleLocations;
    }




    //region getter setter
    public int getPathInfoId() {
        return pathInfoId;
    }

    public void setPathInfoId(int pathInfoId) {
        this.pathInfoId = pathInfoId;
    }

    public int getOrder() {
        return pathInfoNo;
    }

    public void setOrder(int order) {
        this.pathInfoNo = order;
    }

    public String getMiddleLocations() {
        return middleLocations;
    }

    public void setMiddleLocations(String middleLocations) {
        this.middleLocations = middleLocations;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Station getFrom() {
        return from;
    }

    public void setFrom(Station from) {
        this.from = from;
    }

    public Station getTo() {
        return to;
    }

    public void setTo(Station to) {
        this.to = to;
    }

    public int getPathInfoNo() {
        return pathInfoNo;
    }

    public void setPathInfoNo(int pathInfoNo) {
        this.pathInfoNo = pathInfoNo;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    //endregion
}
