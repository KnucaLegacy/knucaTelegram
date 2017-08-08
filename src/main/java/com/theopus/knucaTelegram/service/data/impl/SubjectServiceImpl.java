package com.theopus.knucaTelegram.service.data.impl;

import com.theopus.knucaTelegram.entity.schedule.Subject;
import com.theopus.knucaTelegram.service.data.repository.SubjectRepository;
import com.theopus.knucaTelegram.service.data.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
public class SubjectServiceImpl implements SubjectService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Set<Subject> subjectsCache = new HashSet<>();

    @Resource
    private SubjectRepository subjectRepository;

    @Override
    public Set<Subject> saveAll(Collection<Subject> subjects) {
        return null;
    }

    @Override
    public Collection<Subject> getAll() {
        return subjectRepository.findAll();
    }

    @Override
    public long getCount() {
        return subjectRepository.count();
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
    public Subject getById(long id) {
        return subjectRepository.getOne(id);
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
