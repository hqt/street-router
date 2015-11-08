package com.fpt.router.library.model.bus;

import org.joda.time.Period;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 10/21/15.
 */
public class Journey implements Serializable {
    public String code;
    public Period totalTime;
    public int minutes;
    public double totalDistance;
    public List<Result> results;


}
