package com.fpt.router.dao;

import com.fpt.router.database.HDConnection;
import com.fpt.router.model.Connection;
import com.fpt.router.model.Trip;
import com.fpt.router.work.DemoDB;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Set;

/**
 * Created by asus on 9/27/2015.
 */
public class ConnectionDao {
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
        sessionFactory = HDConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(conn);
        session.getTransaction().commit();
        session.close();
    }

    public List<Connection> getListConnectionWithTrip(Trip trip){
        sessionFactory = HDConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Connection where trip= :trip");
        query.setEntity("trip", trip);
        List<Connection> connectionList  = query.list();
        session.close();
        return connectionList ;
    }
}
