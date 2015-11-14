package com.fpt.router.utils;

import android.util.Log;

import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.bus.Segment;
import com.fpt.router.library.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ngoan on 11/13/2015.
 */
public class CheckDuplicateUtils {
    public static List<Journey> checkDuplicateJourney(List<Journey> journeys) {
        List<Journey> journeyList = new ArrayList<>();
        journeyList = journeys;
        for (int i=0;i<journeyList.size();i++){
            Journey journey = journeyList.get(i);
            List<String> compareJourney = new ArrayList<>();
            List<Result> results = journey.results;
            for(int j=0;j<results.size();j++){
                Result result = results.get(j);
                List<INode> iNodeList = result.nodeList;
                for (int k = 0; k < iNodeList.size(); k++) {
                    if (iNodeList.get(k) instanceof Segment) {
                        Segment segment = (Segment) iNodeList.get(k);
                        compareJourney.add(String.valueOf(segment.routeNo));
                    }
                }
            }
            journey.busNoStrList = compareJourney;
            Log.e("hqthao", "go through bus list journey: " + journey.busNoStrList +
                    "\ttime: " + journey.minutes +
                    "\tdistance: " + journey.totalDistance);
        }

        for (int n = 0;n<journeyList.size()-1;n++){
            for (int m=n+1;m<journeyList.size();m++){
                List<String> one = journeyList.get(n).busNoStrList;
                List<String> two = journeyList.get(m).busNoStrList;
                boolean compare_equal = StringUtils.equalLists(one, two);
                if (compare_equal) {
                    journeyList.remove(m);
                    m--;
                }
            }
        }
        return journeyList;
    }

    public static List<Result> checkDuplicateResult(List<Result> results) {
        List<Result> resultList = new ArrayList<>();
        resultList = results;
        for (int i = 0; i < resultList.size(); i++) {
            Result result = resultList.get(i);
            List<String> compare = new ArrayList<>();
            List<INode> iNodeList = result.nodeList;
            for (int j = 0; j < iNodeList.size(); j++) {
                if (iNodeList.get(j) instanceof Segment) {
                    Segment segment = (Segment) iNodeList.get(j);
                    compare.add(String.valueOf(segment.routeNo));
                }
            }
            result.busNoStrList = compare;
            Log.e("hqthao", "go through bus list result: " + compare +
                    "\ttransfer: " + result.totalTransfer +
                    "\ttime: " + result.minutes +
                    "\tdistance: " + result.totalDistance);
        }

        for (int k = 0; k < resultList.size() - 1; k++) {
            for (int m = k + 1; m < resultList.size(); m++) {
                List<String> one = resultList.get(k).busNoStrList;
                List<String> two = resultList.get(m).busNoStrList;
                boolean compare_equal = StringUtils.equalLists(one, two);
                if (compare_equal) {
                    resultList.remove(m);
                    m--;
                }
            }
        }

      return resultList;
    }

}
