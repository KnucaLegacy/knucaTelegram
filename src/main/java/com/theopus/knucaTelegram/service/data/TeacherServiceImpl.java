package com.theopus.knucaTelegram.service.data;

import com.theopus.knucaTelegram.entity.schedule.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private Map<Long, Teacher> teacherServiceMap;

    private RestTemplate restTemplate;

    public TeacherServiceImpl() {
        teacherServiceMap = new ConcurrentSkipListMap<>();
        restTemplate = new RestTemplate();
        Arrays.stream(restTemplate.getForObject("http://localhost:8080/teachers", Teacher[].class))
                .forEach(t -> teacherServiceMap.put(t.getId(), t));
    }

    @Override
    public Teacher saveOne(Teacher teacher) {
        return null;
    }

    @Override
    public Teacher getById(long id) {
        return teacherServiceMap.get(id);
    }

    @Override
    public Set<Teacher> saveAll(Collection<Teacher> teachers) {
        return null;
    }

    @Override
    public Set<Teacher> getByAlliesName(String name) {
        return teacherServiceMap.values().stream().filter(teacher -> teacher.getName().toLowerCase()
                .contains(name.toLowerCase())).collect(Collectors.toSet());
    }

    @Override
    public Teacher findByName(String name) {
        for (Teacher teacher : teacherServiceMap.values()) {
            if (teacher.getName().equals(name)) {
                return teacher;
            }
        }
        return null;
    }

    @Override
    public void deleteById(long Id) {

    }

    @Override
    public void deleteTeacher(Teacher t) {

    }

    @Override
    public Set<Teacher> getAll() {
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
    public void deleteBy(Teacher teacher) {

    }

    @Override
    public void loadSearchLine() {

    }

    private String searchLine;

    @Override
    public String getSearchLine() {
        if (searchLine == null) {
            searchLine = teacherServiceMap.values().stream()
                    .map(group -> group.getName())
                    .collect(Collectors.joining(";"));
        }
        return searchLine;
    }

}
