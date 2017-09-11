package com.theopus.knucaTelegram.service.data.impl;

import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.Lesson;
import com.theopus.knucaTelegram.service.data.LessonService;
import com.theopus.knucaTelegram.service.data.repository.GroupRepository;
import com.theopus.knucaTelegram.service.data.GroupService;
import com.theopus.knucaTelegram.service.data.repository.LessonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class GroupServiceImpl implements GroupService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private String searchLine;

    @Resource
    private GroupRepository groupRepository;

    private Set<Group> groupsCache = new HashSet<>();

    @Override
    public Group saveOne(Group group) {
        Group result;
        if (groupsCache.contains(group))
            result = getGroup(group);
        else {
            Group findG = groupRepository.findByName(group.getName());
            if (findG != null){
                result = findG;
                groupsCache.add(findG);
            }
            else{
                Group savedG = groupRepository.save(group);
                result = savedG;
                groupsCache.add(savedG);
            }

        }
        return result;

    }

    @Override
    public Group getById(long id) {
        return groupRepository.findOne(id);
    }

    @PostConstruct
    @Override
    public void loadSearchLine() {
        StringBuilder line = new StringBuilder();
        Set<Group> groups = this.getAll();
        line.append(";");
        groups.forEach(group -> {
            line
                    .append(group.getName())
                    .append(";");
        });
        searchLine = line.toString();
    }

    @Override
    public String getSearchLine() {
        return searchLine;
    }

    @Override
    public Set<Group> saveAll(Collection<Group> groupSet) {
        return groupSet.stream().map(this::saveOne).collect(Collectors.toSet());
    }

    @Override
    public Group getByExactName(String name) {
        return groupRepository.findByName(name);
    }

    @Override
    public Set<Group> getByAlliesName(String name) {
        return groupRepository.findNameAlies(name);
    }

    @Override
    public long getCount() {
        return groupRepository.count();
    }

    @Override
    public void flush() {
        loadSearchLine();
        groupsCache = new HashSet<>();
    }

    @Override
    public void deleteBy(long id) {
        deleteBy(groupRepository.findOne(id));
    }

    @Override
    public void deleteBy(Group group) {
        for (Lesson lesson : group.getLessons()) {
            lesson.getGroups().remove(group);
        }
        groupRepository.delete(group);
    }

    @Override
    public Set<Group> getAll() {
        HashSet<Group> result = new HashSet<>();
        groupRepository.findAll().forEach(result::add);
        return result;
    }

    private Group getGroup(Group group){
        return groupsCache.stream().filter(group1 -> group1.equals(group)).findFirst().orElse(null);
    }
}
