package com.theopus.knucaTelegram.controller.resources;

import com.theopus.knucaTelegram.entity.schedule.SimpleLesson;
import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonOrder;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonType;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

public class SimpleLessonResourceFull extends SimpleLessonResource {


    private DayOfWeek dayOfWeek;
    private Date date;

    public SimpleLessonResourceFull(SimpleLesson simpleLesson) {
        super(simpleLesson);
        this.dayOfWeek = simpleLesson.getDayOfWeek();
        this.date = simpleLesson.getDate();
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
