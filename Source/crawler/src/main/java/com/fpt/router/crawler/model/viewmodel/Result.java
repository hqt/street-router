package com.fpt.router.crawler.model.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class Result {
    double distance;
    double time;
    double k;
    List<Path> nodeList;

    public Result() {
        nodeList = new ArrayList<Path>();
    }
}
