package com.fpt.router.web.action.common;

/**
 * Created by datnt on 11/15/2015.
 */
public class URL {

    public static class COMMON {
        public static final String LOGIN = "/login";
        public static final String ROUTE_LIST = "/route/list";
        public static final String ROUTE_DETAIL = "/route/detail";
    }

    public static class STAFF {

        public static final String ROUTE_ADD = "/route/add";
        public static final String ROUTE_UPDATE = "/route/update";
        public static final String ROUTE_DELETE = "/route/delete";
        public static final String ROUTE_PARSE = "/route/parse";

        public static final String STATION_LIST = "/station/list";
        public static final String STATION_ADD = "/station/add";
        public static final String STATION_UPDATE = "/station/update";

        public static final String TRIP_ADD = "/trip/add";
        public static final String TRIP_UPDATE = "/trip/update";
        public static final String TRIP_DELETE = "/trip/delete";

        public static final String COMPARE = "/compare";
        public static final String NOF_STATION_LIST = "/notification/station/list";
        public static final String NOF_STATION_APPROVE = "/notification/station/approve";
        public static final String NOF_STATION_BLOCK = "/notification/station/block";
        public static final String NOF_STATION_UNBLOCK = "/notification/station/unblock";
        public static final String NOF_STATION_DELETE = "/notification/station/delete";
        public static final String NOF_TRIP_LIST = "/notification/trip/list";
        public static final String NOF_TRIP_APPROVE = "/notification/trip/approve";
        public static final String NOF_TRIP_BLOCK = "/notification/trip/block";
        public static final String NOF_TRIP_UNBLOCK = "/notification/trip/unblock";
        public static final String NOF_TRIP_DELETE = "/notification/trip/delete";

        public static final String CONFIGURE = "/configuration";
        public static final String CONFIGURE_SOURCE = "/configuration/source";

        public static final String PARSE_SOURCE = "/parseSource";
    }

    public static class ADMIN {
        public static final String STAFF_ADD = "/staff/add";
        public static final String STAFF_LIST = "/staff/list";
        public static final String STAFF_UPDATE = "/staff/update";
        public static final String STAFF_DELETE = "/staff/delete";
    }

    public static class API {
        public static final String TWO_POINT = "/api/twopoint";
        public static final String MUlTI_POINT = "/search/multi";
    }
}
