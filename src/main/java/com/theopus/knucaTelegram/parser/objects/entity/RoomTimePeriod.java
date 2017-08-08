package com.theopus.knucaTelegram.parser.objects.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.theopus.knucaTelegram.entity.schedule.Lesson;
import com.theopus.knucaTelegram.entity.schedule.Room;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class RoomTimePeriod {


    private long id;


    private Room room;


    private Lesson lesson;

    @OneToMany(mappedBy = "roomTimePeriod", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<LessonDate> lessonDate = new HashSet<>();

    public RoomTimePeriod() {
    }

    public RoomTimePeriod(Room room) {
        this.room = room;
    }

    public boolean isAtDate(Date date){
        for (LessonDate ld: lessonDate) {
            if (ld.isAtDate(date))
                return true;
        }
        return false;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean addLessonDate(LessonDate lessonDate){
        if (this.lessonDate == null)
            this.lessonDate = new HashSet<>();
        return this.lessonDate.add(lessonDate);
    }

    public boolean addAllLessonDate(Set<LessonDate> lessonDates){
        if (this.lessonDate == null)
            this.lessonDate = new HashSet<>();
        return this.lessonDate.addAll(lessonDates);
    }

    public Set<LessonDate> getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(Set<LessonDate> lessonDate) {
        this.lessonDate = lessonDate;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomTimePeriod that = (RoomTimePeriod) o;

        if (room != null ? !room.equals(that.room) : that.room != null) return false;
        return lessonDate != null ? lessonDate.equals(that.lessonDate) : that.lessonDate == null;
    }

    @Override
    public int hashCode() {
        int result = room != null ? room.hashCode() : 0;
        result = 31 * result + (lessonDate != null ? lessonDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoomTimePeriod{" +
                "room=" + room +
                ", lessonDate=" + lessonDate +
                '}';
    }
}
