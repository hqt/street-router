package com.fpt.router.web.viewmodel.api;

import com.fpt.router.artifacter.model.viewmodel.Journey;
import com.fpt.router.artifacter.model.viewmodel.Result;
import org.joda.time.Period;

import java.util.List;

/**
 * Created by datnt on 10/22/2015.
 */
public class JourneyVM {

    private String code;
    private Period totalTime;
    private int minutes;
    private double totalDistance;
    private List<Result> results;

    public JourneyVM() {

    }

    public JourneyVM(Journey journey) {
        this.code = journey.code;
        this.totalTime = journey.totalTime;
        this.minutes = journey.minutes;
        this.totalDistance = journey.totalDistance;
        this.results = journey.results;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Period getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Period totalTime) {
        this.totalTime = totalTime;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
