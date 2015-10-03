package com.fpt.router.crawler.dao;

import com.fpt.router.crawler.dao.common.JPADaoImpl;
import com.fpt.router.crawler.database.HibernateConnection;
import com.fpt.router.crawler.model.entity.Route;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 9/27/2015.
 */
public class RouteDAO extends JPADaoImpl<Route, Integer> {
}
