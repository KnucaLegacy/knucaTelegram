package com.theopus.knucaTelegram.service.data;

import com.theopus.knucaTelegram.entity.Group;
import com.theopus.knucaTelegram.entity.Lesson;
import com.theopus.knucaTelegram.entity.Teacher;

import java.util.*;


public interface LessonService extends CustomService<Lesson> {

    Collection<Lesson> saveAll(Collection<Lesson> lessons);

    Collection<Lesson> getAll();

    List<Lesson> getByGroup(Date date, Group group);

    List<Lesson> getByTeacher(Date date, Teacher teacher);

    Map<Date, List<Lesson>> getByGroup(Set<Date> dates, Group group);

    Map<Date, List<Lesson>> getByTeacher(Set<Date> date, Teacher teacher);

    Map<Date,List<Lesson>> getWeekByGroup(Date date, Group group);

    Map<Date,List<Lesson>> getWeekByTeacher(Date date, Teacher teacher);

    Map<Date, List<Lesson>> getByGroup(Date from, Date to, Group group);

    Map<Date, List<Lesson>> getByTeacher(Date from, Date to, Teacher teacher);
}
