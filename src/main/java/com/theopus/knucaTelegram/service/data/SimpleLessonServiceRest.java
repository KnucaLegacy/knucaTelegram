package com.theopus.knucaTelegram.service.data;

import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.SimpleLesson;
import com.theopus.knucaTelegram.entity.schedule.Teacher;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleLessonServiceRest implements SimpleLessonService {


    @Override
    public List<SimpleLesson> getById(Date date, long id) {
        return null;
    }
    @Override
    public List<SimpleLesson> getByGroup(Date date, Group group) {
        return null;
    }

    @Override
    public List<SimpleLesson> getByTeacher(Date date, Teacher teacher) {
        return null;
    }

    @Override
    public Map<Date, List<SimpleLesson>> getByGroup(Set<Date> dates, Group group) {
        return null;
    }

    @Override
    public Map<Date, List<SimpleLesson>> getByTeacher(Set<Date> date, Teacher teacher) {
        return null;
    }

    @Override
    public Map<Date, List<SimpleLesson>> getWeekByGroup(Date date, Group group) {
        return null;
    }

    @Override
    public Map<Date, List<SimpleLesson>> getWeekByTeacher(Date date, Teacher teacher) {
        return null;
    }

    @Override
    public Map<Date, List<SimpleLesson>> getByGroup(Date from, Date to, Group group) {
        return null;
    }

    @Override
    public Map<Date, List<SimpleLesson>> getByTeacher(Date from, Date to, Teacher teacher) {
        return null;
    }
}
