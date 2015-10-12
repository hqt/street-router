package com.fpt.router.web.action;

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


        if (url == null || url.equals("")) {
            int a = 3;
        }

        if (url.equals("/login")) {
            action = new DirectPageAction();
        } else if (url.equals("/list")) {
            action = new MainAction();
        } else if (url.equals("/paging")) {
            action = new AjaxAction();
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
            } else if (actionCommand.equals("detailRoute")) {
                action = new DetailRouteAction();
            } else if (actionCommand.equals("paging")) {
                action = new AjaxAction();
            } else if (actionCommand.equals("detail")) {
                action = new DetailRouteAction();
            }
        }

        return action;
    }
}
