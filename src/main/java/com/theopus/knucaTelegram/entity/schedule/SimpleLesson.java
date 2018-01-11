package com.theopus.knucaTelegram.entity.schedule;

import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonOrder;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonType;

import java.util.Date;
import java.util.Set;

public class SimpleLesson {


    private long fullid;

    private DayOfWeek dayOfWeek;

    private LessonOrder order;

    private LessonType lessonType;

    private Subject subject;

    private Set<Group> groups;

    private Set<Teacher> teachers;

    private Set<Room> rooms;

    private Date date;

    public SimpleLesson() {
    }

    public SimpleLesson(long fullid, DayOfWeek dayOfWeek, LessonOrder order, LessonType lessonType, Subject subject, Set<Group> groups, Set<Teacher> teachers, Set<Room> rooms, Date date) {
        this.fullid = fullid;
        this.dayOfWeek = dayOfWeek;
        this.order = order;
        this.lessonType = lessonType;
        this.subject = subject;
        this.groups = groups;
        this.teachers = teachers;
        this.rooms = rooms;
        this.date = date;
    }


    public long getFullid() {
        return fullid;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LessonOrder getOrder() {
        return order;
    }

    public Subject getSubject() {
        return subject;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "SimpleLesson{" +
                "fullid=" + fullid +
                ", dayOfWeek=" + dayOfWeek +
                ", order=" + order +
                ", subject=" + subject +
                ", groups=" + groups +
                ", teachers=" + teachers +
                ", rooms=" + rooms +
                ", date=" + date +
                '}';
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    public void setFullid(long fullid) {
        this.fullid = fullid;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setOrder(LessonOrder order) {
        this.order = order;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
