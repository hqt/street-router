package com.fpt.router.web.entity;

import java.io.Serializable;

/**
 * Created by datnt on 10/7/2015.
 */
public class Route implements Serializable {

    public String routeName;
    public int no;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
}
