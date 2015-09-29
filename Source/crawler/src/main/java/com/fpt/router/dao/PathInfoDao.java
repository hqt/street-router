package com.fpt.router.dao;

import com.fpt.router.database.HDConnection;
import com.fpt.router.model.PathInfo;
import com.fpt.router.model.Route;
import com.fpt.router.work.DemoDB;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by asus on 9/27/2015.
 */
public class PathInfoDao {
    SessionFactory sessionFactory = null;
    /*public static void main(String[] args){
        DemoDB demoDB = new DemoDB();
        PathInfoDao pathInfoDao = new PathInfoDao();
        *//*Set<PathInfo> pathInfoSet = demoDB.getPathInfos();
        for (PathInfo pathInfo : pathInfoSet){
            pathInfoDao.insertPathInfo(pathInfo);
        }*//*
        PathInfo pathInfo = pathInfoDao.getPathInfoWithId(1);
        System.out.println("PathInfo :"+pathInfo.getMiddleLocations());
    }*/

    /**
     *
     * @param pathInfo
     */
    public void insertPathInfo(PathInfo pathInfo){

        try{
            sessionFactory = HDConnection.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(pathInfo);
            session.getTransaction().commit();
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public PathInfo getPathInfoWithId(int id){
        sessionFactory = HDConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from PathInfo where pathInfoId= :id");
        query.setLong("id", id);
        PathInfo pathInfo = (PathInfo) query.uniqueResult();
        session.close();
        return pathInfo;
    }

    public List<PathInfo> getPathInfo(Route route){
        sessionFactory = HDConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from PathInfo where route= :route");
        query.setEntity("route",route);
        List<PathInfo> pathInfos = query.list();
        session.close();
        return pathInfos;
    }
}
