package com.fpt.router.web.viewmodel.api;

import com.fpt.router.artifacter.model.viewmodel.Journey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 10/22/2015.
 */
public class ListJourneyVM {

    private List<JourneyVM> journeyVMList;

    public List<JourneyVM> getJourneyVMList() {
        return journeyVMList;
    }

    public void setJourneyVMList(List<JourneyVM> journeyVMList) {
        this.journeyVMList = journeyVMList;
    }

    public ListJourneyVM(List<Journey> journeys) {
        journeyVMList = new ArrayList<JourneyVM>();
        for (Journey j : journeys) {
            journeyVMList.add(new JourneyVM(j));
        }

    }
}
