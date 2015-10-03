package com.fpt.router.crawler.dao;

import com.fpt.router.crawler.dao.common.JPADaoImpl;
import com.fpt.router.crawler.database.HibernateConnection;
import com.fpt.router.crawler.model.entity.Connection;
import com.fpt.router.crawler.model.entity.Trip;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by asus on 9/27/2015.
 */
public class ConnectionDAO extends JPADaoImpl<Connection, Integer> {
    SessionFactory sessionFactory = null;

    public List<Connection> getListConnectionWithTrip(Trip trip){
        sessionFactory = HibernateConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Connection where trip= :trip");
        query.setEntity("trip", trip);
        List<Connection> connectionList  = query.list();
        session.close();
        return connectionList ;
    }
}
