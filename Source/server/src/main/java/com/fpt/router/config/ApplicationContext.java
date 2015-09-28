package com.fpt.router.config;

import com.fpt.router.model.CityMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/27/15.
 */
public class ApplicationContext {

    public static CityMap map;

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public ApplicationContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        response.setCharacterEncoding("UTF-8");
    }

    public void redirect(String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException ex) {
            Logger.getLogger(ApplicationContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void forward(String url) {
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ApplicationContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ApplicationContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getParameter(String parameter) {
        return request.getParameter(parameter);
    }

    public int getIntParameter(String parameter) {
        return Integer.parseInt(request.getParameter(parameter));
    }

    public void setAttribute(String attributeName, Object attributeValue) {
        request.setAttribute(attributeName, attributeValue);
    }

    public void setResponseContentType(String contentType) {
        response.setContentType(contentType);
    }

    public void setResponseContentLength(int length) {
        response.setContentLength(length);
    }

    public Object getAttribute(String attributeName) {
        return request.getAttribute(attributeName);
    }

    public void setSessionAttribute(String attributeName, Object attributeValue) {
        request.getSession().setAttribute(attributeName, attributeValue);
    }

    public Object getSessionAttribute(String attributeName) {
        return request.getSession().getAttribute(attributeName);
    }

    public String getServletPath() {
        return request.getServletPath();
    }

    public String getPathInfo() {
        return request.getPathInfo();
    }
    /*
     *
     * Description : get Writer
     *
     * @return
     */
    public PrintWriter getWriter(){
        try {
            return response.getWriter() ;
        } catch (IOException ex) {
            Logger.getLogger(ApplicationContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null ;
    }

    public ServletOutputStream getOutputStream() {
        try {
            return response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
     *
     * Description : getParameterValues
     *
     * @return
     */
    public String[] getParameterValues(String name){
        return request.getParameterValues(name);
    }

    /*
     *
     * Description :
     *
     * @return
     */
    public void invalidate(){
        request.getSession().invalidate();
    }

}