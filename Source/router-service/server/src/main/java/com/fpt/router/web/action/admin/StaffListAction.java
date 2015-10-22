package com.fpt.router.web.action.admin;


import com.fpt.router.artifacter.config.Config;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 10/20/2015.
 */
public class StaffListAction extends AdminAction {

    public String execute(ApplicationContext context) {
        String auth = super.execute(context);
        System.out.println(auth);
        return Config.WEB.PAGE + auth;
    }

}
