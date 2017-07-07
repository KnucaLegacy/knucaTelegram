package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Teacher;

import java.util.Set;


public interface TeacherService {

    Set<Teacher> saveAll(Set<Teacher> teachers);
    void flush();
}
