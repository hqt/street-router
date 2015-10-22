package com.fpt.router.web.framework;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/3/15.
 */
public class CharsetFilter implements Filter {
    private String encoding;

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("requestEncoding");

        if (encoding == null) encoding = "UTF-8";
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {

        // Respect the client-specified character encoding
        // (see HTTP specification section 3.4.1)
        if (null == request.getCharacterEncoding())
            request.setCharacterEncoding(encoding);

        /**
         * Set the default response content type and encoding
         */
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");


        next.doFilter(request, response);
    }

    public void destroy() {
    }
}