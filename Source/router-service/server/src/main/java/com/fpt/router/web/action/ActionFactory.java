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


        if (url.equals("/login")) {
            System.out.println("go to login page");
            action = new DirectPageAction();
        }


        // handle "action" parameter that end with jsp. will go directly to jsp page
        /**
         * if (parameter.length() >= 4) { String lastStr =
         * parameter.substring(parameter.length() - 4, parameter.length()); if
         * (lastStr.equals(".jsp")) { return new JSPPageAction(); } }
         */

        if (actionCommand != null) {
            System.out.println("action command not null");
            if (actionCommand.equals("login")) {
                System.out.println("login action not null");
                action = new LoginAction();
            }
        }

        return action;
    }
}
