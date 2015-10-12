package com.fpt.router.artifacter.dao;

import com.fpt.router.artifacter.dao.common.JPADaoImpl;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.utils.PaginationUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 9/27/2015.
 */
public class RouteDAO extends JPADaoImpl<Route, Integer> {
    public List<Route> getRoutesAtPage(int pageNum) {
        EntityManagerFactory factory = JPADaoImpl.factory;
        EntityManager entityManager = factory.createEntityManager();

        String hql = "SELECT a FROM Route a" +
                " ORDER BY a.routeId DESC";

        Query query = entityManager.createQuery(hql).setFirstResult(PaginationUtils.getOffset(pageNum)).setMaxResults(10);
        /*query.setParameter("offset", PaginationUtils.getOffset(pageNum));
        query.setParameter("limit", 10);*/
        List<Route> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }
}
