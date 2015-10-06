package com.fpt.router.web.servlet;

import com.fpt.router.crawler.config.Config;
import com.fpt.router.web.action.ActionFactory;
import com.fpt.router.web.action.IAction;
import com.fpt.router.web.action.IActionFactory;
import com.fpt.router.web.config.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        // Set the response message's MIME type.
        response.setContentType("text/html;charset=UTF-8");
        // Allocate a output writer to write the response message into the network socket.
        PrintWriter out = response.getWriter();



        // Write the response message, in an HTML document.
        try {
            out.println("<!DOCTYPE html>");  // HTML 5
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>" + "Huynh Quang Thao" + "</title></head>");
            out.println("<body>");
            out.println("<h1>" + "Pham Huong Lan" + "</h1>");  // Prints "Hello, world!"
            out.println("</body></html>");
        } finally {
            out.close();  // Always close the output writer
        }
    }


}
