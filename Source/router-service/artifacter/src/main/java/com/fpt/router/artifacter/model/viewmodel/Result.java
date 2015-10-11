package com.fpt.router.artifacter.model.viewmodel;

import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class Result {
    public double totalDistance;
    public Period totalTime;
    public int totalTransfer;

    public List<INode> nodeList;

    public Result() {
        nodeList = new ArrayList<INode>();
    }
}
