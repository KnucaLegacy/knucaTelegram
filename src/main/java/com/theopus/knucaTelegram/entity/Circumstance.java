package com.theopus.knucaTelegram.entity;

import com.theopus.knucaTelegram.entity.enums.LessonOrder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Circumstance {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "id", length = 6, nullable = false)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(name = "lesson_order")
    private LessonOrder order;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="dates", joinColumns=@JoinColumn(name="circumstance_id"))
    @Column(name="dates")
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
