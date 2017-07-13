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

    List<Lesson> getExactDayByGroup(Date date, String groupName, int offset);
    /**
     *
     * @param date
     * @param groupId
     * @param offset :
     *               0 today
     *               <0 n days ago
     *               >0 n days forwadr
     * @return list of lessons at the exact day
     */
    List<Lesson> getExactDayByGroup(Date date, long groupId, int offset);
    List<Lesson> getExactDayByGroup(Date date, Group group, int offset);

    List<Lesson> getExactDayByTeacher(Date date, String teacherName, int offset);
    /**
     *
     * @param date
     * @param teacherIdId
     * @param offset :
     *               0 today
     *               <0 n days ago
     *               >0 n days forwadr
     * @return list of lessons at the exact day
     */
    List<Lesson> getExactDayByTeacher(Date date, long teacherId, int offset);
    List<Lesson> getExactDayByTeacher(Date date, Teacher teacher, int offset);

    Map<DayOfWeek,List<Lesson>> getWeekByGroup(Date date, Group group, int week);
    Map<DayOfWeek,List<Lesson>> getWeekByGroup(Date date, long groupId, int week);
    Map<DayOfWeek,List<Lesson>> getWeekByGroup(Date date, String groupName, int week);

    Map<DayOfWeek,List<Lesson>> getWeekByTeacher(Date date, Teacher teacher, int week);
    Map<DayOfWeek,List<Lesson>> getWeekByTeacher(Date date, long teacherId, int week);
    Map<DayOfWeek,List<Lesson>> getWeekByTeacher(Date date, String teacherName, int week);

}
