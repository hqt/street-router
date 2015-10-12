package com.fpt.router.web.action.common;

import com.fpt.router.web.action.common.IAction;
import com.fpt.router.web.config.ApplicationContext;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 9/27/15.
 */
public interface IActionFactory {
    IAction getAction(ApplicationContext context);
}