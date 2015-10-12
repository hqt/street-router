package com.fpt.router.web.action.common;

import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.dal.UserDAL;

/**
 * Created by datnt on 10/10/2015.
 */
public class LoginAction implements IAction {

    @Override
    public String execute(ApplicationContext context) {



        // get input username and password
        String username = context.getParameter("txtUsername");
        String password = context.getParameter("txtPassword");

        // Authenticate user
        boolean authenticated = UserDAL.checkLogin(username, password);

        // If success redirect use to list view, if fail show error message
        if (authenticated) {
            return "redirect.list";
        }


        return "redirect.login";
    }
}
