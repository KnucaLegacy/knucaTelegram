package com.theopus.knucaTelegram.service.massupload.impl;

import com.theopus.knucaTelegram.entity.Teacher;
import com.theopus.knucaTelegram.repository.TeacherRepository;
import com.theopus.knucaTelegram.service.massupload.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by irina on 03.07.17.
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private TeacherRepository teacherRepository;

    private Set<Teacher> teachersCache = new HashSet<>();

    @Override
    public Set<Teacher> saveAll(Set<Teacher> TeacherSet) {
        Set<Teacher> result = new HashSet<>();
        for (Teacher t : TeacherSet) {
            if (teachersCache.contains(t))
                result.add(getTeacher(t));
            else {
                Teacher findT = teacherRepository.findByName(t.getName());
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
