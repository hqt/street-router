package com.fpt.router.crawler.dao.common;

import com.fpt.router.crawler.model.entity.IEntity;

import java.io.Serializable;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/1/15.
 */
public interface GenericDao<T extends IEntity, PK extends Serializable> {
    T create(T t);

    T read(PK id);

    T update(T t);

    void delete(T t);
}