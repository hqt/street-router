package com.fpt.router.dao;

import com.fpt.router.database.HDConnection;
import com.fpt.router.model.Route;
import com.fpt.router.model.Trip;
import com.fpt.router.work.DemoDB;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by asus on 9/27/2015.
 */
public class TripDao {
    SessionFactory sessionFactory = null;

    /*public static void main(String[] args){
        DemoDB demoDB = new DemoDB();
        TripDao tripDao = new TripDao();
        *//*Map<String,Set<Trip>> tripss = demoDB.getTrips();
        Set<Trip> trips1d = tripss.get("trips1d");*//*
        *//*Set<Trip> trips1r = tripss.get("trips1r");
        Set<Trip> trips18d = tripss.get("trips18d");
        Set<Trip> trips18r = tripss.get("trips18r");*//*

        *//*for (Trip trip1d : trips1d){
            tripDao.insertTrip(trip1d);
        }*//*

        Trip trip = tripDao.getTripWithId(3);
        System.out.println("Trip : "+trip.getStartTime());


    }*/

    /**
     * insert trip into DB
     * @param trip
     */
    public void insertTrip(Trip trip){
        sessionFactory = HDConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(trip);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * get Trip with ID
     * @param id
     * @return
     */
    public Trip getTripWithId(int id){
        sessionFactory = HDConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Trip where tripId= :id");
        query.setLong("id", id);
        Trip trip = (Trip) query.uniqueResult();
        session.close();
        return trip;
    }

    /**
     * get All Trip
     * @return
     */
    public List<Trip> getTrips(){
        sessionFactory = HDConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Trip");
        List<Trip> trips = query.list();
        return trips;
    }

    /**
     * get All Trip
     * @return
     */
    public List<Trip> getTripsWithId(Route route){
        sessionFactory = HDConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Trip where route= :route");
        query.setEntity("route",route);
        List<Trip> trips = query.list();
        session.close();
        return trips;
    }

}
