package com.fpt.router.crawler.database;

import com.fpt.router.crawler.dao.MapDAL;
import com.fpt.router.crawler.dao.StationDAO;
import com.fpt.router.crawler.dao.TempDAO;
import com.fpt.router.crawler.dao.common.GenericDao;
import com.fpt.router.crawler.dao.common.JPADaoImpl;
import com.fpt.router.crawler.model.entity.CityMap;
import com.fpt.router.crawler.model.entity.Station;
import com.fpt.router.crawler.model.entity.Temp;
import com.fpt.router.crawler.utils.TimeUtils;
import com.fpt.router.crawler.work.Work;
import org.joda.time.*;

import java.util.Date;
import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/1/15.
 */
public class DatabaseTest {

    public static void main(String[] args) {


        Work work = new Work();
        CityMap map = work.run();
        Validation validation = new Validation(map);
        validation.run();
        MapDAL.insertDatabase(map);
        JPADaoImpl.closeFactory();


        /*DemoDB db = new DemoDB();
        CityMap map = db.cityMap;
        Validation validation = new Validation(map);
        validation.run();
        MapDAL.insertDatabase(map);
        JPADaoImpl.closeFactory();*/

        /*LocalTime time = new LocalTime(10, 30);
        LocalTime add = new LocalTime(2, 30);
        Duration d = new Duration(1, 0);
        Period p = new Period(1, 0);

        time = time.plus(p);

        //time = time.plusMinutes(31);
        System.out.println(time.toString());*/

        // MapDAL.readDatabase();
        // JPADaoImpl.closeFactory();


       /* List<Station> stationMap = (new StationDAO()).findAll();
        System.out.println(stationMap.size());*/

       /* Temp temp = new Temp();
        temp.setTempNo(10);
        LocalTime dt = new LocalTime(13,30,45);
        temp.setDate(dt);
        (new TempDAO()).create(temp);
        JPADaoImpl.closeFactory();*/


        //map = MapDAL.readDatabase();
        //System.out.println("hello world");

       /* Route route = new Route(1, Route.RouteType.DEPART, "ha kim quy zzzzzzz");
        //route.setRouteId(0);
        route.setTrips(new ArrayList<Trip>());
        route.setPathInfos(new ArrayList<PathInfo>());
        System.out.println("new route id: " + route.getRouteId());
        new RouteDAO().create(route);
        System.out.println("new route id: " + route.getRouteId());

        *//*Trip trip = new Trip(1, new Date(), new Date(), route);
        //route.setRouteId(9999);
        new JPADAOImpl<Trip, Integer>().create(trip);
        // tripDAL.insertTrip(trip);
        System.out.println("new trip id: " + trip.getTripId());
        System.out.println("new route id: " + route.getRouteId());*//*

        List<Route> routes = (new RouteDAO()).findAll();
        System.out.println(routes.size());
        JPADaoImpl.closeFactory();*/
      //  MapDAL.deleteDatabase();



    }
}
