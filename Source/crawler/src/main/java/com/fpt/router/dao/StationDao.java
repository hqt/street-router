package com.fpt.router.dao;

import com.fpt.router.database.HDConnection;
import com.fpt.router.model.Station;
import com.fpt.router.work.DemoDB;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Set;

/**
 * Created by asus on 9/27/2015.
 */
public class StationDao {
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
        sessionFactory = HDConnection.getSessionFactory();
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
        sessionFactory = HDConnection.getSessionFactory();
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
        sessionFactory = HDConnection.getSessionFactory();
        Session session  = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Station");
        List<Station> stations = query.list();
        return stations;
    }
}
