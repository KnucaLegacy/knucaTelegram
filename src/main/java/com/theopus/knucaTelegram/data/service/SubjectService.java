package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Subject;

import java.util.Set;

/**
 * Created by irina on 03.07.17.
 */
public interface SubjectService {

    Set<Subject> saveAll(Set<Subject> subjects);
    Subject saveOne(Subject subject);
    void flush();

}
