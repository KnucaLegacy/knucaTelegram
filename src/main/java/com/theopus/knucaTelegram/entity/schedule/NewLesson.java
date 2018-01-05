package com.theopus.knucaTelegram.entity.schedule;

import com.theopus.knucaTelegram.entity.schedule.enums.LessonOrder;

import java.time.LocalDate;
import java.util.Set;

public class NewLesson {


    private LessonOrder order;
    private Set<Room> rooms;
    private Set<Group> groups;
    private LocalDate date;
    private Course course;

    public NewLesson() {
    }

    public LessonOrder getOrder() {
        return order;
    }

    public void setOrder(LessonOrder order) {
        this.order = order;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "order=" + order +
                ", rooms=" + rooms +
                ", groups=" + groups +
                ", date=" + date +
                ", course=" + course +
                '}';
    }
}
