package com.theopus.knucaTelegram.entity.schedule;

import com.theopus.knucaTelegram.entity.schedule.enums.LessonOrder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Circumstance {

    private long id;


    private Lesson lesson;


    private LessonOrder order;


    private Room room;

    private Set<Date> dates = new HashSet<>();

    public Circumstance() {
    }

    public Circumstance(LessonOrder order, Room room, Set<Date> dates) {
        this.order = order;
        this.room = room;
        this.dates = dates;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public LessonOrder getOrder() {
        return order;
    }

    public void setOrder(LessonOrder order) {
        this.order = order;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Set<Date> getDates() {
        return dates;
    }

    public void setDates(Set<Date> dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "Circumstance{" +
                "id=" + id +
                ", order=" + order +
                ", room=" + room +
                ", dates=" + dates +
                '}';
    }
}
