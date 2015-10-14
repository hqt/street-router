package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.database.HibernateConnection;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Trip;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by asus on 9/27/2015.
 */
public class TripDAO extends JPADaoImpl<Trip, Integer> {
    SessionFactory sessionFactory = null;

    /**
     * get All Trip
     * @return
     */
    public List<Trip> getTripsWithId(Route route){
        sessionFactory = HibernateConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Trip where route= :route");
        query.setEntity("route",route);
        List<Trip> trips = query.list();
        session.close();
        return trips;
    }

}