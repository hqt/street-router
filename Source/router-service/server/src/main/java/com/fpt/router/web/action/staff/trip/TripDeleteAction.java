package com.fpt.router.web.action.staff.trip;

import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.TripDAO;
import com.fpt.router.artifacter.model.entity.Trip;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 11/4/2015.
 */
public class TripDeleteAction implements IAction {
    @Override
    public String execute(ApplicationContext context) {
        System.out.println("Delete Trip Action...");
        // get parameter
        String tripIdParam = context.getParameter("tripId");

        if (tripIdParam != null) {
            // cast param
            int tripId = -1;
            try {
                tripId = Integer.parseInt(tripIdParam);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }

            if (tripId != -1) {
                // deleting trip process
                TripDAO tripDAO = new TripDAO();
                Trip trip = tripDAO.read(tripId);
                if (trip != null) {
                    tripDAO.deleteTrip(trip);
                }
            }
        }

        return Config.AJAX_FORMAT;
    }
}