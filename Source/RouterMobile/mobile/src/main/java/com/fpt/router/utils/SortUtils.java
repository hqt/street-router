package com.fpt.router.utils;

import com.fpt.router.framework.PrefStore;
import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.bus.Journey;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.motorbike.Leg;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 11/13/15.
 */
public class SortUtils {

    public static void sortJourney(List<Journey> journeys) {
        int type = PrefStore.getBusSortType();
        Comparator<Journey> comparator = null;

        switch (type) {
            case 0:
            case 1:
                comparator = new Comparator<Journey>() {
                    @Override
                    public int compare(Journey lhs, Journey rhs) {
                        if (lhs.minutes != rhs.minutes) {
                            return lhs.minutes - rhs.minutes;
                        }
                        return (int) (lhs.totalDistance - rhs.totalDistance);
                    }
                };
                break;

            case 2:
                comparator = new Comparator<Journey>() {
                    @Override
                    public int compare(Journey lhs, Journey rhs) {
                        if (Math.abs(lhs.totalDistance - rhs.totalDistance) > AppConstants.EPS) {
                            // not very true. but look ok
                            return (int) (lhs.totalDistance - rhs.totalDistance);
                        }

                        return lhs.minutes - rhs.minutes;
                    }
                };
                break;
        }

        Collections.sort(journeys, comparator);

    }

    public static void sortResult(List<Result> results) {
        int type = PrefStore.getBusSortType();
        Comparator<Result> comparator = null;

        switch (type) {
            case 0:
                comparator = new Comparator<Result>() {
                    @Override
                    public int compare(Result lhs, Result rhs) {
                        if (lhs.totalTransfer != rhs.totalTransfer) {
                            return lhs.totalTransfer - rhs.totalTransfer;
                        }
                        if (lhs.minutes != rhs.minutes) {
                            return lhs.minutes - rhs.minutes;
                        }
                        return (int) (lhs.totalDistance - rhs.totalDistance);

                    }
                };
                break;
            case 1:
                comparator = new Comparator<Result>() {
                    @Override
                    public int compare(Result lhs, Result rhs) {
                        if (lhs.minutes != rhs.minutes) {
                            return lhs.minutes - rhs.minutes;
                        }
                        return (int) (lhs.totalDistance - rhs.totalDistance);
                    }
                };
                break;

            case 2:
                comparator = new Comparator<Result>() {
                    @Override
                    public int compare(Result lhs, Result rhs) {
                        if (Math.abs(lhs.totalDistance - rhs.totalDistance) > AppConstants.EPS) {
                            // not very true. but look ok
                            return (int) (lhs.totalDistance - rhs.totalDistance);
                        }

                        return lhs.minutes - rhs.minutes;
                    }
                };
                break;
        }

        Collections.sort(results, comparator);

    }

}
