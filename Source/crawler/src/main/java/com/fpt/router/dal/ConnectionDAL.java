package com.fpt.router.dal;

import com.fpt.router.database.HibernateConnection;
import com.fpt.router.model.Connection;
import com.fpt.router.model.Trip;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by asus on 9/27/2015.
 */
public class ConnectionDAL {
    SessionFactory sessionFactory = null;
   /* public static void main(String[] args){
        DemoDB demoDB = new DemoDB();
        ConnectionDao connectionDao = new ConnectionDao();
        Set<Connection> connectionSet = demoDB.getConnections();
        for (Connection conn : connectionSet){
            connectionDao.insertConnection(conn);
        }

    }*/

    /**
     *
     * @param conn
     */
    public void insertConnection(Connection conn){
        sessionFactory = HibernateConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(conn);
        session.getTransaction().commit();
        session.close();
    }

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
