package com.hqt.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: a single result
 * Created by Huynh Quang Thao on 9/30/15.
 */
public class Result {
    double distance;
    double time;
    double k;
    List<Node> nodeList;

    public Result() {
        nodeList = new ArrayList<Node>();
    }

}
