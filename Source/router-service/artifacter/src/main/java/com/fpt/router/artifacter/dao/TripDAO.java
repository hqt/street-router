package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.entity.Connection;
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

    public List<Trip> getTripsByRoute(Route route) {
        String tripHQL = "select t from Trip t where t.route = :route";
        Query hql = createEntityManager().createQuery(tripHQL);
        hql.setParameter("route", route);
        // get list trip
        List<Trip> trips = hql.getResultList();
        createEntityManager().close();
        return trips;
    }

    public void deleteTripAndCon(Route route) {
        List<Trip> trips = getTripsByRoute(route);
        ConnectionDAO connectionDAO = new ConnectionDAO();
        System.out.println("Deleting Connections");
        for (Trip t : trips) {
            List<Connection> connections = t.getConnections();
            connectionDAO.deleteConnections(connections);
        }
        System.out.println("Deleting Trips " +trips.size());
        int i = 0;
        trips = getTripsByRoute(route);
        for (Trip t : trips) {
            System.out.println("Iterate del trip " +i);
            delete(t);
            i++;
        }
    }

    public void deleteTrip(Trip trip) {
        System.out.println("In trip dao for delete trip");
        // 0. get list connection
        String hql = "select c from Connection c where c.trip = :trip";
        Query query = createEntityManager().createQuery(hql);
        query.setParameter("trip", trip);
        List<Connection> connections = query.getResultList();
        createEntityManager().close();

        // 1.delete connections
        ConnectionDAO connectionDAO = new ConnectionDAO();
        connectionDAO.deleteConnections(connections);

        // 2.delete trip
        delete(trip);
    }

    public Trip readTripByRouteAndNo(Route route, int tripNo) {
        String tripHQL = "select t from Trip t where t.route = :route and t.tripNo= :tripNo";
        Query query = createEntityManager().createQuery(tripHQL);
        query.setParameter("route", route);
        query.setParameter("tripNo", tripNo);

        Trip trip;
        try {
            trip = (Trip) query.getSingleResult();
        } catch (Exception ex) {
            System.out.println("Cannot get Single Result at TripDAO line 73");
            trip = null;
        }
        return trip;
    }

}
