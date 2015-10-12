package com.fpt.router.web.action.common;

import com.fpt.router.web.config.ApplicationContext;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Purpose: All actions need authentication need extends this Action
 * If user doesn't login yet. return to login page. After login successfully will return to old page (by parameter has passed)
 * Created by Huynh Quang Thao on 10/11/15.
 */
public class AuthAction implements IAction {

    @Override
    public String execute(ApplicationContext context) {
        return null;
    }

    private String permissionDenied(ApplicationContext context) {
        try {
            String redirect = getCurrentUrl(context);
            String url = "/login&redirect=" + URLEncoder.encode(redirect, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }

    private String getCurrentUrl(ApplicationContext context) {
        if (context.getQueryString() != null) {
            return context.getRequestURI() + "?" + context.getQueryString();
        } else {
            return context.getRequestURI();
        }
    }
}
