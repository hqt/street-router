package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.TripNotification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 11/19/2015.
 */
public class NofTripListVM {

    public List<NofTripVM> modelL;

    public NofTripListVM() {
        this.modelL = new ArrayList<NofTripVM>();
    }

    public List<NofTripVM> getModelL() {
        return modelL;
    }

    public void setModelL(List<NofTripVM> modelL) {
        this.modelL = modelL;
    }

    public NofTripListVM(List<TripNotification> entities) {
        this.modelL = new ArrayList<NofTripVM>();
        for (TripNotification entity : entities) {
            NofTripVM item = new NofTripVM(entity);
            item.buildNotification();
            modelL.add(item);
        }
    }

}
