package com.fpt.router.web.action.staff.station;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.PathInfoDAO;
import com.fpt.router.artifacter.dao.StationDAO;
import com.fpt.router.artifacter.model.entity.Station;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.staff.StationListVM;

import java.util.List;

/**
 * Created by datnt on 10/12/2015.
 */
public class StationListAction extends StaffAction {
    @Override
    public String execute(ApplicationContext context) {

        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        // get list of station from database
        StationDAO stationDAO = new StationDAO();
        List<Station> stations = stationDAO.findAll();

        // convert entity to model
        if (stations != null && !stations.isEmpty()) {
            StationListVM stationListVM = new StationListVM(stations);
            context.setAttribute("stationsVM", stationListVM);
        }

        return PAGE.STATION.LIST;
    }
}
