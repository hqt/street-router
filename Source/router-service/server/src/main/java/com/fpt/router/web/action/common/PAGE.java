package com.fpt.router.web.action.common;

import com.fpt.router.artifacter.config.Config;

/**
 * Created by datnt on 11/15/2015.
 */
public class PAGE {

    public static class COMMON {
        public static final String LOGIN = Config.WEB.PAGE +  "/login.jsp";
    }

    public static class ROUTE {
        public static final String LIST =  Config.WEB.PAGE + "/route/index.jsp";
        public static final String ADD = Config.WEB.PAGE + "/route/indexAdd.jsp";
        public static final String DETAIL = Config.WEB.PAGE + "/route/indexDetail.jsp";
    }

    public static class STATION {
        public static final String LIST = Config.WEB.PAGE + "/station/index.jsp";
        public static final String ADD = Config.WEB.PAGE + "/station/add.jsp";
    }

    public static class TRIP {
        public static final String LIST = Config.WEB.PAGE +  "/trip/index.jsp";
        public static final String ADD = Config.WEB.PAGE + "/trip/add.jsp";
    }

    public static class STAFF {
        public static final String LIST = Config.WEB.PAGE + "/staff/index.jsp";
        public static final String CREATE = Config.WEB.PAGE + "/staff/indexCreate.jsp";
    }

    public static class NOTIFICATION {
        public static final String STATION_LIST = Config.WEB.PAGE + "/notification/station/index.jsp";
        public static final String TRIP_LIST = Config.WEB.PAGE + "/notification/trip/index.jsp";
    }

}
