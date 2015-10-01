package com.fpt.router.dal.common;

import java.io.Serializable;

/**
 * Purpose:
 * Created by Huynh Quang Thao on 10/1/15.
 */
public interface GenericDAO<T, PK extends Serializable> {
    T create(T t);

    T read(PK id);

    T update(T t);

    void delete(T t);
}