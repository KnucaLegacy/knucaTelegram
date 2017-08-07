package com.theopus.knucaTelegram.entity;

import com.theopus.knucaTelegram.entity.enums.LessonType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

public class Lesson {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "id", length = 6, nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "lesson_type")
    private LessonType lessonType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "lesson_teacher",
            joinColumns =@JoinColumn(name = "lesson_id"),
            inverseJoinColumns =@JoinColumn(name = "teacher_id") )
    private Set<Teacher> teachers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "lesson_group",
            joinColumns =@JoinColumn(name = "lesson_id"),
            inverseJoinColumns =@JoinColumn(name = "group_id") )
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
