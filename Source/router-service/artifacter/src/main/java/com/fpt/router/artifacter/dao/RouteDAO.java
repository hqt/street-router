package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.algorithm.PathInfo;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.helper.RouteType;
import com.fpt.router.artifacter.utils.PaginationUtils;

import javax.persistence.*;
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

    public Route getRoutebyRouteNo(int routeNo, RouteType routeType) {
        String hql = "select distinct r from Route r where r.routeNo = :routeNo and r.routeType = :routeType";
        Query query = createEntityManager().createQuery(hql);
        query.setParameter("routeNo", routeNo);
        query.setParameter("routeType", routeType);
        Route route;
        try {
            route = (Route) query.getSingleResult();
        } catch (NonUniqueResultException ex) {
            System.out.println("Non unique result exception error at RouteDAO at row 42");
            route = null;
        } catch (NoResultException e) {
            System.out.println("No result found exception error at RouteDAO at row 42");
            route = null;
        }
        createEntityManager().close();
        return route;
    }

}
