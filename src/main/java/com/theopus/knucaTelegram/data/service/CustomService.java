package com.theopus.knucaTelegram.data.service;

import java.util.Collection;

public interface CustomService <T> {

    T saveOne(T t);

    T getById(long id);

    Collection<T> saveAll(Collection<T> collection);

    Collection<T> getAll();

    long getCount();

    void flush();


}
