package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.entity.TripNotification;
import com.fpt.router.artifacter.model.helper.RouteType;

import javax.persistence.Query;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 11/8/15.
 */
public class TripNotificationDAO extends JPADaoImpl<TripNotification, Integer> {

    public TripNotification readTripNof(int routeNo, int tripNo, RouteType routeType) {
        String hql = "select t from TripNotification t where t.routeNo= :routeNo and t.tripNo= :tripNo and t.routeType= :routeType";
        Query query = createEntityManager().createQuery(hql);
        query.setParameter("routeNo", routeNo);
        query.setParameter("tripNo", tripNo);
        query.setParameter("routeType", routeType);
        TripNotification tn;
        try {
            tn = (TripNotification) query.getSingleResult();
        } catch (Exception ex) {
            tn = null;
        }
        return tn;
    }

}
