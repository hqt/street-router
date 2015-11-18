package com.fpt.router.web.action.staff;

import com.fpt.router.artifacter.dao.*;
import com.fpt.router.artifacter.model.entity.*;
import com.fpt.router.web.action.staff.comparer.CompareRoute;
import com.fpt.router.web.action.staff.parser.ParseExcelLocal;
import com.fpt.router.web.action.staff.parser.ParseJsonLocal;
import com.fpt.router.web.action.util.PasswordUtils;

import java.io.*;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by datnt on 10/12/2015.
 */
public class abcd {

    public static void buildFileName() {
        String fileName = "600-false.json";
        String regexJsonFile = "^(\\d+)-(\\w+).(json)";
        Pattern p = Pattern.compile(regexJsonFile);

        Matcher m = p.matcher(fileName);
        if (m.find()) {
            System.out.println("Route No: " + m.group(1));
            System.out.println("Route Type: " + m.group(2));
            System.out.println("File Type: " + m.group(3));
        } else {
            System.out.println("Not match");
        }

    }

    public void testParseFileFromLocal() {
        File jsonFile = new File("C:\\Users\\datnt\\Desktop\\abcd");
        if (jsonFile.isDirectory()) {
            ParseJsonLocal parseJsonLocal = new ParseJsonLocal(jsonFile);
            CityMap map = parseJsonLocal.run();

            File fileExcel = new File("C:\\Users\\datnt\\Desktop\\bcda");
            if (fileExcel.exists() && fileExcel.isDirectory()) {
                ParseExcelLocal parseExcelLocal = new ParseExcelLocal(map, fileExcel);
                map = parseExcelLocal.run();
            }
        }
    }

    public static void testInsertStationNof() {
        System.out.println("Begin insert station notification");
        Station station = new Station();
        station.setStationId(1);
        StationNotification stationNof = new StationNotification();
        stationNof.setStation(station);
        stationNof.setCreatedTime(new Date());
        stationNof.setChangeLatitude(3209128.0);
        stationNof.setChangeLongitude(1302012.0);
        stationNof.setChangeName(null);
        stationNof.setChangeStreet(null);
        StationNotificationDAO stationNotificationDAO = new StationNotificationDAO();
        stationNotificationDAO.create(stationNof);
        System.out.println("Done");
    }

    public static void testParseData() {
        /* HashSet<Integer> hs1 = new HashSet<>();
        HashSet<Integer> hs2 = new HashSet<>();

        hs1.add(1);
        hs1.add(2);
        hs1.add(3);

        hs2.add(1);
        hs2.add(2);
        hs2.add(3);
        hs2.add(4);

        HashSet<Integer> hsTotal = new HashSet<>();
        hsTotal.addAll(hs1);
        hsTotal.addAll(hs2);

        hsTotal.removeAll(hs1);
        hs2.removeAll(hsTotal);
        hs1.removeAll(hs2);

        int a = 3;*/



        List<Route> routesDB = buildRouteFull();

        File jsonFolder = new File("C:\\Users\\datnt\\Desktop\\testJsonFolder");
        ParseJsonLocal parseJsonLocal = new ParseJsonLocal(jsonFolder);
        CityMap mapSource = parseJsonLocal.run();

        File excelFolder = new File("C:\\Users\\datnt\\Desktop\\testExcelFolder");
        ParseExcelLocal parseExcelLocal = new ParseExcelLocal(mapSource, excelFolder);
        mapSource = parseExcelLocal.run();

        List<Route> routesSource = mapSource.getRoutes();

        CompareRoute compareRoute = new CompareRoute(routesDB, routesSource);
        compareRoute.run();

        int a = 3;

        /*List<Trip> tripsDB = routesDB.get(0).getTrips();
        List<Trip> tripsSource = routesSource.get(0).getTrips();

        CompareTrip compareTrip = new CompareTrip(tripsDB, tripsSource);
        compareTrip.run();
        int a = 3;*/
    }

