package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.database.HibernateConnection;
import com.fpt.router.artifacter.model.entity.Connection;
import com.fpt.router.artifacter.model.entity.Trip;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by asus on 9/27/2015.
 */
public class ConnectionDAO extends JPADaoImpl<Connection, Integer> {

    public void deleteConnections(List<Connection> connections) {
        System.out.println("Delete connection size " +connections.size());
        for (Connection c : connections) {
            delete(c);
        }
        System.out.println("Done Delete Connections");
    }
}
