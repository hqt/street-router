package com.fpt.router.crawler.dao;

import com.fpt.router.crawler.dao.common.JPADaoImpl;
import com.fpt.router.crawler.database.HibernateConnection;
import com.fpt.router.crawler.model.entity.PathInfo;
import com.fpt.router.crawler.model.entity.Route;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by asus on 9/27/2015.
 */
public class PathInfoDAO extends JPADaoImpl<PathInfo, Integer> {
    SessionFactory sessionFactory = null;

    public List<PathInfo> getPathInfo(Route route){
        sessionFactory = HibernateConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from PathInfo where route= :route");
        query.setEntity("route",route);
        List<PathInfo> pathInfos = query.list();
        session.close();
        return pathInfos;
    }
}
