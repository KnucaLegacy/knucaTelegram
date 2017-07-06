package com.theopus.knucaTelegram.data.service.impl;

import com.theopus.knucaTelegram.data.entity.Subject;
import com.theopus.knucaTelegram.data.repository.SubjectRepository;
import com.theopus.knucaTelegram.data.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by irina on 03.07.17.
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Set<Subject> subjectsCache = new HashSet<>();

    @Resource
    private SubjectRepository subjectRepository;

    @Override
    public Set<Subject> saveAll(Set<Subject> subjects) {
        return null;
    }

    @Override
    public Subject saveOne(Subject subject) {
        if (subjectsCache.contains(subject))
            return getSubject(subject);
        else {
            Subject findS = subjectRepository.findByName(subject.getName());
            if (findS != null)
            {
                subjectsCache.add(findS);
                return findS;
            }
            else {
                Subject saveS = subjectRepository.save(subject);
                subjectsCache.add(saveS);
                return saveS;
            }
        }
    }

    @Override
    public void flush() {
        subjectsCache = null;
    }

    private Subject getSubject(Subject subject){
        for (Subject t: subjectsCache) {
            if (t.equals(subject))
                return t;
        }
        return null;
    }
}
