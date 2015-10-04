package com.fpt.router.crawler.model.algorithm;

import org.joda.time.LocalTime;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Huynh Quang Thao on 9/26/2015.
 */
public class Connection implements IAlgorithmModel {

    public long id;

    public Trip trip;

    public PathInfo pathInfo;

    public LocalTime arrivalTime;

    public Connection(){

    }

    @Override
    public void build() {

    }
}


