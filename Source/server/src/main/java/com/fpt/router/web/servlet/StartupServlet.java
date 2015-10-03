package com.fpt.router.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/27/15.
 */
public class StartupServlet extends HttpServlet {
    public void init() throws ServletException {
        System.out.println("----------");
        System.out.println("Loading Database");
        System.out.println("----------");
    }
}
