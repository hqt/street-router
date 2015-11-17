package com.fpt.router.web.action.staff;

import com.fpt.router.artifacter.model.entity.CityMap;
import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Created by datnt on 10/31/2015.
 */
public class CompareMapAction implements IAction {

    private CityMap mapServer;
    private CityMap mapDB;

    /*
        Compare between server and database
     */
    @Override
    public String execute(ApplicationContext context) {

        return null;
    }
}
