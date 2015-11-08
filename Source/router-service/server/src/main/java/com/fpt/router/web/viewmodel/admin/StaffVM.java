package com.fpt.router.web.viewmodel.admin;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/12/15.
 */
public class StaffVM {

    private int id;
    private String fullName;
    private String accountName;
    private String email;
    private String password;
    private String phoneNumber;

    public StaffVM() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StaffVM(int id, String fullName, String accountName, String email, String password, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.accountName = accountName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
