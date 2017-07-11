package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Subject;

import java.util.Collection;
import java.util.Set;


public interface SubjectService extends CustomService<Subject> {

    Set<Subject> saveAll(Collection<Subject> subjects);

}
