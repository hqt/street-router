package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.database.HibernateConnection;
import com.fpt.router.artifacter.model.entity.PathInfo;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Station;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 9/27/2015.
 */
public class PathInfoDAO extends JPADaoImpl<PathInfo, Integer> {
    public static SessionFactory sessionFactory = null;

    public static EntityManager getEntiyManager() {
        EntityManagerFactory entityManagerFactory = JPADaoImpl.factory;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }

    public static List<PathInfo> getPathInfo(Route route){
      /*  sessionFactory = HibernateConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from PathInfo where route= :route");
        query.setEntity("route",route);
        List<PathInfo> pathInfos = query.list();
        session.close();*/
        return null;
    }

    public static List<Station> getListPathInfoByRouteNo(int routeNo) {

        List<Station> stations = new ArrayList<Station>();

        // create hql
        String hql = "select r from Route r where r.routeNo= :routeno";

        Query query = getEntiyManager().createQuery(hql);
        query.setParameter("routeno", routeNo);
        List<Route> routes = query.getResultList();

        for (Route r : routes) {
            List<PathInfo> pathInfos = r.getPathInfos();
            for (PathInfo p : pathInfos) {
                Station stationFrom = p.getFrom();
                stations.add(stationFrom);
            }
        }

        return stations;
    }
}
