package com.fpt.router.dal;

import com.fpt.router.database.HibernateConnection;
import com.fpt.router.model.Route;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 9/27/2015.
 */
public class RouteDAL {

    SessionFactory sessionFactory = null;
    /*public static void main(String[] args){
        DemoDB demoDB = new DemoDB();
        RouteDao routeDao = new RouteDao();

        *//*Set<Route> routes = demoDB.getRoutes();
        for (Route route : routes){
           routeDao.insertRoute(route);
        }*//*
        //get All Data
        *//*List<Route> routes = routeDao.getRoute();
        for (Route route : routes){
            System.out.println("Route : "+route.getRouteName());
        }*//*
        // GET ONE ROUTE
        *//*Route route = routeDao.getRouteId(1);
        System.out.println("Route :"+route.getRouteName());*//*

    }*/

    /**
     * insert a route into DB
     * @param route
     */
    public int insertRoute(Route route){

        try{
            sessionFactory = HibernateConnection.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(route);
            session.getTransaction().commit();
            session.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return (int)route.getRouteId();
    }

    /**
     * get a Route with id
     * @param id
     * @return
     */
    public Route getRouteId(int id){
            sessionFactory = HibernateConnection.getSessionFactory();
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("from Route where routeId= :id");
            query.setLong("id",id);
            Route route = (Route) query.uniqueResult();
            session.close();
            return route;
    }

    /**
     * get All Route from DB
     * @return
     */
    public List<Route> getRoute(){
        List<Route> routes = new ArrayList<Route>();
        sessionFactory = HibernateConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Route");
        routes = query.list();
        return  routes;
    }




}
