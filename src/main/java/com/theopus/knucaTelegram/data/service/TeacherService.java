package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Teacher;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Set;


public interface TeacherService extends CustomService<Teacher> {

    Set<Teacher> saveAll(Collection<Teacher> teachers);

    Set<Teacher> getByAlliesName(String name);

    Teacher findByName(String name);
}
