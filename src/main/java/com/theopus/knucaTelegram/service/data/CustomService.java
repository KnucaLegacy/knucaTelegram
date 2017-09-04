package com.theopus.knucaTelegram.service.data;

import java.util.Collection;

public interface CustomService <T> {

    T saveOne(T t);

    T getById(long id);

    Collection<T> saveAll(Collection<T> collection);

    Collection<T> getAll();

    long getCount();

    void flush();

    void deleteBy(long id);

    void deleteBy(T t);
}
