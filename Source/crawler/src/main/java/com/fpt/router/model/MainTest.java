package com.fpt.router.model;

import com.fpt.router.database.HDConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by asus on 9/27/2015.
 */
public class MainTest {
    public static void main(String[] args){
        SessionFactory sessionFactory = null;
        try{
            sessionFactory = HDConnection.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            Transaction tx = session.beginTransaction();

            //Route data
            Route route18 = new Route(18, Route.RouteType.DEPART,"xe muoi tam");
            Route route55 = new Route(55, Route.RouteType.DEPART,"xe 55");
             /*Route route55 = (Route) session.get(Route.class,new Long(1));*/
            //Trip data
            Set<Trip> trips = new HashSet<Trip>();
            Trip trip18_1 = new Trip(1,new Date(), new Date(), route18);
            Trip trip18_2 = new Trip(1,new Date(), new Date(), route18);

            trips.add(trip18_1);
            trips.add(trip18_2);

            route18.setTrips(trips);

            //Station
            Station stationA = new Station("A001","Nong Lam","Duong So 7",20000.002,30000.009);
            /*Station a = (Station) session.get(Station.class, new Long(19));*/
            Station stationB = new Station("B001","Nga Tu Thu Duc","Duong Vo Van Ngan",40000.008,50000.001);
            /*Station b =(Station) session.get(Station.class, new Long(20));*/
            Station stationC = new Station("C001","Nga Tu Binh Thai", "Duong Vo Van Kiet",60000.004,70000.003);
            //Path information
            /*PathInfo pathInfoAB = new PathInfo(route55,a,b,4,"hello");*/
            /*PathInfo pathInfoBC = new PathInfo(route55,stationB, stationC,2,"20000.002,30000.009;40000.008,50000.001");*/
            /*PathInfo pathInforDemo = (PathInfo) session.get(PathInfo.class, new Long(1));*/

            //Trip
           /* Trip trip1 = (Trip) session.get(Trip.class,new Long(1));*/

            /*Connection connection = new Connection();
            connection.setTrip(trip18_1);
            connection.setPathInfo(pathInfoBC);
            connection.setArrivalTime(new Date());*/

            session.save(route18);
//            session.save(pathInfoAB);
            /*session.save(connection);*/
            tx.commit();
            sessionFactory.close();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
