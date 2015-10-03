package com.fpt.router.crawler.model.algorithm;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Huynh Quang Thao on 9/26/2015.
 */
public class Connection implements IAlgorithmModel {

    public long id;

    public Trip trip;

    public PathInfo pathInfo;

    public Date arrivalTime;

    public Connection(){

    }

    @Override
    public void build() {

    }
}


