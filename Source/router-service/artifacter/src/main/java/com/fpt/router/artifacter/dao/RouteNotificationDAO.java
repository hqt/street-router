package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.entity.RouteNotification;
import com.fpt.router.artifacter.model.helper.RouteType;

import javax.persistence.Query;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 11/8/15.
 */
public class RouteNotificationDAO extends JPADaoImpl<RouteNotification, Integer> {

    public RouteNotification readRouteNofByRouteNo(int routeNo, RouteType routeType) {
        String hql = "select r from RouteNotification r where r.routeNo= :routeNo and r.routeType= :routeType";
        Query query = createEntityManager().createQuery(hql);
        query.setParameter("routeNo", routeNo);
        query.setParameter("routeType", routeType);
        RouteNotification routeNof;
        try {
            routeNof = (RouteNotification) query.getSingleResult();
        } catch (Exception ex) {
            System.out.println("In " + RouteNotification.class.getName() + " throw exception NonUniqueResult");
            routeNof = null;
        }
        return routeNof;
    }

}
