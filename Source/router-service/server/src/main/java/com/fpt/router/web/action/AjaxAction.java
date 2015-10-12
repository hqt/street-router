package com.fpt.router.web.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fpt.router.artifacter.config.Config;
import com.fpt.router.artifacter.dao.RouteDAO;
import com.fpt.router.artifacter.model.entity.Route;
import com.fpt.router.web.config.ApplicationContext;
import com.fpt.router.web.viewmodel.RouteListVM;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by datnt on 10/11/2015.
 */
public class AjaxAction implements IAction {
    @Override
    public String execute(ApplicationContext context) {

        int pageNum = context.getIntParameter("pageNum");
        RouteDAO routeDAO = new RouteDAO();
        List<Route> routes = routeDAO.getRoutesAtPage(pageNum);
        RouteListVM routeListVM = new RouteListVM(routes);

        // convert this list to json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;
        try {
            json = ow.writeValueAsString(routeListVM);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter out = context.getWriter();
        out.write(json);


        return Config.AJAX_FORMAT;
    }
}
