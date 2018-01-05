package com.theopus.knucaTelegram.service.data;

import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.SimpleLesson;
import com.theopus.knucaTelegram.entity.schedule.Teacher;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SimpleLessonService {

    List<SimpleLesson> getById(Date date, long id);

    List<SimpleLesson> getByGroup(Date date, Group group);

    List<SimpleLesson> getByTeacher(Date date, Teacher teacher);

    Map<Date, List<SimpleLesson>> getByGroup(Set<Date> dates, Group group);

    Map<Date, List<SimpleLesson>> getByTeacher(Set<Date> date, Teacher teacher);

    Map<Date,List<SimpleLesson>> getWeekByGroup(Date date, Group group);

    Map<Date,List<SimpleLesson>> getWeekByTeacher(Date date, Teacher teacher);

    Map<Date, List<SimpleLesson>> getByGroup(Date from, Date to, Group group);

    Map<Date, List<SimpleLesson>> getByTeacher(Date from, Date to, Teacher teacher);

}
