package com.fpt.router.work;

import com.fpt.router.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by asus on 9/27/2015.
 */
public class DemoDB {
    public CityMap dummyData() {

        return null;
    }

    public Set<Route> getRoutes(){
        Set<Route> routes = new HashSet<Route>();

        Route route1d = new Route(1, Route.RouteType.DEPART,"xe so 1");
        Route route1r = new Route(1, Route.RouteType.RETURN,"xe so 1");
        Route route18d = new Route(18, Route.RouteType.DEPART,"xe so 18");
        Route route18r = new Route(18, Route.RouteType.RETURN,"xe so 18");
        routes.add(route1d);
        routes.add(route1r);
        routes.add(route18d);
        routes.add(route18r);
        return routes;
    }

    public Set<Station> getStations() {
        Set<Station> stations = new HashSet<Station>();

        Station R1StationA = new Station("BX06","Công Tr??ng Mê Linh","Công tr??ng Mê Linh, Thi Sách, Qu?n 1",106.705856990563,10.7767894851893);
        Station R1StationB = new Station("Q1T030","Phà Th? Thiêm","21, Tôn ??c Th?ng, Qu?n 1",106.706395410034,10.7732232669716);
        Station R1StationC = new Station("Q1N020","Ch? C?", "84, Hàm Nghi, Qu?n 1",106.703169414919,10.7709944598553);
        Station R1StationD = new Station("Q1T021","Tr??ng Cao Th?ng", "Xa Lo Ha Noi",106.701721418991,10.7710708725722);

        Station R18StationA = new Station("BX01","Ga xe buýt Sài Gòn","GA HKXB SAI GON - CV 23/9 , Lê Lai, Qu?n 1",106.689362102212,10.7676765239509);
        Station R18StationB = new Station("Q1T040","Tôn Th?t Tùng","??i di?n 210, Lê Lai, Qu?n 1",106.690297220232,10.7685123173763);
        Station R18StationC = new Station("Q1T041","Nguy?n Th? Ngh?a", "??i di?n 96, Lê Lai, Qu?n 1",106.693339515807,10.7697303323519);
        Station R18StationD = new Station("Q1T042","Khách s?n New world", "??i di?n 1A Ph?m H?ng Thái, Lê Lai, Qu?n 1",106.696741227397,10.7711887842904);

        stations.add(R1StationA);
        stations.add(R1StationB);
        stations.add(R1StationC);
        stations.add(R1StationD);
        stations.add(R18StationA);
        stations.add(R18StationB);
        stations.add(R18StationC);
        stations.add(R18StationD);

        return stations;
    }

    public Set<Trip> getTrips(){
        Set<Trip> trips = new HashSet<Trip>();
        SimpleDateFormat triptime = new SimpleDateFormat("hh:mm");
        try{
            Trip Trip1 = new Trip(1,triptime.parse("5:00"),triptime.parse("5:35"),getRouteId(1));
            Trip Trip2 = new Trip(2,triptime.parse("5:08"),triptime.parse("5:43"),getRouteId(1));
            Trip Trip3 = new Trip(3,triptime.parse("5:16"),triptime.parse("5:51"),getRouteId(1));
            Trip Trip4 = new Trip(4,triptime.parse("5:22"),triptime.parse("5:57"),getRouteId(1));
        }catch (ParseException e){
            e.printStackTrace();

        }




        return null;
    }

    public Set<PathInfo> getPathInfos(){
        Set<PathInfo> pathInfos = new HashSet<PathInfo>();
        PathInfo pathInfo1AB = new PathInfo(getRouteId(1), getFromStation(1),getToStation(2),1,"106.70589000000001,10.776800000000001 106.70627,10.775810000000002 106.70623,10.77579 106.70617000000001,10.77576 106.70607000000001,10.77569 106.70598000000001,10.775590000000001 106.70593000000001,10.775480000000002 106.70590000000001,10.77538 106.70589000000001,10.77527 106.70592,10.775110000000002 106.706,10.77495 106.70614,10.774830000000001 106.70622,10.774780000000002 106.70633000000001,10.774750000000001 106.70649,10.77476 106.70658,10.774600000000001 106.70655000000001,10.7742 106.70647000000001,10.77322 ");

        pathInfos.add(pathInfo1AB);


        return pathInfos;
    }

    public Route getRouteId(int id){
        Route route = new Route();
        return route;
    }

    public Station getFromStation(int id){
        Station station = new Station();
        return station;
    }
    public Station getToStation(int id){
        Station station = new Station();
        return  station;
    }



}
