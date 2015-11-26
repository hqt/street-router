package com.fpt.router.web.action.admin;


import com.fpt.router.artifacter.dao.StaffDAO;
import com.fpt.router.artifacter.model.entity.Staff;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.admin.StaffListVM;
import com.fpt.router.web.viewmodel.admin.StaffVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datnt on 10/20/2015.
 */
public class StaffListAction extends AdminAction {

    public String execute(ApplicationContext context) {
        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.ADMIN.name())) {
            return PAGE.COMMON.LOGIN;
        }

        StaffDAO dao = new StaffDAO();
        List<Staff> entities = dao.findAll();
        if (entities != null && !entities.isEmpty()) {
            StaffListVM staffListVM = new StaffListVM(entities);

            context.setAttribute("staffsVM", staffListVM);
        }

        return PAGE.STAFF.LIST;
    }

}
