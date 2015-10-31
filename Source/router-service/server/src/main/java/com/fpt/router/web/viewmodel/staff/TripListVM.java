package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 10/28/2015.
 */
public class TripListVM {

    private List<TripVM> tripVMList;

    public TripListVM(List<Trip> trips) {
        this.tripVMList = new ArrayList<>();
        for (Trip trip : trips) {
            this.tripVMList.add(new TripVM(trip));
        }
    }

    public List<TripVM> getTripVMList() {
        return tripVMList;
    }

    public void setTripVMList(List<TripVM> tripVMList) {
        this.tripVMList = tripVMList;
    }
}
