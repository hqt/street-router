package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 10/12/2015.
 */
public class StationListVM {

    private List<StationVM> stationListVM;

    public StationListVM() {

    }

    public List<StationVM> getStationListVM() {
        return stationListVM;
    }

    public void setStationListVM(List<StationVM> stationListVM) {
        this.stationListVM = stationListVM;
    }

    public StationListVM(List<Station> stations) {
        stationListVM = new ArrayList<>();
        for (Station s : stations) {
            stationListVM.add(new StationVM(s));
        }
    }

}
