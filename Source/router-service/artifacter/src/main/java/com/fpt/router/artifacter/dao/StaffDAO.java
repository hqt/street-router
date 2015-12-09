package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.entity.RouteNotification;
import com.fpt.router.artifacter.model.entity.Staff;
import org.hibernate.SQLQuery;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 11/8/15.
 */
public class StaffDAO extends JPADaoImpl<Staff, Integer> {
// 16449c760b5d61e0ba28514b3738a1af
    public Staff findStaffByEmail(String email) {
        String hql = "select * from Staff where staffEmail =?";
        // Query query = createEntityManager().createNativeQuery(hql);
        TypedQuery<Staff> query = createEntityManager().createQuery(" from Staff", Staff.class);
        //query.setParameter(1, email);


        Staff staff = null;
        try {
             //List<Staff> staffs =  query.get();
            //staff = staffs.get(0);
            System.out.println(staff.getStaffEmail());
            System.out.println(staff.getPhoneNumber());
            System.out.println(staff.getStaffId());
            System.out.println(staff.getStaffName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        createEntityManager().close();
        return staff;
    }

}
