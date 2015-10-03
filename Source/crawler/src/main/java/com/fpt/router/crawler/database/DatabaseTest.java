package com.fpt.router.crawler.database;

import com.fpt.router.crawler.dao.StationDAO;
import com.fpt.router.crawler.dao.common.JPADaoImpl;
import com.fpt.router.crawler.model.entity.Station;

import java.util.List;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/1/15.
 */
public class DatabaseTest {

    public static void main(String[] args) {
       /* DemoDB db = new DemoDB();
        CityMap map = db.cityMap;
        MapDAL.insertDatabase(map);*/

        List<Station> stations = (new StationDAO()).findAll();
        System.out.println(stations.size());
        JPADaoImpl.closeFactory();

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
