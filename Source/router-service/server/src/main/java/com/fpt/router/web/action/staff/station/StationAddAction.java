package com.fpt.router.web.action.staff.station;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.StationDAO;
import com.fpt.router.artifacter.model.entity.Station;
import com.fpt.router.web.action.common.PAGE;
import com.fpt.router.web.action.common.Role;
import com.fpt.router.web.action.staff.StaffAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 10/28/2015.
 */
public class StationAddAction extends StaffAction {

    @Override
    public String execute(ApplicationContext context) {
        String authenticated = super.execute(context);
        if (authenticated == null || !authenticated.equals(Role.STAFF.name())) {
            return PAGE.COMMON.LOGIN;
        }

        // get parameter
        String sCodeID = context.getParameter("stationID");
        String sNameParam = context.getParameter("stationName");
        String sLatParam = context.getParameter("stationLat");
        String sLongParam = context.getParameter("stationLong");
        String sStreetParam = context.getParameter("stationStreet");

        if (sCodeID == null || sNameParam == null || sLatParam == null || sLongParam == null || sStreetParam == null) {
            return null;
        }

        // check station already exist
        StationDAO stationDAO = new StationDAO();

        Station staiton = new Station();

        return PAGE.STATION.ADD;
    }
}
