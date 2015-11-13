package com.fpt.router.library.model.bus;

import org.joda.time.Period;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class Result implements Serializable {
    public String code;
    public double totalDistance;
    public Period totalTime;
    public int minutes;
    public int totalTransfer;
    public List<String> compare;

    public List<INode> nodeList;

    public Result() {
        nodeList = new ArrayList<INode>();
    }
}
