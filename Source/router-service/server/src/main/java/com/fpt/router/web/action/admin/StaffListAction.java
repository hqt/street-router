package com.fpt.router.web.action.admin;


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

        StaffVM s1 = new StaffVM(1, "Ngô Tiến Đạt", "datntse60980", "datntse60980@gmail.com", "khongcopass", "01265663317");
        StaffVM s2 = new StaffVM(2, "Lê Trần Nhật Vỹ", "vyltnse67823", "vyltnse67823@gmail.com", "khongcopass", "34985794375");
        StaffVM s3 = new StaffVM(3, "Hồ Đoàn Trung", "trunghd63712", "trunghd63712@gmail.com", "khongcopass", "346798123786");
        StaffVM s4 = new StaffVM(4, "Nguyễn Trung Nam", "namnt69126", "namnt69126@gmail.com", "khongcopass", "34856890712");
        StaffVM s5 = new StaffVM(5, "Huỳnh Quang Thảo", "thaohq68901", "thaohq68901@gmail.com", "khongcopass", "42543765873");
        StaffVM s6 = new StaffVM(6, "Hà Kim Quy", "quyhk68217", "datntse60980@gmail.com", "khongcopass", "4727362778");

        List<StaffVM> staffVMs = new ArrayList<>();
        staffVMs.add(s1);
        staffVMs.add(s2);
        staffVMs.add(s3);
        staffVMs.add(s4);
        staffVMs.add(s5);
        staffVMs.add(s6);
        StaffListVM staffListVM = new StaffListVM();
        staffListVM.setStaffVMList(staffVMs);

        context.setAttribute("staffsVM", staffListVM);

        return PAGE.STAFF.LIST;
    }

}
