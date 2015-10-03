package com.fpt.router.crawler.dao.common;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;

/**
 * Hibernate implementation of GenericDao
 * A typesafe implementation of CRUD and finder methods based on Hibernate and Spring AOP
 * The finders are implemented through the executeFinder method. Normally called by the FinderIntroductionInterceptor
 * Created by Huynh Quang Thao on 10/3/15.
 */
/*
public class HibernateDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {
    private SessionFactory sessionFactory;
   // private FinderNamingStrategy namingStrategy = new SimpleFinderNamingStrategy(); // Default. Can override in config
   // private FinderArgumentTypeFactory argumentTypeFactory = new SimpleFinderArgumentTypeFactory(); // Default. Can override in config

    private Class<T> type;

    public HibernateDaoImpl(Class<T> type) {
        this.type = type;
    }

    public T create(T o) {
        getSession().save(o);
    }

    public T read(PK id) {
        return (T) getSession().get(type, id);
    }

    public void update(T o) {
        getSession().update(o);
    }

    public void delete(T o) {
        getSession().delete(o);
    }


    public Session getSession() {
        boolean allowCreate = true;
        //return SessionFactoryUtils.getSession(sessionFactory, allowCreate);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
*/
