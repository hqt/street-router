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

    public List<PathInfo> getPathInfosByRoute(Route route) {
        String hqlPathInfo = "select p from PathInfo p where p.route = :route";
        Query query = createEntityManager().createQuery(hqlPathInfo);
        query.setParameter("route", route);
        List<PathInfo> pathInfos = query.getResultList();
        createEntityManager().close();
        return pathInfos;
    }

    public void deletePathInfoByRouteId(Route route) {
        System.out.println("Deleting PathInfo...");

        String hql = "select p from PathInfo p where p.route = :route";

        Query query = createEntityManager().createQuery(hql);
        query.setParameter("route", route);

        List<PathInfo> deletePathInfos = query.getResultList();
        createEntityManager().close();

        System.out.println("Paths Size "+deletePathInfos.size());
        if (!deletePathInfos.isEmpty()) {
            for (PathInfo p : deletePathInfos) {
                delete(p);
            }
        }
    }
}
