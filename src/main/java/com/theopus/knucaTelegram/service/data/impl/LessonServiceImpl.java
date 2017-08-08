package com.theopus.knucaTelegram.service.data.impl;

import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.Lesson;
import com.theopus.knucaTelegram.entity.schedule.Teacher;
import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import com.theopus.knucaTelegram.service.data.repository.LessonRepository;
import com.theopus.knucaTelegram.service.data.GroupService;
import com.theopus.knucaTelegram.service.data.LessonService;
import com.theopus.knucaTelegram.service.data.SubjectService;
import com.theopus.knucaTelegram.service.data.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class LessonServiceImpl implements LessonService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private GroupService groupService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private SubjectService subjectService;
    @Resource
    private RoomServiceImpl roomService;
    @Resource
    private LessonRepository lessonRepository;

    @Override
    public Collection<Lesson> saveAll(Collection<Lesson> lessons) {
        lessons.forEach(lesson ->{
            lesson.setSubject(subjectService.saveOne(lesson.getSubject()));
            lesson.setGroups(groupService.saveAll(lesson.getGroups()));
            lesson.setTeachers(teacherService.saveAll(lesson.getTeachers()));
            lesson.getCircumstances().forEach(circumstance ->{
                circumstance.setRoom(roomService.saveRooms(circumstance.getRoom()));
                circumstance.setLesson(lesson);
            });
            lessonRepository.save(lesson);
        });
        flush();
        return lessons;
    }

    @Override
    public Lesson saveOne(Lesson lesson) {
        lesson.setSubject(subjectService.saveOne(lesson.getSubject()));
        lesson.setGroups(groupService.saveAll(lesson.getGroups()));
        lesson.setTeachers(teacherService.saveAll(lesson.getTeachers()));
        lesson.getCircumstances().forEach(circumstance ->{
            circumstance.setRoom(roomService.saveRooms(circumstance.getRoom()));
            circumstance.setLesson(lesson);
        });
        flush();
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson getById(long id) {
        return lessonRepository.getOne(id);
    }

    @Override
    public Collection<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    @Override
    public List<Lesson> getByGroup(Date date, Group group) {
        return lessonRepository.getByGroup(group, date);
    }

    @Override
    public Map<Date, List<Lesson>> getByGroup(Set<Date> dates, Group group){
        Set<Date> datesS = new TreeSet<>(dates);
        Map<Date, List<Lesson>> result = new LinkedHashMap<>();
        datesS.forEach(date -> {
            result.put(date, lessonRepository.getByGroup(group, date));
        });
        return result;
    }

    @Override
    public Map<Date, List<Lesson>> getByTeacher(Set<Date> dates, Teacher teacher) {
        Set<Date> datesS = new TreeSet<>(dates);
        Map<Date, List<Lesson>> result = new LinkedHashMap<>();
        datesS.forEach(date -> result.put(date, lessonRepository.getByTeacher(teacher, date)));
        return result;
    }

    @Override
    public List<Lesson> getByTeacher(Date date, Teacher teacher) {
        return lessonRepository.getByTeacher(teacher, date);
    }

    @Override
    public Map<Date, List<Lesson>> getWeekByGroup(Date date, Group group) {
        Set<Date> dates = DayOfWeek.getWeekOfDate(date);
        return getByGroup(dates,group);
    }

    @Override
    public Map<Date, List<Lesson>> getWeekByTeacher(Date date, Teacher teacher) {
        Set<Date> dates = DayOfWeek.getWeekOfDate(date);
        return getByTeacher(dates,teacher);
    }

    @Override
    public Map<Date, List<Lesson>> getByTeacher(Date from, Date to, Teacher teacher) {
        Set<Date> dates = DayOfWeek.fromToToDateSet(from,to);
        return getByTeacher(dates, teacher);
    }

    @Override
    public Map<Date, List<Lesson>> getByGroup(Date from, Date to, Group group) {
        Set<Date> dates = DayOfWeek.fromToToDateSet(from,to);
        return getByGroup(dates,group);
    }

    @Override
    public long getCount() {
        return lessonRepository.count();
    }

    @Override
    public void flush() {
        groupService.flush();
        teacherService.flush();
        subjectService.flush();
        roomService.flush();
    }
}
