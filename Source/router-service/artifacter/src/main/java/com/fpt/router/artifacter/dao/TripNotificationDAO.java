package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.entity.TripNotification;

import javax.persistence.Query;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 11/8/15.
 */
public class TripNotificationDAO extends JPADaoImpl<TripNotification, Integer> {

    public TripNotification readTripNof(int routeNo, int tripNo) {
        String hql = "select t from TripNotification t where t.routeNo= :routeNo and t.tripNo= :tripNo";
        Query query = createEntityManager().createQuery(hql);
        query.setParameter("routeNo", routeNo);
        query.setParameter("tripNo", tripNo);
        TripNotification tn;
        try {
            tn = (TripNotification) query.getSingleResult();
        } catch (Exception ex) {
            tn = null;
        }
        return tn;
    }

}
