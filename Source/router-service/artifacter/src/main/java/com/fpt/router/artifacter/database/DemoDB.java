package com.fpt.router.artifacter.database;

import com.fpt.router.artifacter.model.entity.CityMap;
import com.fpt.router.artifacter.model.entity.Connection;
import com.fpt.router.artifacter.model.entity.PathInfo;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.artifacter.model.entity.Station;
import com.fpt.router.artifacter.model.entity.Trip;
import com.fpt.router.artifacter.model.helper.RouteType;
import org.joda.time.LocalTime;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by asus on 9/27/2015.
 */
public class DemoDB {

    public CityMap cityMap = new CityMap();
    public DemoDB(){

        List<Route> routes = new ArrayList<Route>();
        List<Trip> trips1d = new ArrayList<Trip>();
        List<Trip> trips18d = new ArrayList<Trip>();
        SimpleDateFormat triptime = new SimpleDateFormat("hh:mm");

        // add station
        List<Station> stations = new ArrayList<Station>();

        Station R1StationA = new Station(0, "BX06","C�ng Tr??ng M� Linh","C�ng tr??ng M� Linh, Thi S�ch, Qu?n 1",106.705856990563,10.7767894851893);
        Station R1StationB = new Station(1, "Q1T030","Ph� Th? Thi�m","21, T�n ??c Th?ng, Qu?n 1",106.706395410034,10.7732232669716);
        Station R1StationC = new Station(2, "Q1N020","Ch? C?", "84, H�m Nghi, Qu?n 1",106.703169414919,10.7709944598553);
        Station R1StationD = new Station(3, "Q1T021","Tr??ng Cao Th?ng", "Xa Lo Ha Noi",106.701721418991,10.7710708725722);

        Station R18StationA = new Station(4, "BX01","Ga xe bu�t S�i G�n","GA HKXB SAI GON - CV 23/9 , L� Lai, Qu?n 1",106.689362102212,10.7676765239509);
        Station R18StationB = new Station(5, "Q1T040","T�n Th?t T�ng","??i di?n 210, L� Lai, Qu?n 1",106.690297220232,10.7685123173763);
        Station R18StationC = new Station(6, "Q1T041","Nguy?n Th? Ngh?a", "??i di?n 96, L� Lai, Qu?n 1",106.693339515807,10.7697303323519);
        Station R18StationD = new Station(7, "Q1T042","Kh�ch s?n New world", "??i di?n 1A Ph?m H?ng Th�i, L� Lai, Qu?n 1",106.696741227397,10.7711887842904);

        stations.add(R1StationA);
        stations.add(R1StationB);
        stations.add(R1StationC);
        stations.add(R1StationD);
        stations.add(R18StationA);
        stations.add(R18StationB);
        stations.add(R18StationC);
        stations.add(R18StationD);

        cityMap.setStations(stations);


        // Route 1 depart
        Route route1d = new Route(1, RouteType.DEPART,"xe so 1");

        PathInfo pathInfo1AB = new PathInfo(route1d, R1StationA, R1StationB,1,"106.70589000000001,10.776800000000001 106.70627,10.775810000000002 106.70623,10.77579 106.70617000000001,10.77576 106.70607000000001,10.77569 106.70598000000001,10.775590000000001 106.70593000000001,10.775480000000002 106.70590000000001,10.77538 106.70589000000001,10.77527 106.70592,10.775110000000002 106.706,10.77495 106.70614,10.774830000000001 106.70622,10.774780000000002 106.70633000000001,10.774750000000001 106.70649,10.77476 106.70658,10.774600000000001 106.70655000000001,10.7742 106.70647000000001,10.77322 ");
        PathInfo pathInfo1BC = new PathInfo(route1d, R1StationB,R1StationC,2,"106.70589000000001,10.776800000000001 106.70627,10.775810000000002 106.70623,10.77579 106.70617000000001,10.77576 106.70607000000001,10.77569 106.70598000000001,10.775590000000001 106.70593000000001,10.775480000000002 106.70590000000001,10.77538 106.70589000000001,10.77527 106.70592,10.775110000000002 106.706,10.77495 106.70614,10.774830000000001 106.70622,10.774780000000002 106.70633000000001,10.774750000000001 106.70649,10.77476 106.70658,10.774600000000001 106.70655000000001,10.7742 106.70647000000001,10.77322 ");
        PathInfo pathInfo1CD = new PathInfo(route1d, R1StationC,R1StationD,3,"106.70589000000001,10.776800000000001 106.70627,10.775810000000002 106.70623,10.77579 106.70617000000001,10.77576 106.70607000000001,10.77569 106.70598000000001,10.775590000000001 106.70593000000001,10.775480000000002 106.70590000000001,10.77538 106.70589000000001,10.77527 106.70592,10.775110000000002 106.706,10.77495 106.70614,10.774830000000001 106.70622,10.774780000000002 106.70633000000001,10.774750000000001 106.70649,10.77476 106.70658,10.774600000000001 106.70655000000001,10.7742 106.70647000000001,10.77322 ");
        PathInfo pathInfo1DE = new PathInfo(route1d, R1StationD,R18StationA,4,"106.70589000000001,10.776800000000001 106.70627,10.775810000000002 106.70623,10.77579 106.70617000000001,10.77576 106.70607000000001,10.77569 106.70598000000001,10.775590000000001 106.70593000000001,10.775480000000002 106.70590000000001,10.77538 106.70589000000001,10.77527 106.70592,10.775110000000002 106.706,10.77495 106.70614,10.774830000000001 106.70622,10.774780000000002 106.70633000000001,10.774750000000001 106.70649,10.77476 106.70658,10.774600000000001 106.70655000000001,10.7742 106.70647000000001,10.77322 ");


        route1d.getPathInfos().add(pathInfo1AB);
        route1d.getPathInfos().add(pathInfo1BC);
        route1d.getPathInfos().add(pathInfo1CD);
        route1d.getPathInfos().add(pathInfo1DE);


        // Trip Depart in Route No 1
        Trip trip1 = new Trip(1, new LocalTime(5, 0), new LocalTime(5, 35),route1d);

        Connection connectionRoute11 = new Connection(trip1,pathInfo1AB, new LocalTime(5, 8), new LocalTime(5, 8));
        Connection connectionRoute12 = new Connection(trip1,pathInfo1BC, new LocalTime(5, 16), new LocalTime(5, 8));
        Connection connectionRoute13 = new Connection(trip1,pathInfo1CD, new LocalTime(5, 24), new LocalTime(5, 8));
        Connection connectionRoute14 = new Connection(trip1,pathInfo1DE, new LocalTime(5, 35), new LocalTime(5, 8));

        trip1.getConnections().add(connectionRoute11);
        trip1.getConnections().add(connectionRoute12);
        trip1.getConnections().add(connectionRoute13);
        trip1.getConnections().add(connectionRoute14);


        Trip trip2 = new Trip(2,new LocalTime(5, 8),new LocalTime(5, 43),route1d);

        Connection connTrip21 = new Connection(trip2,pathInfo1AB,new LocalTime(5, 16), new LocalTime(5, 8));
        Connection connTrip22 = new Connection(trip2,pathInfo1BC,new LocalTime(5, 24), new LocalTime(5, 8));
        Connection connTrip23 = new Connection(trip2,pathInfo1CD,new LocalTime(5, 35), new LocalTime(5, 8));
        Connection connTrip24 = new Connection(trip2,pathInfo1DE,new LocalTime(5, 43), new LocalTime(5, 8));

        trip2.getConnections().add(connTrip21);
        trip2.getConnections().add(connTrip22);
        trip2.getConnections().add(connTrip23);
        trip2.getConnections().add(connTrip24);


        // Trip Return in Route No 1
        Trip trip3 = new Trip(3,new LocalTime("5:16"),new LocalTime("5:51"),route1d);

        Connection connTrip31 = new Connection(trip3,pathInfo1AB,new LocalTime("5:24"), new LocalTime(5, 8));
        Connection connTrip32 = new Connection(trip3,pathInfo1BC,new LocalTime("5:35"), new LocalTime(5, 8));
        Connection connTrip33 = new Connection(trip3,pathInfo1CD,new LocalTime("5:43"), new LocalTime(5, 8));
        Connection connTrip34 = new Connection(trip3,pathInfo1DE,new LocalTime("5:51"), new LocalTime(5, 8));

        trip3.getConnections().add(connTrip31);
        trip3.getConnections().add(connTrip32);
        trip3.getConnections().add(connTrip33);
        trip3.getConnections().add(connTrip34);


        trips1d.add(trip1);
        trips1d.add(trip2);
        trips1d.add(trip3);


        route1d.setTrips(trips1d);


        //Route 18 depart
        Route route18d = new Route(18, RouteType.DEPART,"xe so 18");

        PathInfo pathInfo18AB = new PathInfo(route18d, R1StationD,R18StationA,1,"106.70589000000001,10.776800000000001 106.70627,10.775810000000002 106.70623,10.77579 106.70617000000001,10.77576 106.70607000000001,10.77569 106.70598000000001,10.775590000000001 106.70593000000001,10.775480000000002 106.70590000000001,10.77538 106.70589000000001,10.77527 106.70592,10.775110000000002 106.706,10.77495 106.70614,10.774830000000001 106.70622,10.774780000000002 106.70633000000001,10.774750000000001 106.70649,10.77476 106.70658,10.774600000000001 106.70655000000001,10.7742 106.70647000000001,10.77322 ");
        PathInfo pathInfo18BC = new PathInfo(route18d, R18StationA,R18StationB,2,"106.70589000000001,10.776800000000001 106.70627,10.775810000000002 106.70623,10.77579 106.70617000000001,10.77576 106.70607000000001,10.77569 106.70598000000001,10.775590000000001 106.70593000000001,10.775480000000002 106.70590000000001,10.77538 106.70589000000001,10.77527 106.70592,10.775110000000002 106.706,10.77495 106.70614,10.774830000000001 106.70622,10.774780000000002 106.70633000000001,10.774750000000001 106.70649,10.77476 106.70658,10.774600000000001 106.70655000000001,10.7742 106.70647000000001,10.77322 ");
        PathInfo pathInfo18CD = new PathInfo(route18d, R18StationB,R18StationC,3,"106.70589000000001,10.776800000000001 106.70627,10.775810000000002 106.70623,10.77579 106.70617000000001,10.77576 106.70607000000001,10.77569 106.70598000000001,10.775590000000001 106.70593000000001,10.775480000000002 106.70590000000001,10.77538 106.70589000000001,10.77527 106.70592,10.775110000000002 106.706,10.77495 106.70614,10.774830000000001 106.70622,10.774780000000002 106.70633000000001,10.774750000000001 106.70649,10.77476 106.70658,10.774600000000001 106.70655000000001,10.7742 106.70647000000001,10.77322 ");
        PathInfo pathInfo18DE = new PathInfo(route18d, R18StationC,R18StationD,4,"106.70589000000001,10.776800000000001 106.70627,10.775810000000002 106.70623,10.77579 106.70617000000001,10.77576 106.70607000000001,10.77569 106.70598000000001,10.775590000000001 106.70593000000001,10.775480000000002 106.70590000000001,10.77538 106.70589000000001,10.77527 106.70592,10.775110000000002 106.706,10.77495 106.70614,10.774830000000001 106.70622,10.774780000000002 106.70633000000001,10.774750000000001 106.70649,10.77476 106.70658,10.774600000000001 106.70655000000001,10.7742 106.70647000000001,10.77322 ");


        route18d.getPathInfos().add(pathInfo18AB);
        route18d.getPathInfos().add(pathInfo18BC);
        route18d.getPathInfos().add(pathInfo18CD);
        route18d.getPathInfos().add(pathInfo18DE);


        // Trip Depart in Route No 1
        Trip trip18 = new Trip(1,new LocalTime("5:00"),new LocalTime("5:35"),route18d);

        Connection connectionRoute181 = new Connection(trip18,pathInfo18AB,new LocalTime("5:08"), new LocalTime(5, 8));
        Connection connectionRoute182 = new Connection(trip18,pathInfo18BC,new LocalTime("5:16"), new LocalTime(5, 8));
        Connection connectionRoute183 = new Connection(trip18,pathInfo18CD,new LocalTime("5:24"), new LocalTime(5, 8));
        Connection connectionRoute184 = new Connection(trip18,pathInfo18DE,new LocalTime("5:35"), new LocalTime(5, 8));

        trip18.getConnections().add(connectionRoute181);
        trip18.getConnections().add(connectionRoute182);
        trip18.getConnections().add(connectionRoute183);
        trip18.getConnections().add(connectionRoute184);


        Trip trip28 = new Trip(2,new LocalTime(5, 8),new LocalTime("5:43"),route18d);

        Connection connTrip281 = new Connection(trip28,pathInfo18AB,new LocalTime("5:16"), new LocalTime(5, 8));
        Connection connTrip282 = new Connection(trip28,pathInfo18BC,new LocalTime("5:24"), new LocalTime(5, 8));
        Connection connTrip283 = new Connection(trip28,pathInfo18CD,new LocalTime("5:35"), new LocalTime(5, 8));
        Connection connTrip284 = new Connection(trip28,pathInfo18DE,new LocalTime("5:43"), new LocalTime(5, 8));

        trip28.getConnections().add(connTrip281);
        trip28.getConnections().add(connTrip282);
        trip28.getConnections().add(connTrip283);
        trip28.getConnections().add(connTrip284);

        trips18d.add(trip18);
        trips18d.add(trip28);

        route18d.setTrips(trips18d);

        //and route into list route

        routes.add(route1d);
        routes.add(route18d);

        cityMap.setRoutes(routes);

    }

}
