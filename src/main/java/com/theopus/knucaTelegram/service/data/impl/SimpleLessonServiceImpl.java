package com.theopus.knucaTelegram.service.data.impl;

import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.SimpleLesson;
import com.theopus.knucaTelegram.entity.schedule.Teacher;
import com.theopus.knucaTelegram.service.LessonSimplifier;
import com.theopus.knucaTelegram.service.data.LessonService;
import com.theopus.knucaTelegram.service.data.SimpleLessonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class SimpleLessonServiceImpl implements SimpleLessonService {

    @Resource
    private LessonService lessonService;
    @Resource
    private LessonSimplifier simplifier;

    @Override
    public List<SimpleLesson> getByGroup(Date date, Group group) {
        return simplifier.simplify(lessonService.getByGroup(date,group), date);
    }

    @Override
    public List<SimpleLesson> getByTeacher(Date date, Teacher teacher) {
        return simplifier.simplify(lessonService.getByTeacher(date,teacher), date);
    }

    @Override
    public Map<Date, List<SimpleLesson>> getByGroup(Set<Date> dates, Group group) {
        return simplifier.simplify(lessonService.getByGroup(dates, group));
    }

    @Override
    public Map<Date, List<SimpleLesson>> getByTeacher(Set<Date> date, Teacher teacher) {
        return simplifier.simplify(lessonService.getByTeacher(date, teacher));
    }

    @Override
    public Map<Date, List<SimpleLesson>> getWeekByGroup(Date date, Group group) {
        return simplifier.simplify(lessonService.getWeekByGroup(date, group));
    }

    @Override
    public Map<Date, List<SimpleLesson>> getWeekByTeacher(Date date, Teacher teacher) {
        return simplifier.simplify(lessonService.getWeekByTeacher(date, teacher));
    }

    @Override
    public Map<Date, List<SimpleLesson>> getByGroup(Date from, Date to, Group group) {
        return simplifier.simplify(lessonService.getByGroup(from, to, group));
    }

    @Override
    public Map<Date, List<SimpleLesson>> getByTeacher(Date from, Date to, Teacher teacher) {
        return simplifier.simplify(lessonService.getByTeacher(from, to, teacher));
    }
}
