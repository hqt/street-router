package com.fpt.router.web.action.common;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.StaffDAO;
import com.fpt.router.artifacter.model.entity.Staff;
import com.fpt.router.web.action.util.PasswordUtils;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 10/10/2015.
 */
public class LoginAction implements IAction {

    public static String msg = "";

    @Override
    public String execute(ApplicationContext context) {

        // get user
        String emailParam = context.getParameter("txtEmail");
        String password = context.getParameter("txtPassword");

        String path = context.getServletPath();
        if (emailParam == null || password == null) {
            return Config.WEB.REDIRECT + path;
        }

        // read staff and check staff already exist
        PasswordUtils passwordUtils = new PasswordUtils();
        StaffDAO staffDAO = new StaffDAO();
        Staff staff = staffDAO.findStaffByEmail(emailParam, passwordUtils.md5(password));

        if (staff != null) {
            context.setSessionAttribute("user", staff);
            /*path = path.substring(1, path.length());*/
            if (path.equals(URL.COMMON.LOGIN)) {
                return Config.WEB.REDIRECT + URL.COMMON.ROUTE_LIST;
            }
            return Config.WEB.REDIRECT + path;
        } else {
            msg = "This account is not exist!";
            context.setAttribute("msg", msg);
        }

        return Config.WEB.REDIRECT + URL.COMMON.ROUTE_LIST;
    }
}
