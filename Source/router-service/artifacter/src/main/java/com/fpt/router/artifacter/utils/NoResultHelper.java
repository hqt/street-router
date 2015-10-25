package com.fpt.router.artifacter.utils;

import com.fpt.router.artifacter.model.viewmodel.Journey;
import com.fpt.router.artifacter.model.viewmodel.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/25/15.
 */
public class NoResultHelper {
    public static List<Journey> NoJourneyFound(String message) {
        List<Journey> dummyJourneys = new ArrayList<Journey>();
        Journey journey = new Journey();
        journey.code = message;
        dummyJourneys.add(journey);
        return dummyJourneys;
    }

    public static List<Result> NoResultFound(String message) {
        List<Result> dummyResults = new ArrayList<Result>();
        Result result = new Result();
        result.code = message;
        dummyResults.add(result);
        return dummyResults;
    }
}
