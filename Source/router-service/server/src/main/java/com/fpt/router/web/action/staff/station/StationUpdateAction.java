package com.fpt.router.web.action.staff.station;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.StationDAO;
import com.fpt.router.artifacter.model.entity.Station;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/3/2015.
 */
public class StationUpdateAction implements IAction {
    @Override
    public String execute(ApplicationContext context) {

        // get parameter from server
        String stationIdParam = context.getParameter("stationId");
        String stationNameParam = context.getParameter("stationName");
        String stationStreetParam = context.getParameter("stationStreet");

        // update station action
        if (stationIdParam != null) {
            int stationId = -1;
            try {
                stationId = Integer.parseInt(stationIdParam);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            if (stationId != -1) {
                StationDAO stationDAO = new StationDAO();
                Station station = stationDAO.read(stationId);
                if (station != null) {
                    station.setName(stationNameParam);
                    station.setStreet(stationStreetParam);
                    // update station
                    stationDAO.update(station);
                }
            }
        }

        return Config.AJAX_FORMAT;
    }
}
