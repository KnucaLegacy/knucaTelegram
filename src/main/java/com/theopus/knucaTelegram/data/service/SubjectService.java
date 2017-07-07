package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Subject;

import java.util.Set;


public interface SubjectService {

    Set<Subject> saveAll(Set<Subject> subjects);
    Subject saveOne(Subject subject);
    void flush();

}
