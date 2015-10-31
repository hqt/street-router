package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.entity.PathInfo;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Station;
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

    public List<Station> getStaionsOfRoutePassed(int routeId) {

        List<Station> stations = new ArrayList<Station>();

        Route route = new Route();
        route.setRouteId(routeId);
        String hqlPathInfo = "select p from PathInfo p where p.route = :route";

        Query query = getEntiyManager().createQuery(hqlPathInfo);
        query.setParameter("route", route);
        List<PathInfo> pathInfos = query.getResultList();

        for (PathInfo pathInfo : pathInfos) {
            Station stationFrom = pathInfo.getFrom();
            stations.add(stationFrom);
        }

        return stations;
    }
}
