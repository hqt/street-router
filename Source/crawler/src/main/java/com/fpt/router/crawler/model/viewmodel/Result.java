package com.fpt.router.crawler.model.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/4/15.
 */
public class Result {
    public double distance;
    public double time;
    public int k;
    public List<Path> nodeList;

    public Result() {
        nodeList = new ArrayList<Path>();
    }
}
