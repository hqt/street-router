package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Trip;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by asus on 9/27/2015.
 */
public class TripDAO extends JPADaoImpl<Trip, Integer> {

    public EntityManager getEntityManager() {
        EntityManagerFactory factory = JPADaoImpl.factory;
        return factory.createEntityManager();
    }

    public List<Trip> getTripsByRouteId(int routeId) {
        // create hibernate query
        Route route = new Route();
        route.setRouteId(routeId);
        String tripHQL = "select t from Trip t where t.route = :route";
        Query hql = getEntityManager().createQuery(tripHQL);
        hql.setParameter("route", route);
        // get list trip
        List<Trip> trips = hql.getResultList();
        return trips;
    }

}
