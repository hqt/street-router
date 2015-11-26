package com.fpt.router.web.viewmodel.admin;

import com.fpt.router.artifacter.model.entity.Staff;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/12/15.
 */
public class StaffVM {

    private int id;
    private String staffName;
    private String accountName;
    private String email;
    private String password;
    private String phoneNumber;
    private int role;

    public StaffVM(Staff staff) {
        this.id = staff.getStaffId();
        this.staffName = staff.getStaffName();
        this.email = staff.getStaffEmail();
        this.phoneNumber = staff.getPhoneNumber();
        this.role = staff.getRole();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StaffVM(int id, String fullName, String accountName, String email, String password, String phoneNumber) {
        this.id = id;
        this.staffName = fullName;
        this.accountName = accountName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
