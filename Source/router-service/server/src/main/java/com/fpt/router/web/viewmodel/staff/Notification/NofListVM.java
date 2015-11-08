package com.fpt.router.web.viewmodel.staff.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 11/6/2015.
 */
public class NofListVM {

    private List<NotificationVM> listNofActive;
    private List<NotificationVM> listNofInActive;

    public NofListVM() {
        listNofActive = new ArrayList<>();
        listNofInActive = new ArrayList<>();
    }

    public List<NotificationVM> getListNofInActive() {
        return listNofInActive;
    }

    public void setListNofInActive(List<NotificationVM> listNofInActive) {
        this.listNofInActive = listNofInActive;
    }

    public List<NotificationVM> getListNofActive() {
        return listNofActive;
    }

    public void setListNofActive(List<NotificationVM> listNofActive) {
        this.listNofActive = listNofActive;
    }
}
