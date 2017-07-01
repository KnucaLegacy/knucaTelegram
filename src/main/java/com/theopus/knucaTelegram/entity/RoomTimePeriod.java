package com.theopus.knucaTelegram.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roomTimePeriod")
public class RoomTimePeriod {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "id", length = 6, nullable = false)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name="lesson_id")
    private Lesson lesson;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomTimePeriod", cascade = CascadeType.ALL)
    private Set<LessonDate> lessonDate;

    public RoomTimePeriod() {
        lessonDate = new HashSet<>();
    }

    public RoomTimePeriod(Room room) {
        this.room = room;
        lessonDate = new HashSet<>();
    }

    public Room getRoom() {
        return room;
    }


    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean addLessonDate(LessonDate lessonDate){
        return this.lessonDate.add(lessonDate);
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
