package com.fpt.router.web.dal;

/**
 * Created by datnt on 10/10/2015.
 */
public class UserDAL {

    public static boolean checkLogin(String username, String password) {

        boolean status = false;

        if (username.equals("admin") && password.equals("123")) {
            status = true;
        }

        if (username.equals("staff") && password.equals("123")) {
            status = true;
        }

        return status;
    }

}
