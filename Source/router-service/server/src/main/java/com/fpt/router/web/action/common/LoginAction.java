package com.fpt.router.web.action.common;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.dal.UserDAL;

/**
 * Created by datnt on 10/10/2015.
 */
public class LoginAction implements IAction {

    // here for fixed code, not use in future
    public class User {
        private String username;
        private String password;
        private String role;

        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    @Override
    public String execute(ApplicationContext context) {



        // get input username and password
        String username = context.getParameter("txtUsername");
        String password = context.getParameter("txtPassword");

        // Authenticate user
        boolean authenticated = UserDAL.checkLogin(username, password);

        // If success redirect use to list view, if fail show error message
        if (authenticated) {

            if (username.equals("admin")) {
                User user = new User(username,password,"admin");
                context.setSessionAttribute("user", user);
                return Config.WEB.REDIRECT + "staff/index";
            }

            if (username.equals("staff")) {
                User user = new User(username,password,"staff");
                context.setSessionAttribute("user", user);
                return Config.WEB.REDIRECT + "list";
            }

        }

        return "redirect.login";
    }
}
