package com.fpt.router.web.viewmodel.admin;

import java.util.List;

/**
 * Created by datnt on 11/5/2015.
 */
public class StaffListVM {

    private List<StaffVM> staffVMList;

    public StaffListVM(){

    }

    public List<StaffVM> getStaffVMList() {
        return staffVMList;
    }

    public void setStaffVMList(List<StaffVM> staffVMList) {
        this.staffVMList = staffVMList;
    }
}
