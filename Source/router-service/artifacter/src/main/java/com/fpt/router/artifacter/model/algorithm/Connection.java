package com.fpt.router.artifacter.model.algorithm;

import org.joda.time.LocalTime;

import javax.persistence.*;

/**
 * Created by Huynh Quang Thao on 9/26/2015.
 */
public class Connection implements IAlgorithmModel {

    public int id;

    public Trip trip;

    public PathInfo pathInfo;

    public LocalTime arrivalTime;

    public LocalTime departureTime;

    public Connection(){

    }

    @Override
    public void build() {

    }
}


