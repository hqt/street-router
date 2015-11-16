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

    public Staff findStaffByEmail(String email, String password) {
        String hql = "select s from Staff s where s.staffEmail= :staffEmail and s.password= :password";
        Query query = createEntityManager().createQuery(hql);
        query.setParameter("staffEmail", email);
        query.setParameter("password", password);
        createEntityManager().close();
        return (Staff) query.getSingleResult();
    }

}
