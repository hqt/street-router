package com.fpt.router.artifacter.model.viewmodel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.deser.PeriodDeserializer;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class Result {
    public String code;
    public double totalDistance;
    @JsonDeserialize(using=PeriodDeserializer.class)
    public Period totalTime;
    public int minutes;
    public int totalTransfer;

    public List<INode> nodeList;

    public Result() {
        nodeList = new ArrayList<INode>();
    }
}
