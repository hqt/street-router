package com.fpt.router.web.servlet;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.action.common.ActionFactory;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.action.common.IActionFactory;
import com.fpt.router.web.config.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/27/15.
 */
public class DispatcherServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DispatcherServlet");

        ApplicationContext context = new ApplicationContext(request, response);
        IActionFactory factory = new ActionFactory();
        IAction action = factory.getAction(context);
        String view = action.execute(context);
        // check if ajax call , don't forward or redirect
        if (view.equals(Config.AJAX_FORMAT)) {
            return;
        }

        if (view.length() >= 9 && view.substring(0, 9).equals("redirect.")) {
            view = view.substring(9, view.length());
            context.redirect(view);
        } else {
            context.forward(view);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request,response);
    }
}
