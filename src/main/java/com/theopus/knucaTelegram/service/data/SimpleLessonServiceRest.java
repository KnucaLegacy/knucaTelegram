package com.theopus.knucaTelegram.service.data;

import com.theopus.knucaTelegram.entity.schedule.*;
import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleLessonServiceRest implements SimpleLessonService {


    private static final SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public List<SimpleLesson> getById(Date date, long id) {
        return null;
    }
    @Override
    public List<SimpleLesson> getByGroup(Date date, Group group) {
        RestTemplate restTemplate = new RestTemplate();
        String dates = simp.format(date);
        NewLesson[] forObject = restTemplate.getForObject("http://localhost:8080/lessons/" + dates + "/group/" + group.getId(), NewLesson[].class);
        return Arrays.stream(forObject).map(this::newToSimple).collect(Collectors.toList());
    }

    @Override
    public List<SimpleLesson> getByTeacher(Date date, Teacher teacher) {
        RestTemplate restTemplate = new RestTemplate();
        String dates = simp.format(date);
        NewLesson[] forObject = restTemplate.getForObject("http://localhost:8080/lessons/" + dates + "/teacher/" + teacher.getId(), NewLesson[].class);
        return Arrays.stream(forObject).map(this::newToSimple).collect(Collectors.toList());
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

    public SimpleLesson newToSimple(NewLesson lesson) {
        SimpleLesson lesson1 = new SimpleLesson();
        lesson1.setDate(new Date(lesson.getDate().toEpochDay()));
        lesson1.setDayOfWeek(DayOfWeek.intToDay(lesson.getDate().getDayOfWeek().ordinal() - 1));
        lesson1.setGroups(lesson.getGroups());
        lesson1.setOrder(lesson.getOrder());
        lesson1.setSubject(lesson.getCourse().getSubject());
        lesson1.setTeachers(lesson.getCourse().getTeachers());
        lesson1.setLessonType(lesson.getCourse().getType());
        return lesson1;
    }
}
