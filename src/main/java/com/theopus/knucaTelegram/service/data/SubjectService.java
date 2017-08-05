package com.theopus.knucaTelegram.service.data;

import com.theopus.knucaTelegram.entity.Subject;

import java.util.Collection;
import java.util.Set;


public interface SubjectService extends CustomService<Subject> {

    Set<Subject> saveAll(Collection<Subject> subjects);

}
