package com.fpt.router.web.action;

import com.fpt.router.web.config.ApplicationContext;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/27/15.
 */
public class ActionFactory implements IActionFactory {

    @Override
    public IAction getAction(ApplicationContext context) {
        String parameter = context.getParameter("action");
        IAction action = null;

        // handle link such as localhost/qna/login.jsp
        // forward to WEB-INF/login.jsp
        String url = context.getServletPath();
       /* if (url.equals("/list")) {
            action = new FindRouteAction();
        }*//* else if (url.equals("/questions")) {
            action = new DetailQuestionAction();
        } else if (url.equals("/login")) {
            context.setAttribute(Config.DIRECT_PAGE_ATTRIBUTE, "WEB-INF/login.jsp");
            action = new DirectPageAction();
        }
        }*/

        if (action != null) {
            return action;
        }


        if (parameter.equals("find")) {
            // action = new FindRouteAction();
        } else if (parameter.equals("pdf")) {
            // action = new PDFRouteAction();
        } else {
            // action = new PDFRouteAction();
        }

        // handle "action" parameter that end with jsp. will go directly to jsp page
        /**
         * if (parameter.length() >= 4) { String lastStr =
         * parameter.substring(parameter.length() - 4, parameter.length()); if
         * (lastStr.equals(".jsp")) { return new JSPPageAction(); } }
         */
        /*if (parameter.equals("Log in")) {
         action = new LoginAction();
         } else if (parameter.equals("detailquestion")) {
         action = new DetailQuestionAction();
         } else if (parameter.equals("Register")) {
         action = new RegisterAction();
         }
         */
        return action;
    }
}
