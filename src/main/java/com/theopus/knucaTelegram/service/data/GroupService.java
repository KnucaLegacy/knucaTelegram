package com.theopus.knucaTelegram.service.data;

import com.theopus.knucaTelegram.entity.Group;

import java.util.Collection;
import java.util.Set;

public interface GroupService extends CustomService<Group>, SearchableService {

    Set<Group> saveAll(Collection<Group> group);

    Group getByExactName(String name);

    Set<Group> getByAlliesName(String name);
    
    Set<Group> getAll();
}
