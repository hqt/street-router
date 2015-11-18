package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.entity.StationNotification;
import com.fpt.router.artifacter.model.entity.Trip;

import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 11/8/15.
 */
public class StationNotificationDAO extends JPADaoImpl<StationNotification, Integer> {

    public StationNotification readByCode(String code) {
        String hql = "select s from StationNotification s where s.stationCodeID= :code";
        Query query = createEntityManager().createQuery(hql);
        query.setParameter("code", code);
        StationNotification stationNotification;
        try {
            stationNotification = (StationNotification) query.getSingleResult();
        } catch (Exception ex) {
            stationNotification = null;
        }
        createEntityManager().close();
        return stationNotification;
    }

}
