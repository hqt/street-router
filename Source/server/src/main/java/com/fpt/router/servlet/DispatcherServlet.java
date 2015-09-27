package com.fpt.router.servlet;

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
