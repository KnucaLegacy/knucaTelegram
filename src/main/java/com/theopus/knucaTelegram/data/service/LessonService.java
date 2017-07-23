package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;


public interface LessonService extends CustomService<Lesson> {

    List<Lesson> saveAll(Collection<Lesson> lessons);

    List<Lesson> getAll();

    List<Lesson> getExactDayByGroup(Date date, String groupName);
    List<Lesson> getExactDayByGroup(Date date, long groupId);
    List<Lesson> getExactDayByGroup(Date date, Group group);
    List<Lesson> getExactDayByTeacher(Date date, String teacherName);
    List<Lesson> getExactDayByTeacher(Date date, long teacherId);
    List<Lesson> getExactDayByTeacher(Date date, Teacher teacher);

    Map<Date,List<Lesson>> getWeekByGroup(Date date, Group group);
    Map<Date,List<Lesson>> getWeekByGroup(Date date, long groupId);
    Map<Date,List<Lesson>> getWeekByGroup(Date date, String groupName);

    Map<Date,List<Lesson>> getWeekByTeacher(Date date, Teacher teacher);
    Map<Date,List<Lesson>> getWeekByTeacher(Date date, long teacherId);
    Map<Date,List<Lesson>> getWeekByTeacher(Date date, String teacherName);

    Map<Date, List<Lesson>> getByTeacher(Date from, Date to, Teacher teacher);
    Map<Date, List<Lesson>> getByTeacher(Date from, Date to, long teacherId);
    Map<Date, List<Lesson>> getByTeacher(Date from, Date to, String teacherName);

    Map<Date, List<Lesson>> getByGroup(Date from, Date to, Group group);
    Map<Date, List<Lesson>> getByGroup(Date from, Date to, long groupId);
    Map<Date, List<Lesson>> getByGroup(Date from, Date to, String groupName);

}
