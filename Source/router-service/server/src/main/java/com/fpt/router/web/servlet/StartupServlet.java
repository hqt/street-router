package com.fpt.router.web.servlet;


import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.MapDAL;
import com.fpt.router.artifacter.model.algorithm.CityMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/27/15.
 */
public class StartupServlet extends HttpServlet {
    public static CityMap map;

    public void init() throws ServletException {
        System.out.println("----------");
        System.out.println("Loading Database zzzz");
        map = MapDAL.readDatabase();
        System.out.println("thao thao hi hi : " + map.routes.size());
        System.out.println("fuck");
        System.out.println("----------");
    }
}
