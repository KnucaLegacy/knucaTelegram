package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Teacher;

import java.util.Collection;
import java.util.Set;


public interface TeacherService extends CustomService<Teacher>, SearchableService {

    Set<Teacher> saveAll(Collection<Teacher> teachers);

    Set<Teacher> getByAlliesName(String name);

    Teacher findByName(String name);

    void deleteById(long Id);

    void deleteTeacher(Teacher t);
}
