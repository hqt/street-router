package com.fpt.router.artifacter.dao.common;


import com.fpt.router.artifacter.model.entity.IEntity;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/1/15.
 */
public class JPADaoImpl<T extends IEntity, PK extends Serializable> implements GenericDao<T, PK> {

    protected Class<T> entityClass;

    /**
     * This variable HAVE TO be static or it will create new connection
     * every totalTime a derived class of this is created, which causes "too many connections" error.
     */
    public static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("SWRPersistenceUnit");

    public JPADaoImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass
                .getActualTypeArguments()[0];
    }

    public JPADaoImpl(Class<T> type) {
        this.entityClass = type;
    }

    @Override
    public T create(T t) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.getTransaction().commit();
        entityManager.close();
        return t;
    }

    @Override
    public T read(PK id) {
        EntityManager entityManager = factory.createEntityManager();
        T t = entityManager.find(entityClass, id);
        //entityManager.close();
        return t;
    }

    @Override
    public T update(T t) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        T result = entityManager.merge(t);
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

    @Override
    public void delete(T t) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        t = entityManager.merge(t);
        entityManager.remove(t);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<T> findAll() {
        EntityManager entityManager = factory.createEntityManager();
        List<T> resultList = entityManager.createQuery("from " + entityClass.getName()).getResultList();
        //entityManager.close();
        return resultList;
    }

    public void deleteAll() {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        System.out.println("debug: " + entityClass.getName());
        System.out.println("debug: " + entityClass.getSimpleName());
        System.out.println("debug: " + entityClass.getCanonicalName());
        int count = entityManager.createQuery("delete from " + entityClass.getSimpleName()).executeUpdate();
        System.out.println("total " + count + " entities delete");
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static void closeFactory() {
        factory.close();
    }
}