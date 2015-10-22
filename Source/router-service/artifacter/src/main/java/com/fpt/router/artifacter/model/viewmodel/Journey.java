package com.fpt.router.artifacter.model.viewmodel;

import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/21/15.
 */
public class Journey {
    public String code;
    public Period totalTime;
    public int minutes;
    public double totalDistance;
    public List<Result> results;

    public Journey() {
        results = new ArrayList<Result>();
    }
}