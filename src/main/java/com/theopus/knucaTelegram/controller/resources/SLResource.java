package com.theopus.knucaTelegram.controller.resources;

import com.theopus.knucaTelegram.entity.schedule.SimpleLesson;
import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonOrder;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonType;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

public class SLResource extends ResourceSupport {

    private long fullid;
    private DayOfWeek dayOfWeek;
    private LessonOrder order;
    private LessonType lessonType;
    private Date date;

    public SLResource(SimpleLesson simpleLesson) {
        this.fullid = simpleLesson.getFullid();
        this.dayOfWeek = simpleLesson.getDayOfWeek();
        this.order = simpleLesson.getOrder();
        this.lessonType = simpleLesson.getLessonType();
        this.date = simpleLesson.getDate();
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

    public LessonType getLessonType() {
        return lessonType;
    }

    public Date getDate() {
        return date;
    }
}
