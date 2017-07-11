package com.theopus.knucaTelegram.data.service;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.Teacher;

import java.util.Collection;
import java.util.Date;
import java.util.List;


public interface LessonService extends CustomService<Lesson> {

    List<Lesson> saveAll(Collection<Lesson> lessons);

    List<Lesson> getAll();

    List<Lesson> getTodayByGroupName(String name);
    List<Lesson> getExactDayByGroup(Date date, String groupName);
    List<Lesson> getExactDayByGroup(Date date, long groupId);
    List<Lesson> getExactDayByGroup(Date date, Group group);

    List<Lesson> getExactDayByTeacher(Date date, String teacherName);
    List<Lesson> getExactDayByTeacher(Date date, long teacherId);
    List<Lesson> getExactDayByTeacher(Date date, Teacher teacher);


    List<Lesson> getWeekByGroup(Group group);
    List<Lesson> getWeekByGroup(Group group, int week);
    List<Lesson> getWeekByGroup(long groupId, int week);
    List<Lesson> getWeekByGroup(String groupName, int week);

    List<Lesson> getWeekByTeacher(Teacher teacher);
    List<Lesson> getWeekByTeacher(Teacher teacher, int week);
    List<Lesson> getWeekByTeacher(long teacherId, int week);
    List<Lesson> getWeekByTeacher(String teacherName, int week);

}
