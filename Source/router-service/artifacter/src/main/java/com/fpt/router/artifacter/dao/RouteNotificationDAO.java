package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.entity.RouteNotification;

import javax.persistence.Query;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 11/8/15.
 */
public class RouteNotificationDAO extends JPADaoImpl<RouteNotification, Integer> {

    public RouteNotification readRouteNofByRouteNo(int routeNo) {
        String hql = "select r from RouteNotification r where r.routeNo= :routeNo";
        Query query = createEntityManager().createQuery(hql);
        query.setParameter("routeNo", routeNo);
        RouteNotification routeNof;
        try {
            routeNof = (RouteNotification) query.getSingleResult();
        } catch (Exception ex) {
            routeNof = null;
        }
        return routeNof;
    }

}
