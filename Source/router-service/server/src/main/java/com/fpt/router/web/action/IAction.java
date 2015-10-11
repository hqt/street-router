package com.fpt.router.web.action;

import com.fpt.router.web.config.ApplicationContext;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/27/15.
 */
public interface IAction {
    String execute(ApplicationContext context);
}