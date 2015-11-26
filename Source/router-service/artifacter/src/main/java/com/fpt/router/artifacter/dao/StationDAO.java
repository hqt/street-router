package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.entity.Station;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Created by asus on 9/27/2015.
 */
public class StationDAO extends JPADaoImpl<Station, Integer> {

    public EntityManager getEntityManager() {
        EntityManagerFactory entityManagerFactory = JPADaoImpl.factory;
        return entityManagerFactory.createEntityManager();
    }

    public Station findStationByCodeID(String codeId) {

        String hql = "Select s from Station s where s.codeId= :codeId";

        Query query = getEntityManager().createQuery(hql);
        query.setParameter("codeId", codeId);
        Station s;
        s = (Station) query.getResultList().get(0);
        createEntityManager().close();
        return s;
    }

}
