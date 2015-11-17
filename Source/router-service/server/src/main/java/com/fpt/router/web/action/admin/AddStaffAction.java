package com.fpt.router.web.action.admin;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.StaffDAO;
import com.fpt.router.artifacter.model.entity.Staff;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.action.util.PasswordUtils;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/12/15.
 */
public class AddStaffAction extends AdminAction {


    @Override
    public String execute(ApplicationContext context) {
        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.ADMIN.name())) {
            return PAGE.COMMON.LOGIN;
        }

        PasswordUtils passwordUtils = new PasswordUtils();

        Staff khuong = new Staff();
        String password = passwordUtils.md5("123456");
        khuong.setRole(0);
        khuong.setStaffName("Man Huỳnh Khương");
        khuong.setStaffEmail("khuongbungbu@gmail.com");
        khuong.setPhoneNumber("0934992244");
        khuong.setPassword(password);

        Staff quy = new Staff();
        quy.setRole(0);
        quy.setStaffName("Hà Kim Quy");
        quy.setStaffEmail("hakimquy@gmail.com");
        quy.setPhoneNumber("0934992244");
        quy.setPassword(password);

        Staff ngoan = new Staff();
        ngoan.setRole(1);
        ngoan.setStaffName("Trần Thanh Ngoan");
        ngoan.setStaffEmail("khuongbungbu@gmail.com");
        ngoan.setPhoneNumber("0934992244");
        ngoan.setPassword(password);

        Staff thao = new Staff();
        thao.setRole(2);
        thao.setStaffName("Huỳnh Quang Thảo");
        thao.setStaffEmail("huynhquangthao@gmail.com");
        thao.setPhoneNumber("0934992244");
        thao.setPassword(password);

        Staff nam = new Staff();
        nam.setRole(0);
        nam.setStaffName("Nguyễn Trung Nam");
        nam.setStaffEmail("nguyentrumnam@gmail.com");
        nam.setPhoneNumber("0934992244");
        nam.setPassword(password);

        Staff dat = new Staff();
        dat.setRole(1);
        dat.setStaffName("Ngô Tiến Đạt");
        dat.setStaffEmail("ngotiendat@gmail.com");
        dat.setPhoneNumber("0934992244");
        dat.setPassword(password);

        StaffDAO dao = new StaffDAO();
        dao.create(khuong);
        dao.create(quy);
        dao.create(ngoan);
        dao.create(thao);
        dao.create(nam);
        dao.create(dat);

        return PAGE.STAFF.CREATE;
    }
}