    public void insert() {
        PasswordUtils passwordUtils = new PasswordUtils();

        Staff khuong = new Staff();
        String password = passwordUtils.md5("123456");
        khuong.setRole(0);
        khuong.setStaffName("Man Huỳnh Khương");
        khuong.setStaffEmail("khuongbungbu@gmail.com");
        khuong.setPhoneNumber("0934992244");
        khuong.setPassword(password);

        Staff quy = new Staff();
        quy.setRole(0);
        quy.setStaffName("Hà Kim Quy");
        quy.setStaffEmail("hakimquy@gmail.com");
        quy.setPhoneNumber("0934992244");
        quy.setPassword(password);

        Staff ngoan = new Staff();
        ngoan.setRole(1);
        ngoan.setStaffName("Trần Thanh Ngoan");
        ngoan.setStaffEmail("khuongbungbu@gmail.com");
        ngoan.setPhoneNumber("0934992244");
        ngoan.setPassword(password);

        Staff thao = new Staff();
        thao.setRole(2);
        thao.setStaffName("Huỳnh Quang Thảo");
        thao.setStaffEmail("huynhquangthao@gmail.com");
        thao.setPhoneNumber("0934992244");
        thao.setPassword(password);

        Staff nam = new Staff();
        nam.setRole(0);
        nam.setStaffName("Nguyễn Trung Nam");
        nam.setStaffEmail("nguyentrumnam@gmail.com");
        nam.setPhoneNumber("0934992244");
        nam.setPassword(password);

        Staff dat = new Staff();
        dat.setRole(1);
        dat.setStaffName("Ngô Tiến Đạt");
        dat.setStaffEmail("ngotiendat@gmail.com");
        dat.setPhoneNumber("0934992244");
        dat.setPassword(password);

        StaffDAO dao = new StaffDAO();
        dao.create(khuong);
        dao.create(quy);
        dao.create(ngoan);
        dao.create(thao);
        dao.create(nam);
        dao.create(dat);
    }

    private enum Role {
        ABC,
        BCD,
        DAH,
    }

    public static void main(String args[]) {
        StationNotificationDAO stationNotificationDAO = new StationNotificationDAO();
        /*StationNotification stationNotification = new StationNotification();
        stationNotification.setChangeName("asdsa");
        stationNotification.setStationCodeID("QTDT073");
        stationNotification.setCreatedTime(new Date());
        Station sta = new Station();
        sta.setStationId(1);
        stationNotification.setStation(sta);
        stationNotificationDAO.create(stationNotification);*/

        /*StationNotification stationNotification = stationNotificationDAO.readByCode("Q5T023");
        stationNotificationDAO.update(stationNotification);
        int a = 3;*/
        double f = 10.849804816682;
        double k = 10.841651662725;
        double a = 10.8498048166817;
        double c = Math.ulp(a);
        double g = Math.ulp(k);
        System.out.println(c);
        System.out.println(g);
        if (c == g) {
            System.out.println("Được đấy !");
        }
        int asd = 123;
    }

    public static List<Route> buildRouteFull() {

        List<Route> result = new ArrayList<Route>();

        RouteDAO routeDAO = new RouteDAO();
        TripDAO tripDAO = new TripDAO();
        PathInfoDAO pathInfoDAO = new PathInfoDAO();

        List<Route> routes = routeDAO.findAll();

        if (routes != null && !routes.isEmpty()) {
            result.addAll(routes);
            for (int i = 0; i < result.size(); i++) {
                List<Trip> trips = tripDAO.getTripsByRoute(result.get(i));
                result.get(i).setTrips(trips);
                List<PathInfo> pathInfos = pathInfoDAO.getPathInfosByRoute(result.get(i));
                result.get(i).setPathInfos(pathInfos);
            }
        }

        return result;
    }

    public static void md5() throws NoSuchAlgorithmException {
        String password = "123456";

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuilder sb = new StringBuilder();
        for (byte aByteData : byteData) {
            sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Digest(in hex format):: " + sb.toString());

        //convert the byte to hex format method 2
        StringBuilder hexString = new StringBuilder();
        for (byte aByteData : byteData) {
            String hex = Integer.toHexString(0xff & aByteData);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        System.out.println("Digest(in hex format):: " + hexString.toString());

    }

    public static void getComTime() {
        Date date = new Date();
        System.out.println(date.toString());
        int a = 3;
    }

    // below function for demo validate sub list and this function to add to helper
    public static List<Route> subRoute(List<Route> routes, int index) {

        int last = index + 2;

        if (last > routes.size()) {
            last = routes.size();
        }
        // sub list route
        List<Route> subRoute = routes.subList(index, last);
        toString(subRoute);
        return subRoute;
    }



    public static void toString(List<Route> subRoute) {
        /*for (Route r : subRoute) {
            System.out.println(r.getRouteId() + " - " + r.getRouteName());
        }
        System.out.println("*****************************");*/

        System.out.println("Ngô Tiến Đạt");
    }
}
