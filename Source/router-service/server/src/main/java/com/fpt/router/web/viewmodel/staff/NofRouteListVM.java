package com.fpt.router.web.viewmodel.staff;

import com.fpt.router.artifacter.model.entity.RouteNotification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 11/20/2015.
 */
public class NofRouteListVM {

    public List<NofRouteVM> nofRouteVMs;

    public NofRouteListVM() {
        this.nofRouteVMs = new ArrayList<NofRouteVM>();
    }
    
    public NofRouteListVM(List<RouteNotification> entities) {
        this.nofRouteVMs = new ArrayList<NofRouteVM>();
        for (RouteNotification entity : entities) {
            nofRouteVMs.add(new NofRouteVM(entity));
        }
    }

    public List<NofRouteVM> getNofRouteVMs() {
        return nofRouteVMs;
    }

    public void setNofRouteVMs(List<NofRouteVM> nofRouteVMs) {
        this.nofRouteVMs = nofRouteVMs;
    }
}
