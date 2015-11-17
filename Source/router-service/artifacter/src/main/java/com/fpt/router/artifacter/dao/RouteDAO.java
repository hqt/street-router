package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.algorithm.PathInfo;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.utils.PaginationUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 9/27/2015.
 */
public class RouteDAO extends JPADaoImpl<Route, Integer> {

    public EntityManager getEntityManager() {
        EntityManagerFactory factory = JPADaoImpl.factory;
        EntityManager entityManager = factory.createEntityManager();
        return entityManager;
    }

    public Route getRouteLazy(int id) {
        return getEntityManager().find(Route.class, id);
    }

    public List<Route> findAllRouteLazy() {
        return getEntityManager().createQuery("from " + entityClass.getName()).getResultList().subList(0, 10);
    }

    public Route getRoutebyRouteNo(int routeNo) {
        String hql = "select r from Route r where r.routeNo= :routeNo";
        Query query = createEntityManager().createQuery(hql);
        query.setParameter("routeNo", routeNo);
        Route route = (Route) query.getSingleResult();
        createEntityManager().close();
        return route;
    }

}
