package com.theopus.knucaTelegram.service.massupload.impl;

import com.theopus.knucaTelegram.entity.Group;
import com.theopus.knucaTelegram.repository.GroupRepository;
import com.theopus.knucaTelegram.service.massupload.GroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by irina on 03.07.17.
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupRepository groupRepository;

    private Set<Group> groupsCache = new HashSet<>();

    @Override
    public Set<Group> saveAll(Set<Group> groupSet) {
        Set<Group> result = new HashSet<>();
        for (Group g : groupSet) {
            if (groupsCache.contains(g))
                result.add(getGroup(g));
            else {
                Group findG = groupRepository.findByName(g.getName());
                if (findG != null){
                    result.add(findG);
                    groupsCache.add(findG);
                }
                else{
                    Group savedG = groupRepository.save(g);
                    result.add(savedG);
                    groupsCache.add(savedG);
                }

            }
        }
        return result;
    }

    @Override
    public void flush() {
        groupsCache = null;
    }

    private Group getGroup(Group group){
        for (Group g: groupsCache) {
            if (g.equals(group))
                return g;
        }
        return null;
    }
}