package com.fpt.router.dal;

import com.fpt.router.database.HibernateConnection;
import com.fpt.router.model.Station;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by asus on 9/27/2015.
 */
public class StationDAL {
    SessionFactory sessionFactory = null;
    /*public static void main(String[] args){
        DemoDB demoDB = new DemoDB();
        StationDao stationDao = new StationDao();
        Set<Station> stations = demoDB.getStations();
        for (Station station: stations){
            stationDao.insertStation(station);
        }
        *//*Station station = stationDao.getStationWithId(1);
        System.out.println("Station :"+station.getName());
*//*    }*/

    /**
     * insert Station
     * @param station
     */
    public void insertStation(Station station){
        sessionFactory = HibernateConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(station);
        session.getTransaction().commit();
        session.close();
    }

    /**
     *
     * @param id
     * @return
     */
    public Station getStationWithId(int id){
        sessionFactory = HibernateConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Station where stationId= :id");
        query.setLong("id", id);
        Station station = (Station) query.uniqueResult();
        session.close();
        return station;
    }

    /**
     *
     * @return
     */
    public List<Station> getStations(){
        sessionFactory = HibernateConnection.getSessionFactory();
        Session session  = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Station");
        List<Station> stations = query.list();
        return stations;
    }
}
