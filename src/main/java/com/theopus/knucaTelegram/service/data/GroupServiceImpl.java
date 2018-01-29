package com.theopus.knucaTelegram.service.data;

import com.theopus.knucaTelegram.entity.schedule.Group;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private String address;
    private Map<Long, Group> groupServiceMap;

    private RestTemplate restTemplate;

    public GroupServiceImpl(@Value("${rest.host}") String address) {
        this.address = address;
        groupServiceMap = new ConcurrentSkipListMap<>();
        restTemplate = new RestTemplate();
        Arrays.stream(restTemplate.getForObject("http://" + this.address + "/groups", Group[].class))
                .forEach(g -> groupServiceMap.put(g.getId(), g));
    }

    @Override
    public Group saveOne(Group group) {
        return null;
    }

    @Override
    public Group getById(long id) {
        return null;
    }

    @Override
    public Set<Group> saveAll(Collection<Group> group) {
        return null;
    }

    @Override
    public Group getByExactName(String name) {
        for (Group group : groupServiceMap.values()) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }

    @Override
    public Set<Group> getByAlliesName(String name) {
        return groupServiceMap.values().stream().filter(group -> group.getName().toLowerCase()
                .contains(name.toLowerCase())).collect(Collectors.toSet());
    }

    @Override
    public Set<Group> getAll() {
        return null;
    }

    @Override
    public long getCount() {
        return 0;
    }

    @Override
    public void flush() {

    }

    @Override
    public void deleteBy(long id) {

    }

    @Override
    public void deleteBy(Group group) {

    }

    @Override
    public void loadSearchLine() {

    }

    private String searchLine;

    @Override
    public String getSearchLine() {
        if (searchLine == null) {
            searchLine = groupServiceMap.values().stream()
                    .map(group -> group.getName())
                    .collect(Collectors.joining(";"));
        }
        return searchLine;
    }
}
