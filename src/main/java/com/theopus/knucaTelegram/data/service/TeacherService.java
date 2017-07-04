package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Teacher;

import java.util.Set;

/**
 * Created by irina on 03.07.17.
 */
public interface TeacherService {

    Set<Teacher> saveAll(Set<Teacher> teachers);
    void flush();
}
