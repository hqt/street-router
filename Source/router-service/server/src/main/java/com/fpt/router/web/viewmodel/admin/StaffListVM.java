package com.fpt.router.web.viewmodel.admin;

import com.fpt.router.artifacter.model.entity.Staff;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 11/5/2015.
 */
public class StaffListVM {

    private List<StaffVM> staffVMList;

    public StaffListVM(List<Staff> entites) {
        this.staffVMList = new ArrayList<StaffVM>();
        for (Staff entity : entites) {
            this.staffVMList.add(new StaffVM(entity));
        }
    }

    public List<StaffVM> getStaffVMList() {
        return staffVMList;
    }

    public void setStaffVMList(List<StaffVM> staffVMList) {
        this.staffVMList = staffVMList;
    }
}
