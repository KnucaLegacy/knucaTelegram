package com.theopus.knucaTelegram.entity.schedule;

import com.theopus.knucaTelegram.entity.schedule.enums.LessonType;

import java.util.HashSet;
import java.util.Set;

public class Lesson {


    private long id;

    private Subject subject;


    private LessonType lessonType;


    private Set<Teacher> teachers = new HashSet<>();

    private Set<Group> groups = new HashSet<>();

    private Set<Circumstance> circumstances = new HashSet<>();

    public Lesson() {
    }

    public Lesson(Subject subject, LessonType lessonType, Set<Teacher> teachers, Set<Group> groups, Group ownerGroup, Set<Circumstance> circumstances) {
        this.subject = subject;
        this.lessonType = lessonType;
        this.teachers = teachers;
        this.groups = groups;
        this.circumstances = circumstances;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Circumstance> getCircumstances() {
        return circumstances;
    }

    public void setCircumstances(Set<Circumstance> circumstances) {
        this.circumstances = circumstances;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", subject=" + subject +
                ", lessonType=" + lessonType +
                ", teachers=" + teachers +
                ", groups=" + groups +
                ", circumstances=" + circumstances +
                '}';
    }
}
