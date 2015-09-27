package com.fpt.router.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by asus on 9/26/2015.
 */
@Entity
@Table(name = "PathInfo")
public class PathInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PathInfoID",unique = true,nullable = false)
    private long pathInfoId;
    @Column(name = "Order1",nullable = false)
    private int order1;

    @Column(name = "MiddlePoint")
    private String middleLocations;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RouteID")
    private Route route;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FromStationID")
    private Station from;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ToStationID")
    private Station to;

    @OneToMany(mappedBy = "pathInfo")
    private Set<Connection> connections;

    public PathInfo(){

    }

    public PathInfo(Route route, Station from, Station to, int order1, String middleLocations){
        this.route = route;
        this.from = from;
        this.to = to;
        this.order1 = order1;
        this.middleLocations = middleLocations;
    }

    public long getPathInfoId() {
        return pathInfoId;
    }

    public void setPathInfoId(long pathInfoId) {
        this.pathInfoId = pathInfoId;
    }

    public int getOrder() {
        return order1;
    }

    public void setOrder(int order) {
        this.order1 = order;
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

    public Set<Connection> getConnections() {
        return connections;
    }

    public void setConnections(Set<Connection> connections) {
        this.connections = connections;
    }
}
