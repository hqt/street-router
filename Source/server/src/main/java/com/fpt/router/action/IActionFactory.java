package com.fpt.router.action;

import com.fpt.router.config.ApplicationContext;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/27/15.
 */
public interface IActionFactory {
    IAction getAction(ApplicationContext context);
}