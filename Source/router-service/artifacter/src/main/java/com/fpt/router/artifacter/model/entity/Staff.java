package com.fpt.router.artifacter.model.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 11/8/15.
 */
@Entity
@Table(name = "Staff")
public class Staff implements IEntity {

    @Id
    @Column(name = "StaffID", unique = true, nullable = false)
    private int staffId;

    @Column(name = "StaffName", nullable = false)
    private String staffName;

    @Column(name = "StaffEmail", nullable = false)
    private String staffEmail;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Role", nullable = false)
    private int role;

    @Column(name = "PhoneNumber", nullable = false)
    private String phoneNumber;

    public Staff() {

    }

    public Staff(int staffId, String staffName, String staffEmail, String password, int role, String phoneNumber) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffEmail = staffEmail;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
