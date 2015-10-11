package com.fpt.router.artifacter.model.viewmodel;

import com.fpt.router.artifacter.model.helper.PathType;
import org.joda.time.Period;

import java.util.List;

/**
 * Purpose: a segment is a collection of stations on same route on that journey
 * Created by Huynh Quang Thao on 10/10/15.
 */
public class Segment implements INode {
    // route information
    public int routeId;
    public String routeName;
    public int routeNo;

    // the i-th transfer of the journey
    public int tranferNo;

    public PathType pathType;

    public double segmentDistance;

    public Period segmentTime;

    public List<Path> paths;
}
