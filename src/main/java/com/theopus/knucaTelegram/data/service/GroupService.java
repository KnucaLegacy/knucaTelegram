package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Group;

import java.util.Set;

/**
 * Created by irina on 03.07.17.
 */
public interface GroupService {

    Set<Group> saveAll(Set<Group> group);
    Group getByName(String name);
    void flush();
}
