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

    private static EntityManager staticEntityManager = null;

    // this variable must turn on manually to true. and turn off to false at right time
    private static boolean isUsedStaticEntityManager = false;

    // this method call implicit by createEntityManager. in case isUsedStaticEntityManager = true
    private static EntityManager createStaticEntityManager() {
        if (staticEntityManager == null) {
            staticEntityManager = factory.createEntityManager();
        }
        return staticEntityManager;
    }

    // this method will be called when disable static entity manager.
    private static void closeStaticEntityManager() {
        staticEntityManager.close();
        staticEntityManager = null;
    }

    public EntityManager createEntityManager() {
        if (isUsedStaticEntityManager) {
            // create static factory. using for create map
            return createStaticEntityManager();
        } else {
            // create local factory. using for web framework
            return factory.createEntityManager();
        }
    }

    public void closeEntityManger(EntityManager entityManager) {
        if (!isUsedStaticEntityManager) {
            entityManager.close();
        }
        // do nothing for static manager. must close manually
    }


    /////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////


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
        EntityManager entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.getTransaction().commit();
        closeEntityManger(entityManager);
        return t;
    }

    @Override
    public T read(PK id) {
        EntityManager entityManager = createEntityManager();
        T t = entityManager.find(entityClass, id);
        closeEntityManger(entityManager);
        return t;
    }

    @Override
    public T update(T t) {
        EntityManager entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        T result = entityManager.merge(t);
        entityManager.getTransaction().commit();
        closeEntityManger(entityManager);
        return result;
    }

    @Override
    public void delete(T t) {
        EntityManager entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        t = entityManager.merge(t);
        entityManager.remove(t);
        entityManager.getTransaction().commit();
        closeEntityManger(entityManager);
    }

    public List<T> findAll() {
        EntityManager entityManager = createEntityManager();
        List<T> resultList = entityManager.createQuery("from " + entityClass.getName()).getResultList();
        closeEntityManger(entityManager);
        return resultList;
    }

    public void deleteAll() {
        EntityManager entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        System.out.println("debug: " + entityClass.getName());
        System.out.println("debug: " + entityClass.getSimpleName());
        System.out.println("debug: " + entityClass.getCanonicalName());
        int count = entityManager.createQuery("delete from " + entityClass.getSimpleName()).executeUpdate();
        System.out.println("total " + count + " entities delete");
        entityManager.getTransaction().commit();
        closeEntityManger(entityManager);
    }

    public static void closeFactory() {
        factory.close();
    }

    public static void enableStaticEntityManager() {
        isUsedStaticEntityManager = true;
    }

    public static void disableStaticEntityManager() {
        isUsedStaticEntityManager = false;
        closeStaticEntityManager();
    }
}