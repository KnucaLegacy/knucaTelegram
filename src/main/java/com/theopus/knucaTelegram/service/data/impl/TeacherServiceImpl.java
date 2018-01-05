package com.theopus.knucaTelegram.service.data.impl;

import com.theopus.knucaTelegram.entity.schedule.Teacher;
import com.theopus.knucaTelegram.service.data.TeacherService;
import com.theopus.knucaTelegram.service.data.repository.LessonRepository;
import com.theopus.knucaTelegram.service.data.repository.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private String searchLine;
    @Resource
    private TeacherRepository teacherRepository;
    @Resource
    private LessonRepository lessonRepository;

    private Set<Teacher> teachersCache = new HashSet<>();

    @PostConstruct
    @Override
    public void loadSearchLine() {
        StringBuilder line = new StringBuilder();
        Set<Teacher> teachers = new HashSet<>(this.getAll());
        line.append(";");
        teachers.forEach(teacher -> {
            line
                    .append(teacher.getName())
                    .append(";");
        });
        searchLine = line.toString();
    }

    @Override
    public String getSearchLine() {
        if (searchLine == null)
            loadSearchLine();
        return searchLine;
    }
    @Override
    public Teacher saveOne(Teacher teacher) {
        Teacher findT = teacherRepository.findByExactName(teacher.getName());
        Teacher toSave = null;
        if (findT != null)
            toSave = findT;
        else
            toSave = teacherRepository.save(teacher);

        loadSearchLine();
        return toSave;
    }

    @Override
    public Teacher getById(long id) {
        return teacherRepository.findOne(id);
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
    public Set<Teacher> getByAlliesName(String name) {
        return teacherRepository.findNameAlies(name);
    }

    @Override
    public Teacher findByName(String name) {
        return teacherRepository.findByExactName(name);
    }

    @Override
    public void deleteById(long id) {
        teacherRepository.delete(id);
    }

    @Override
    public void deleteTeacher(Teacher t) {
    }

    @Override
    public Set<Teacher> getAll() {
        Set<Teacher> result = new HashSet<>();
        teacherRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public long getCount() {
        return teacherRepository.count();
    }

    @Override
    public void flush() {
        loadSearchLine();
        teachersCache = null;
    }

    @Override
    public void deleteBy(long id) {

    }

    @Override
    public void deleteBy(Teacher teacher) {

    }

    private Teacher getTeacher(Teacher teacher){
        for (Teacher t: teachersCache) {
            if (t.equals(teacher))
                return t;
        }
        return null;
    }

}
