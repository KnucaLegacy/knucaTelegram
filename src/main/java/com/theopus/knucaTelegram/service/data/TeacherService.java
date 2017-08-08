package com.theopus.knucaTelegram.service.data;

import com.theopus.knucaTelegram.entity.schedule.Teacher;

import java.util.Collection;
import java.util.Set;


public interface TeacherService extends CustomService<Teacher>, SearchableService {

    Set<Teacher> saveAll(Collection<Teacher> teachers);

    Set<Teacher> getByAlliesName(String name);

    Teacher findByName(String name);

    void deleteById(long Id);

    void deleteTeacher(Teacher t);

    @Override
    Set<Teacher> getAll();
}
