package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.StationNotification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 11/16/2015.
 */
public class NofStationListVM {

    public List<NofStationVM> modelL;

    public NofStationListVM() {
        this.modelL = new ArrayList<NofStationVM>();
    }

    public NofStationListVM(List<StationNotification> entityL) {
        this.modelL = new ArrayList<NofStationVM>();
        for (StationNotification entity : entityL) {
            NofStationVM model = new NofStationVM(entity);
            model.buildNotification();
            modelL.add(model);
        }
    }

    public List<NofStationVM> getModelL() {
        return modelL;
    }

    public void setModelL(List<NofStationVM> modelL) {
        this.modelL = modelL;
    }
}
