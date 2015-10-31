package com.fpt.router.web.action.staff;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.PathInfoDAO;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.staff.StationListVM;

/**
 * Created by datnt on 10/12/2015.
 */
public class StationListAction implements IAction {
    @Override
    public String execute(ApplicationContext context) {
/*

        // get parameter route no
        int paramRouteNo = (Integer) context.getSessionAttribute("routeNo");

        // process to get list station of route through pathinfo
        PathInfoDAO pathInfoDao = new PathInfoDAO();
        if (paramRouteNo != -1) {
            StationListVM stationListVM = new StationListVM(pathInfoDao.getListPathInfoByRouteNo(paramRouteNo));
            context.setAttribute("stations", stationListVM.getStationListVM());
            return Config.WEB.PAGE + "station/index.jsp"; // redirect detail route view
        }
*/

        return null;
    }
}
