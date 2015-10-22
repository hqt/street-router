package com.fpt.router.web.action.common;

import com.fpt.router.artifacter.config.Config;
//import com.fpt.router.web.action.api.TwoPointRouteAction;
import com.fpt.router.web.action.admin.AdminAction;
import com.fpt.router.web.action.admin.StaffListAction;
import com.fpt.router.web.action.api.TwoPointRouteAction;
import com.fpt.router.web.action.staff.*;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/27/15.
 */
public class ActionFactory implements IActionFactory {

    @Override
    public IAction getAction(ApplicationContext context) {
        String actionCommand = context.getParameter("action");
        IAction action = null;

        // handle link such as localhost/qna/login.jsp
        // forward to WEB-INF/login.jsp
        String url = context.getServletPath();

        if (url.equals("/login")) {
            context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/login.jsp");
            action = new DirectPageAction();
        } else if (url.equals("/list")) {
            action = new RouteListAction();
        } else if (url.equals("/paging")) {
            action = new RouteListAJAXAction();
        } else if (url.equals("/detail")) {
            action = new StationListAction();
        } else if (url.equals("/api/twopoint")) {
            action = new TwoPointRouteAction();
        } else if (url.equals("/update")) {
            context.setAttribute(Config.WEB.DIRECT_PAGE_ATTRIBUTE, Config.WEB.PAGE + "/update.jsp");
            action = new DirectPageAction();
        } else if (url.equals("/admin")) {
            action = new AdminAction();
        } else if (url.equals("/staff/index")) {
            action = new StaffListAction();
        } else if (url.equals("/search/multi")) {
            action = new MultiPointAction();
        }
        // handle "action" parameter that end with jsp. will go directly to jsp page
        /**
         * if (parameter.length() >= 4) { String lastStr =
         * parameter.substring(parameter.length() - 4, parameter.length()); if
         * (lastStr.equals(".jsp")) { return new JSPPageAction(); } }
         */

        System.out.println("Action Command: " +actionCommand);

        if (actionCommand != null) {
            if (actionCommand.equals("login")) {
                action = new LoginAction();
            } else if (actionCommand.equals("paging")) {
                action = new RouteListAJAXAction();
            } else if (actionCommand.equals("detail")) {
                action = new DetailRouteAction();
            } else if (actionCommand.equals("update")) {
                action = new RouteUpdateAction();
            } else if (actionCommand.equals("multiSearch")) {
            }
        }

        return action;
    }
}
