package com.theopus.knucaTelegram.controller.resources;

import com.theopus.knucaTelegram.entity.schedule.SimpleLesson;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonOrder;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonType;
import org.springframework.hateoas.ResourceSupport;

public class SimpleLessonResource extends ResourceSupport {
    protected long fullid;
    protected LessonType lessonType;
    protected LessonOrder order;

    public SimpleLessonResource(SimpleLesson simpleLesson) {
        this.fullid = simpleLesson.getFullid();
        this.lessonType = simpleLesson.getLessonType();
        this.order = simpleLesson.getOrder();
    }

    public long getFullid() {
        return fullid;
    }

    public void setFullid(long fullid) {
        this.fullid = fullid;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    public LessonOrder getOrder() {
        return order;
    }

    public void setOrder(LessonOrder order) {
        this.order = order;
    }
}
