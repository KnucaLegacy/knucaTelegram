package com.theopus.knucaTelegram.service.massupload;

import com.theopus.knucaTelegram.entity.Group;

import java.util.Set;

/**
 * Created by irina on 03.07.17.
 */
public interface GroupService {

    Set<Group> saveAll(Set<Group> group);
    void flush();
}
