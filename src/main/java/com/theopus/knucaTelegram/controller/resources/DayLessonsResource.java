package com.theopus.knucaTelegram.controller.resources;

import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;

import java.util.Date;
import java.util.List;

public class DayLessonsResource extends ResourceSupport {
    private Date date;
    private DayOfWeek dayOfWeek;
    private List<SimpleLessonResource> lessons;

    public DayLessonsResource(Date date, DayOfWeek dayOfWeek, List<SimpleLessonResource> lessons) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.lessons = lessons;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<SimpleLessonResource> getLessons() {
        return lessons;
    }

    public void setLessons(List<SimpleLessonResource> lessons) {
        this.lessons = lessons;
    }
}
