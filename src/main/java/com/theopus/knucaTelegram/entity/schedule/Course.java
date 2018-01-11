package com.theopus.knucaTelegram.entity.schedule;

import com.google.common.collect.Sets;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a concept of some classes at some time at some places.
 * Created by Oleksandr_Tkachov on 9/15/2017.
 */

public class Course {

    private Long id;

    private Subject subject;
    private LessonType type;

    private Set<Teacher> teachers = new HashSet<>();

    public Course() {
    }

    public Course(Subject subject, LessonType type, Set<Teacher> teachers) {
        this.subject = subject;
        this.type = type;
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        Sets.difference(teachers, course.teachers).isEmpty();
        return Objects.equals(subject, course.subject) &&
                type == course.type &&
                Sets.difference(teachers, course.teachers).isEmpty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, type, teachers);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", subject=" + subject +
                ", type=" + type +
                ", teachers=" + teachers +
                '}';
    }
}
