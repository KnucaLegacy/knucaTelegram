package com.theopus.knucaTelegram.data.service.impl;

import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.repository.TeacherRepository;
import com.theopus.knucaTelegram.data.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private TeacherRepository teacherRepository;

    private Set<Teacher> teachersCache = new HashSet<>();

    @Override
    public Teacher saveOne(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher getById(long id) {
        return teacherRepository.getOne(id);
    }

    @Override
    public Set<Teacher> saveAll(Collection<Teacher> TeacherSet) {
        Set<Teacher> result = new HashSet<>();
        for (Teacher t : TeacherSet) {
            if (teachersCache.contains(t))
                result.add(getTeacher(t));
            else {
                Teacher findT = teacherRepository.findByExactName(t.getName());
                if (findT != null){
                    result.add(findT);
                    teachersCache.add(findT);
                }
                else{
                    Teacher savedT = teacherRepository.save(t);
                    result.add(savedT);
                    teachersCache.add(savedT);
                }

            }
        }
        return result;
    }

    @Override
    public Teacher findByName(String name) {
        return teacherRepository.findByExactName(name);
    }

    @Override
    public Collection<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    @Override
    public long getCount() {
        return teacherRepository.count();
    }

    @Override
    public void flush() {
        teachersCache = null;
    }

    private Teacher getTeacher(Teacher teacher){
        for (Teacher t: teachersCache) {
            if (t.equals(teacher))
                return t;
        }
        return null;
    }

}
