package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.entity.RouteNotification;
import com.fpt.router.artifacter.model.entity.Staff;

import javax.persistence.Query;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 11/8/15.
 */
public class StaffDAO extends JPADaoImpl<Staff, Integer> {
// 16449c760b5d61e0ba28514b3738a1af
    public Staff findStaffByEmail(String email) {
        String hql = "select s from Staff as s where s.staffEmail= :staffEmail1";
        Query query = createEntityManager().createQuery(hql);
        query.setParameter("staffEmail1", email);
        Staff staff;
        try {
            staff = (Staff) query.getSingleResult();
        } catch (Exception ex) {
            staff = null;
        }
        createEntityManager().close();
        return staff;
    }

}
